package al.ozone.bl.dao;

import java.util.Date;
import java.util.List;

import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.bean.SearchDealPurchasesBean;
import al.ozone.bl.bean.SearchDealReportBean;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealPurchasesReport;
import al.ozone.bl.model.DealReport;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.model.SystemConfigBean;

public interface DealDAO extends GenericDAO<Deal, Integer>{

	public List<Deal> search(SearchDealBean searchDealBean);
	
	public List<String> getUploadedFiles(Integer dealId);
	
	public void uploadFileForDeal(Integer dealId, String fileName);

	public void removeFileForDeal(Integer dealId, String fileName);

	public void updateDataDeal(Deal deal);
	
	public void updateSynthConditions(Deal d);

	public void updateDescription(Deal d);

	public void updateImageAndApproval(Deal d);

	public List<Deal> getApprovedDeals();

	public void setDealAsPublished(Integer id, boolean isPublished);

	public List<Deal> getActiveDeals(String category);

	public void setDealAsConfirmed(Integer id);

	public void increasePurchases(Purchase purchase);

	public List<Deal> getAllDealsForCity(String cityId, boolean onlyActives);

	public List<Deal> getDealsNotPublished();

	public void changeOrder(int dealId, int newOrder);

	public List<Deal> getAllClosedDeals();

	public List<Deal> getDealsEndToday(Date endDate);
	public List<Deal> getDealsStartToday(Date startDate);
	public void changeDealsStatusTo(List<Deal> list, String newStatus);
	public void setCouponsPreparedForDeal(Integer dealId, boolean couponsPrepared);
	public List<String> getAllCustomerEmailsForDeal(Integer id);
	public List<Deal> getTodayExpiringDeals();
	public void setDealConfirmed(Integer dealId, boolean isConfirmed);
	public List<Deal> getDealsAlreadyActive();
	public List<Newsletter> getDealsStartTodayGroupedByCity();
	public void updateDealInCalendar(Deal deal);
	public List<SystemConfigBean> getActiveDealsGroupedByPurchases();
	public List<ResultStatisticBean> getDealsGroupByPubDate();
	public List<DealReport> searchDealReport(SearchDealReportBean sb);
	public void changeAccountedDeal(Integer dealId, boolean isAccounted);

	public List<ContratPublicationBean> getContratsGroupByMonths(SearchDealBean sb);
	public List<ResultStatisticBean> getDealsGroupByCategory(SearchDealBean sb);

	public List<Customer> getAllCustomersPurchasedDeal(Integer dealId);
	public List<Deal> getAllDealsForPartner(Integer partnerId);

	public Integer getAllClosedDealsCount();
	public Integer getTotActiveDeals();
	public List<DealPurchasesReport> searchDealPurchases(SearchDealPurchasesBean sb);

}
