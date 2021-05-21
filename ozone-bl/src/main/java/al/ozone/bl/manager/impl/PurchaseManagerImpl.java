package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchPurchaseBean;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.dao.DealChoiceDAO;
import al.ozone.bl.dao.DealDAO;
import al.ozone.bl.dao.PaymentDAO;
import al.ozone.bl.dao.PurchaseDAO;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentPayPal;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.ZUtils;

public class PurchaseManagerImpl implements PurchaseManager, Serializable {

	private static final long serialVersionUID = -4156840260716286337L;
	protected static final transient Log logger = LogFactory.getLog(PurchaseManagerImpl.class);

	private PurchaseDAO purchaseDAO;
	private PaymentDAO paymentDAO;
	private CreditDAO creditDAO;
	private DealDAO dealDAO;
	private DealChoiceDAO dealChoiceDAO;
	
	public PaymentDAO getPaymentDAO() {
		return paymentDAO;
	}
	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}
	public CreditDAO getCreditDAO() {
		return creditDAO;
	}
	public void setCreditDAO(CreditDAO creditDAO) {
		this.creditDAO = creditDAO;
	}
	public PurchaseDAO getPurchaseDAO() {
		return purchaseDAO;
	}
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	public void setDealDAO(DealDAO dealDAO) {
		this.dealDAO = dealDAO;
	}
	public void setDealChoiceDAO(DealChoiceDAO dealChoiceDAO) {
		this.dealChoiceDAO = dealChoiceDAO;
	}
	/**TODO javadoc Ermal
	 * Insert payment
	 * insert purchase
	 * Calculate credit to use, create a new one if necessary (as difference)
	 * Set credit as used, set as used by purchase
	 * increase nr purchases for the publication
	 * @param purchase
	 * @throws Exception
	 */
	@Override
	public void insert(Purchase purchase, boolean useCredit) throws Exception {
		Payment payment = purchase.getPayment();
		//payment = new PaymentCash();
		try {
			paymentDAO.insert(payment);
			//logger.debug("Inserted Payment nr."+payment.getId());
		} catch (Exception e) {
			logger.error("Can not insert Payment for customer "+purchase.getCustomer().getEmail(), e);
			throw e;
		}
				
		//TODO the inserted payment has id, operationName and 
		//purchase.setPayment(payment);//TODO this is a bug, because reset the child fields
//		System.out.println("payment inserted:"+payment);
//		System.out.println("payment stil in purchase:"+purchase.getPayment());
		try {
			purchaseDAO.insert(purchase);
			//logger.debug("Inserted Purchase nr."+purchase.getId());
		} catch (Exception e) {
			logger.error("Can not insert Purchase for customer "+purchase.getCustomer().getEmail(), e);
			throw e;
		}
		
		// use credit only if useCredit checkbox is selected
		if(useCredit){
			String aboutUse = "Used for Purchase " + purchase.getId() + ".";
			int toPay = purchase.getTotAmount();
			//List<Credit> credits = creditDAO.getByCustomer(purchase.getCustomer());
			List<Credit> credits = ZUtils.getValidCreditsForCustomer(purchase.getCustomer());
			logger.info("ToPay "+toPay+"L with creditList:"+credits);
			for (Credit c : credits) {
				if(toPay > 0){
					int creditVal = c.getValue();
					if(creditVal > toPay){
						logger.debug(toPay+"L will be paied with credit id. "+c.getId()+" val="+creditVal);
						//toPay value will be paid with the value of creditVal.
						//create and inserts a new Credit with the remaining value
						String about = "Credit Difference from Purchase "+purchase.getId();
						int remainCredit = creditVal - toPay;
						//Create a new credit with the difference
						Credit newCredit = new Credit();
						newCredit.setAssignedDate(new Date());
						newCredit.setValidDate(c.getValidDate());
						newCredit.setValue(remainCredit);
						newCredit.setType(Credit.TYPE_DIFFERENCE);
						newCredit.setCustomer(c.getCustomer());
						newCredit.setAbout(about);
						creditDAO.insert(newCredit);
						logger.info("Inserted new Credit nr."+newCredit.getId()+" val="+remainCredit+"L as remains/difference from purchase "+purchase.getId()+" [Customer: "+purchase.getCustomer().getEmail()+"] remainCredit="+remainCredit+"; toPay="+toPay+"; creditVal="+creditVal);
						aboutUse = "Used "+toPay+"L for Purchase " + purchase.getId() + ". Remaing "+remainCredit+"L inserted as Credit Nr."+newCredit.getId();
						toPay = 0;
					}else{
						//sottrarre da quello che rimane ancorta da pagare
						toPay = toPay - creditVal;
						logger.info("Paying "+creditVal+"L with credit nr."+c.getId()+". Remaining "+toPay+"L will be paid with the next credit");
					}
				
					//set credit as used
					creditDAO.setCreditAsUsed(c.getId(), aboutUse);
					logger.info("Set Credit Nr."+c.getId()+" val="+c.getValue()+"L as used");	
					
					//set credit used by this purchase
					try {
						purchaseDAO.setCreditUsedByPurchase(purchase.getId(), c.getId());
						logger.info("Set/Inserted Credit Nr."+c.getId()+" Val:"+c.getValue()+"L as used from Purchase "+purchase.getId()+" [Customer: "+purchase.getCustomer().getEmail()+"]");
					} catch (Exception e) {
						logger.error("Can not set/insert Credit Nr."+c.getId()+" val="+c.getValue()+"L as used for Purchase "+purchase.getId()+" [Customer: "+purchase.getCustomer().getEmail()+"]", e);
						throw e;
					}
					
					//send email to customer for notification
					
				}
			}
		}
		
		dealChoiceDAO.increasePurchases(purchase);
		dealDAO.increasePurchases(purchase);
	}

	@Override
	public void update(Purchase purchase) {
		purchaseDAO.update(purchase);
	}

	@Override
	public void delete(Purchase purchase) {
		purchaseDAO.delete(purchase);
		
	}

	@Override
	public List<Purchase> search(Purchase purchase) {
		return purchaseDAO.search(purchase);
	}

	@Override
	public List<Purchase> getAll() {
		return purchaseDAO.getAll();
	}	

	@Override
	public Purchase get(Integer id) {
		return purchaseDAO.get(id);
	}

	@Override
	public List<Credit> getCreditsUsedByPurchase(Integer purId) {
		return purchaseDAO.getCreditsUsedByPurchase(purId);
	}

	@Override
	public List<Purchase> search(SearchPurchaseBean sb) {
		return purchaseDAO.search(sb);
	}

	@Override
	public List<Purchase> getByCustomer(Integer cusId) {
		return purchaseDAO.getByCustomer(cusId);
	}

	@Override
	public int searchCount(SearchPurchaseBean sb) {
		return purchaseDAO.searchCount(sb);
	}
	
	@Override
	public List<Purchase> getAllPurchasesForDeal(Integer dealId) {
		return purchaseDAO.getAllPurchasesForDeal(dealId);
	}
	@Override
	public List<ResultStatisticBean> getPurchasesGroupedByMonths() {
		return purchaseDAO.getPurchasesGroupedByMonths();
	}
	@Override
	public List<ResultStatisticBean> getTotAmountGroupedByMonths() {
		return purchaseDAO.getTotAmountGroupedByMonths();
	}
	@Override
	public List<ResultStatisticBean> getEarningsGroupedByMonths() {
		return purchaseDAO.getEarningsGroupedByMonths();
	}
	@Override
	public Purchase getPurchaseByOrderId(Integer orderId) {
		return purchaseDAO.getPurchaseByOrderId(orderId);
	}
	@Override
	public void setPurchaseAsNotConfirmed(Purchase pur, String reason) {	
		PaymentPayPal pp = (PaymentPayPal) pur.getPayment();
		pp.setPaymentStatus(reason);
		paymentDAO.changePaymentStatus(pp);
		purchaseDAO.setPurchaseAsNotConfirmed(pur.getId());
	}
	@Override
	public List<ResultStatisticBean> getPurchasesByCount() {
		return purchaseDAO.getPurchasesByCount();
	}
	@Override
	public Purchase getPurchaseForCoupon(String code) {
		return purchaseDAO.getPurchaseForCoupon(code);
	}
	@Override
	public void setFeedbackRequestedForPurchase(Integer purId,	boolean isFeedbackReq) {
		purchaseDAO.setFeedbackRequestedForPurchase(purId, isFeedbackReq);
	}
	@Override
	public boolean isPurchaseUsed(Integer purId) {
		return purchaseDAO.isPurchaseUsed(purId);
	}
	
	@Override
	/**
	 * Changes the customer of the given purchase
	 * Also updates the payment note
	 */
	public void changePurchaseCustomer(Purchase p, String note) {
		purchaseDAO.changePurchaseCustomer(p);
		
		paymentDAO.updatePaymentNote(p.getPayment().getId(), note);
	}
	
	@Override
	public Integer getSumTotAmount() {
		return purchaseDAO.getSumTotAmount();
	}
	
	@Override
	public Integer getSumTotEarning() {
		return purchaseDAO.getSumTotEarning();
	}
	
	@Override
	public Integer getTotBuyers() {
		return purchaseDAO.getTotBuyers();
	}
	
	@Override
	public Integer getTotCouponsSold() {
		return purchaseDAO.getTotCouponsSold();
	}
	
}
