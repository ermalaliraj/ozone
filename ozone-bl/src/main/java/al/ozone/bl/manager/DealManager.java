package al.ozone.bl.manager;

import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.bean.SearchDealChoiceBean;
import al.ozone.bl.bean.SearchDealPurchasesBean;
import al.ozone.bl.bean.SearchDealReportBean;
import al.ozone.bl.exception.FileAlreadyExistException;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.DealPurchasesReport;
import al.ozone.bl.model.DealReport;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.model.SystemConfigBean;

public interface DealManager {
	
	@Secured( { "IS_AUTHENTICATED_ANONYMOUSLY" })
	public Deal get(Integer id);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void insert(Deal deal) throws Exception;
	
//	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
//	public void update(Deal deal) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void delete(Deal deal) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Deal> search(Deal deal);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Deal> search(SearchDealBean deal);
	
	public List<Deal> getAll();

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void updateDataDeal(Deal d);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void updateSynthConditions(Deal d);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH" })
	public void updateDescription(Deal d);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH" })
	public String uploadFileForDeal(Integer idDeal, String fileName, String path, InputStream in)throws FileAlreadyExistException;

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH" })
	public boolean removeFileForDeal(Integer dealId, String fileName, String path);
		
	public List<String> getUploadedFiles(Integer id);

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH"})
	public void updateImageAndApproval(Deal d);

	public List<Deal> getApprovedDeals();

	public List<Deal> getActiveDeals(String category);

	public List<DealChoice> searchDealChoice(SearchDealChoiceBean searchChoiceBean);

	public List<DealChoice> getActiveDealsChoice();

	public DealChoice getDealChoice(Integer dealId, Integer choiceNr);

	public void setDealAsConfirmed(Integer id);

	public List<Deal> getAllDealsForCity(String cityChoosed, boolean onlyActives);

	public List<Deal> getDealsNotPublished();

	public void changeOrder(int dealId, int newOrder);

	public Deal getFakeDeal();

	public List<Deal> getAllClosedDeals();

	public List<DealChoice> getChoicesForDeal(Integer dealId);
	public void deleteDealChoicesForDeal(Integer dealId);

	public DealChoice getFullDealChoice(Integer dealId, Integer choiceNr);

	public List<Deal> getDealsEndToday();
	public List<Deal> getDealsStartToday();
	public void changeDealsStatus();
	public Email prepareCouponsForPurchase(DealChoice dc, Purchase p);
	public List<Email> prepareCoupons(Deal d);
	public List<Deal> getTodayExpiringDeals();
	public List<Deal> getDealsAlreadyActive();
	public List<Newsletter> getDealsStartTodayGroupedByCity();
	
	public void insertDealChoice(DealChoice dc);
	public void updateDealChoice(DealChoice dc);
	public void deleteDealChoice(DealChoice dc);

	public void updateDealInCalendar(Deal deal);
	public List<SystemConfigBean> getActiveDealsGroupedByPurchases();
	public List<ResultStatisticBean> getDealsGroupByPubDate();

	public List<DealReport> searchDealReport(SearchDealReportBean searchBean);
	public void changeAccountedDeal(Integer dealId, boolean isAccounted);

	public List<ContratPublicationBean> getContratsGroupByMonths(SearchDealBean searchBean);
	public List<ResultStatisticBean> getDealsGroupByCategory(SearchDealBean sb);

	public List<Customer> getAllCustomersPurchasedDeal(Integer dealId);
	public List<Deal> getAllDealsForPartner(Integer partnerId);
	
	public Integer getAllClosedDealsCount();
	public Integer getTotActiveDeals();
	public List<DealPurchasesReport> searchDealPurchases(SearchDealPurchasesBean sb);
}
