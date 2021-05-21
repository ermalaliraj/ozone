package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchPurchaseBean;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Purchase;

public interface PurchaseDAO extends GenericDAO<Purchase, Integer> {
	
	public void insert(Purchase purchase);
	
	public List<Purchase> search(SearchPurchaseBean sb);
	
	public List<Purchase> getAllPurchasesForDeal(Integer pubId);

	public List<Credit> getCreditsUsedByPurchase(Integer purId);
	
	public void setCreditUsedByPurchase(Integer purId, Integer creditId);

	public List<Purchase> getByCustomer(Integer cusId);

	public int searchCount(SearchPurchaseBean sb);

	public List<ResultStatisticBean> getPurchasesGroupedByMonths();
	public List<ResultStatisticBean> getTotAmountGroupedByMonths();
	public List<ResultStatisticBean> getEarningsGroupedByMonths();
	
	public Purchase getPurchaseByOrderId(Integer orderId);
	public void setPurchaseAsNotConfirmed(Integer purId);
	public List<ResultStatisticBean> getPurchasesByCount();

	public Purchase getPurchaseForCoupon(String code);
	public void setFeedbackRequestedForPurchase(Integer purId,	boolean isFeedbackReq);
	public boolean isPurchaseUsed(Integer purId);
	public void changePurchaseCustomer(Purchase p);

	public Integer getSumTotAmount();
	public Integer getSumTotEarning();
	public Integer getTotBuyers();
	public Integer getTotCouponsSold();

}
