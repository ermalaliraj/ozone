package al.ozone.bl.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchPurchaseBean;
import al.ozone.bl.dao.PurchaseDAO;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Purchase;

public class PurchaseDAOImpl extends GenericDAOImpl<Purchase, Integer> implements PurchaseDAO {

	private static final long serialVersionUID = 1L;

	public PurchaseDAOImpl(Class<Purchase> persistentClass) {
		super(persistentClass);
	}

	@Override
	public void insert(Purchase purchase){
		purchase.setPurchDate(new Date());
		getSqlSession().insert("PURCHASE.insert", purchase);
	}

	@Override
	public List<Purchase> getAllPurchasesForDeal(Integer dealId) {
		@SuppressWarnings("unchecked")
		List<Purchase> list = getSqlSession().selectList("PURCHASE.getAllPurchasesForDeal", dealId);
		return list;
	}

	@Override
	public List<Credit> getCreditsUsedByPurchase(Integer purId) {
		@SuppressWarnings("unchecked")
		List<Credit> list = getSqlSession().selectList("PURCHASE.getCreditsUsedByPurchase", purId);
		return list;
	}

	@Override
	public void setCreditUsedByPurchase(Integer purId, Integer creditId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("purId", purId);
        param.put("creditId", creditId);
		getSqlSession().insert("PURCHASE.setCreditUsedByPurchase", param);
	}

	@Override
	public List<Purchase> search(SearchPurchaseBean sb) {
		@SuppressWarnings("unchecked")
		List<Purchase> list = getSqlSession().selectList("PURCHASE.search", sb);
		return list;
	}

	@Override
	public List<Purchase> getByCustomer(Integer cusId) {
		@SuppressWarnings("unchecked")
		List<Purchase> list = getSqlSession().selectList("PURCHASE.getByCustomer", cusId);
		return list;
	}

	@Override
	public int searchCount(SearchPurchaseBean sb) {
		int totalCount = (Integer) getSqlSession().selectOne("PURCHASE.searchCount", sb);
		return totalCount;
	}

	@Override
	public List<ResultStatisticBean> getPurchasesGroupedByMonths() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("PURCHASE.getPurchasesGroupedByMonths", null);
		return list;
	}

	@Override
	public List<ResultStatisticBean> getTotAmountGroupedByMonths() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("PURCHASE.getTotAmountGroupedByMonths", null);
		return list;
	}

	@Override
	public List<ResultStatisticBean> getEarningsGroupedByMonths() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("PURCHASE.getEarningsGroupedByMonths", null);
		return list;
	}

	@Override
	public Purchase getPurchaseByOrderId(Integer orderId) {
		Purchase record = (Purchase) getSqlSession().selectOne("PURCHASE.getPurchaseByOrderId", orderId);
		return record;
	}

	@Override
	public void setPurchaseAsNotConfirmed(Integer purId) {
		getSqlSession().update("PURCHASE.setPurchaseAsNotConfirmed", purId);
	}

	@Override
	public List<ResultStatisticBean> getPurchasesByCount() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("PURCHASE.getPurchasesByCount", null);
		return list;
	}

	@Override
	public Purchase getPurchaseForCoupon(String code) {
		Purchase record = (Purchase) getSqlSession().selectOne("PURCHASE.getPurchaseForCoupon", code);
		return record;
	}

	@Override
	public void setFeedbackRequestedForPurchase(Integer purId, boolean isFeedbackReq) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purId", purId);
		map.put("feedbackRequested", isFeedbackReq);
		getSqlSession().update("PURCHASE.setFeedbackRequestedForPurchase", map);
	}

	@Override
	public boolean isPurchaseUsed(Integer purId) {
		Integer record = (Integer) getSqlSession().selectOne("PURCHASE.countPurchaseUsed", purId);
		if(record > 0){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public void changePurchaseCustomer(Purchase p) {
		getSqlSession().update("PURCHASE.changePurchaseCustomer", p);
	}

	@Override
	public Integer getSumTotAmount() {
		int totalCount = (Integer) getSqlSession().selectOne("PURCHASE.getSumTotAmount", null);
		return totalCount;
	}

	@Override
	public Integer getSumTotEarning() {
		int totalCount = (Integer) getSqlSession().selectOne("PURCHASE.getSumTotEarning", null);
		return totalCount;
	}

	@Override
	public Integer getTotBuyers() {
		int totalCount = (Integer) getSqlSession().selectOne("PURCHASE.getTotBuyers", null);
		return totalCount;
	}

	@Override
	public Integer getTotCouponsSold() {
		int totalCount = (Integer) getSqlSession().selectOne("PURCHASE.getTotCouponsSold", null);
		return totalCount;
	}

}
