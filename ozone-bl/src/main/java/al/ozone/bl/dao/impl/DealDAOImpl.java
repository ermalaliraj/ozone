package al.ozone.bl.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.bean.SearchDealPurchasesBean;
import al.ozone.bl.bean.SearchDealReportBean;
import al.ozone.bl.dao.DealDAO;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealPurchasesReport;
import al.ozone.bl.model.DealReport;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.model.SystemConfigBean;

@SuppressWarnings("serial")
public class DealDAOImpl extends GenericDAOImpl<Deal, Integer> implements DealDAO{

	protected static final transient Log logger = LogFactory.getLog(DealDAOImpl.class);

	public DealDAOImpl(Class<Deal> persistentClass) {
		super(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<Deal> search(SearchDealBean searchDealBean) {
		List<Deal> list = getSqlSession().selectList("DEAL.search", searchDealBean);
        return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUploadedFiles(Integer dealId) {
		List<String> list = getSqlSession().selectList("DEAL.getUploadedFiles", dealId);
		return list;
	}

	public void uploadFileForDeal(Integer dealId, String fileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileName", fileName);
		map.put("dealId", dealId);
        getSqlSession().insert("DEAL.uploadFileForDeal", map);
        //logger.debug("Associated file "+fileName+" to deal "+dealId);
	}

	public void removeFileForDeal(Integer dealId, String fileName) {
		//logger.debug("Going to remove file "+fileName+" for deal "+dealId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileName", fileName);
		map.put("dealId", dealId);
        getSqlSession().delete("DEAL.removeFileForDeal", map);		
	}

	public void updateDataDeal(Deal deal) {
		getSqlSession().update("DEAL.updateDataDeal", deal);
	}
	
	public void updateSynthConditions(Deal deal) {
		getSqlSession().update("DEAL.updateSynthConditions", deal);
	}
	
	public void updateDescription(Deal deal) {
		getSqlSession().update("DEAL.updateDescription", deal);
	}
	
	public void updateImageAndApproval(Deal deal) {
		getSqlSession().update("DEAL.updateImageAndApproval", deal);
	}

	@Override
	public List<Deal> getApprovedDeals() {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getApprovedDeals", null);
		return list;
	}

	@Override
	public void setDealAsPublished(Integer id, boolean isPublished) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isPublished", isPublished);
		getSqlSession().update("DEAL.setDealAsPublished", map);
	}

	@Override
	public List<Deal> getActiveDeals(String category) {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getActiveDeals", map);
		return list;
	}

	@Override
	public void setDealAsConfirmed(Integer id) {
		getSqlSession().update("DEAL.setDealAsConfirmed", id);
	}

	@Override
	public void increasePurchases(Purchase purchase) {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", purchase.getDealChoice().getDealId());
		map.put("totPurchase", purchase.getQuantity());
		getSqlSession().update("DEAL.increasePurchases", map);
	}

	@Override
	public List<Deal> getAllDealsForCity(String cityId, boolean onlyActives) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", cityId);
		map.put("onlyActives", onlyActives);
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getAllDealsForCity", map);
		return list;
	}

	@Override
	public List<Deal> getDealsNotPublished() {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getDealsNotPublished", null);
		return list;
	}

	@Override
	public void changeOrder(int dealId, int newOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("newOrder", newOrder);
		getSqlSession().update("DEAL.changeOrder", map);
	}

	@Override
	public List<Deal> getAllClosedDeals() {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getAllClosedDeals", null);
		return list;
	}

	@Override
	public List<Deal> getDealsEndToday(Date endDate) {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getDealsEndToday", endDate);
		return list;
	}
	@Override
	public List<Deal> getDealsStartToday(Date startDate) {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getDealsStartToday", startDate);
		return list;
	}
	@Override
	public void changeDealsStatusTo(List<Deal> list, String newStatus) {
		if(list.size()!=0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("status", newStatus);
			getSqlSession().update("DEAL.changeDealStatusTo", map);
		}
	}

	@Override
	public void setCouponsPreparedForDeal(Integer dealId, boolean couponsPrepared) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("couponsPrepared", couponsPrepared);
		getSqlSession().update("DEAL.setCouponsPreparedForDeal", map);
	}

	@Override
	public List<String> getAllCustomerEmailsForDeal(Integer dealId) {
		@SuppressWarnings("unchecked")
		List<String> list = getSqlSession().selectList("DEAL.getAllCustomerEmailsForDeal", dealId);
		return list;
	}

	@Override
	public List<Deal> getTodayExpiringDeals() {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getTodayExpiringDeals", null);
		return list;
	}

	@Override
	public void setDealConfirmed(Integer dealId, boolean isConfirmed) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("confirmed", isConfirmed);
		getSqlSession().update("DEAL.setDealConfirmed", map);
	}
	
	@Override
	public List<Deal> getDealsAlreadyActive() {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getDealsAlreadyActive", null);
		return list;
	}

	@Override
	public List<Newsletter> getDealsStartTodayGroupedByCity() {
		@SuppressWarnings("unchecked")
		List<Newsletter> list = getSqlSession().selectList("DEAL.getDealsStartTodayGroupedByCity", null);
		return list;
	}

	@Override
	public void updateDealInCalendar(Deal deal) {
		getSqlSession().update("DEAL.updateDealInCalendar", deal);
	}

	@Override
	public List<SystemConfigBean> getActiveDealsGroupedByPurchases() {
		@SuppressWarnings("unchecked")
		List<SystemConfigBean> list = (List<SystemConfigBean>)getSqlSession().selectList("DEAL.getActivePubsGroupedByPurchases", null);
		return list;
	}

	@Override
	public List<ResultStatisticBean> getDealsGroupByPubDate() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = (List<ResultStatisticBean>)getSqlSession().selectList("DEAL.getDealsGroupByPubDate", null);
		return list;
	}

	@Override
	public List<DealReport> searchDealReport(SearchDealReportBean sb) {
		@SuppressWarnings("unchecked")
		List<DealReport> list = (List<DealReport>) getSqlSession().selectList("DEAL.searchDealReport", sb);
		return list;
	}

	@Override
	public void changeAccountedDeal(Integer dealId, boolean isAccounted) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("isAccounted", isAccounted);
		getSqlSession().update("DEAL.changeAccountedDeal", map);
	}

	@Override
	public List<ContratPublicationBean> getContratsGroupByMonths(SearchDealBean sb) {
		@SuppressWarnings("unchecked")
		List<ContratPublicationBean> list = (List<ContratPublicationBean>) getSqlSession().selectList("DEAL.getContratsGroupByMonths", sb);
		return list;
	}

	@Override
	public List<ResultStatisticBean> getDealsGroupByCategory(SearchDealBean sb) {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("DEAL.getDealsGroupByCategory", sb);
		return list;
	}

	@Override
	public List<Customer> getAllCustomersPurchasedDeal(Integer dealId) {
		@SuppressWarnings("unchecked")
		List<Customer> list = getSqlSession().selectList("DEAL.getAllCustomersPurchasedDeal", dealId);
		return list;
	}

	@Override
	public List<Deal> getAllDealsForPartner(Integer partnerId) {
		@SuppressWarnings("unchecked")
		List<Deal> list = getSqlSession().selectList("DEAL.getAllDealsForPartner", partnerId);
		return list;
	}

	@Override
	public Integer getAllClosedDealsCount() {
		int totalCount = (Integer) getSqlSession().selectOne("DEAL.getAllClosedDealsCount", null);
		return totalCount;
	}

	@Override
	public Integer getTotActiveDeals() {
		int totalCount = (Integer) getSqlSession().selectOne("DEAL.getTotActiveDeals", null);
		return totalCount;
	}

	@Override
	public List<DealPurchasesReport> searchDealPurchases(SearchDealPurchasesBean sb) {
		@SuppressWarnings("unchecked")
		List<DealPurchasesReport> list = getSqlSession().selectList("DEAL.searchDealPurchases", sb);
		return list;
	}

}
