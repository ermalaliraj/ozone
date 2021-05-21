package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchPurchaseBean;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Purchase;

public interface PurchaseManager {

	public Purchase get(Integer id);
	
	public void insert (Purchase purchase , boolean useCredit) throws Exception;
	
	@Secured( { "ROLE_ADMIN" })
	public void update (Purchase purchase);
	
	@Secured( { "ROLE_ADMIN" })
	public void delete (Purchase purchase);

	public List<Purchase> search(Purchase purchase);
	
	public List<Purchase> getAll();

	public List<Credit> getCreditsUsedByPurchase(Integer purId);

	@Secured( { "ROLE_ADMIN" })
	public List<Purchase> search(SearchPurchaseBean sb);

	public List<Purchase> getByCustomer(Integer cusId);

	public int searchCount(SearchPurchaseBean sb);

	public List<Purchase> getAllPurchasesForDeal(Integer dealId);

	public List<ResultStatisticBean> getPurchasesGroupedByMonths();
	public List<ResultStatisticBean> getTotAmountGroupedByMonths();
	public List<ResultStatisticBean> getEarningsGroupedByMonths();

	public Purchase getPurchaseByOrderId(Integer orderId);
	public void setPurchaseAsNotConfirmed(Purchase pur, String reason);
	public List<ResultStatisticBean> getPurchasesByCount();

	public Purchase getPurchaseForCoupon(String code);
	public void setFeedbackRequestedForPurchase(Integer purId, boolean isFeedbackReq);
	public boolean isPurchaseUsed(Integer purId);
	public void changePurchaseCustomer(Purchase p, String note);

	public Integer getSumTotAmount();
	public Integer getSumTotEarning();
	public Integer getTotBuyers();
	public Integer getTotCouponsSold();


}