package al.ozone.bl.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.bean.SearchDealChoiceBean;
import al.ozone.bl.bean.SearchDealPurchasesBean;
import al.ozone.bl.bean.SearchDealReportBean;
import al.ozone.bl.dao.CityDAO;
import al.ozone.bl.dao.CodeGeneratorDAO;
import al.ozone.bl.dao.CouponDAO;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.dao.DealChoiceDAO;
import al.ozone.bl.dao.DealDAO;
import al.ozone.bl.dao.PurchaseDAO;
import al.ozone.bl.dao.UserDAO;
import al.ozone.bl.exception.FileAlreadyExistException;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.DealPurchasesReport;
import al.ozone.bl.model.DealReport;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Invite;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.SystemConfigBean;
import al.ozone.bl.utils.ZUtils;

public class DealManagerImpl implements DealManager, Serializable {

	private static final long serialVersionUID = 4175919082175966754L;
	protected static final transient Log logger = LogFactory.getLog(DealManagerImpl.class);
	
	private DealDAO dealDAO;
	private DealChoiceDAO dealChoiceDAO;
	private CodeGeneratorDAO codeGeneratorDAO;
	private CouponDAO couponDAO;
	private UserDAO userDAO;
	private PurchaseDAO purchaseDAO;
	private CreditDAO creditDAO;
	private CityDAO cityDAO;
	private InviteManager inviteManager;
	private ApplicationConfig applicationConfig;

	public DealDAO getDealDAO() {
		return dealDAO;
	}
	public void setDealDAO(DealDAO dealDAO) {
		this.dealDAO = dealDAO;
	}
	public void setDealChoiceDAO(DealChoiceDAO dealChoiceDAO) {
		this.dealChoiceDAO = dealChoiceDAO;
	}
	public Deal get(Integer id) {
		return dealDAO.get(id);
	}
	public void setCodeGeneratorDAO(CodeGeneratorDAO codeGeneratorDAO) {
		this.codeGeneratorDAO = codeGeneratorDAO;
	}
	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	public void setCreditDAO(CreditDAO creditDAO) {
		this.creditDAO = creditDAO;
	}
	
	public void insert(Deal deal) throws Exception {
		Date now = new Date();		
		deal.setLastUpdate(now);
		//deal.setLastUpdateUser(userManager.getLoggedUsername());
		//logger.debug("Inserting deal:"+deal);
		
		//1.insert the deal/contract
		try {			
			dealDAO.insert(deal);
		} catch (Exception e) {
			logger.error("Deal "+deal.getId()+" can not be inserted.", e);
		}
		
		//2. insert all dealchoices if present
		try {	
			if(!ZUtils.isEmptyList(deal.getChoices())){
				for (DealChoice dc : deal.getChoices()) {
					dc.setDealId(deal.getId());
					dealChoiceDAO.insert(dc);
				}
			}
		} catch (Exception e) {
			logger.error("Choices of deal "+deal.getId()+" can not be inserted", e);
		}
		
		//3.insert publication cities
		try {
			if(!ZUtils.isEmptyList(deal.getDealCities())){
				for (String c : deal.getDealCities()) {
					cityDAO.insertCityForDeal(deal.getId(), c);
				}
			}
		} catch (Exception e) {
			logger.error("Publication cities of deal "+deal.getId()+" can not be inserted", e);
		}
		
		//4. reference for uploaded files
		try {
			if(!ZUtils.isEmptyList(deal.getUploadedFiles())){
				for (String fileName : deal.getUploadedFiles()) {
					dealDAO.uploadFileForDeal(deal.getId(), fileName);
				}
			}
		} catch (Exception e) {
			logger.error("Name of file can not be associated to deal "+deal.getId(), e);
		}
		
		
	}

//	public void update(Deal deal) throws Exception {
//		logger.debug("Updating deal:"+deal);
//		
////		for (City c : deal.getDealCities()) {
////			cityDAO.insertCityForDeal(deal.getId(), c.getId());
////		}
//		//TODO only TR for the moment
//		//cityDAO.insertCityForDeal(deal.getId(), "TR");
//		dealDAO.update(deal);
//	}

	public void delete(Deal deal) throws Exception {
		//logger.debug("Deleting deal:"+deal);
		cityDAO.deleteCitiesForDeal(deal.getId());
		deleteDealChoicesForDeal(deal.getId());
		dealDAO.delete(deal);
	}

	public List<Deal> getAll() {
		return dealDAO.getAll();
	}
	
	public List<Deal> search(Deal deal) {
		return dealDAO.search(deal);
	}
	
	public List<Deal> search(SearchDealBean searchDealBean) {
		return dealDAO.search(searchDealBean);
	}

	public void updateDataDeal(Deal deal) {
		Date now = new Date();
		deal.setLastUpdate(now);
		//deal.setLastUpdateUser(userManager.getLoggedUsername());
		//logger.debug("Updating deal:"+deal);
		dealDAO.updateDataDeal(deal);
		
	}
	
	public void updateSynthConditions(Deal deal) {
		Date now = new Date();
		deal.setLastUpdate(now);
		//deal.setLastUpdateUser(userManager.getLoggedUsername());
		//logger.debug("Updating deal:"+deal);
		dealDAO.updateSynthConditions(deal);
	}
	
	public void updateDescription(Deal deal) {
		Date now = new Date();
		deal.setLastUpdate(now);
		//deal.setLastUpdateUser(userManager.getLoggedUsername());
		//logger.debug("Updating deal:"+deal);
		dealDAO.updateDescription(deal);	
	}
	
	public void updateImageAndApproval(Deal deal) {
		Date now = new Date();
		deal.setLastUpdate(now);
		//deal.setLastUpdateUser(userManager.getLoggedUsername());
		//logger.debug("Updating deal:"+deal);
		dealDAO.updateImageAndApproval(deal);
	}
	
	public List<String> getUploadedFiles(Integer id) {
		return dealDAO.getUploadedFiles(id);
	}
	
	/**
	 * Associate the given file to the given deal and update the file physically 
	 * on the server.
	 * @param idDeal deal id
	 * @param fileName name of file to upload on the server for the give deal
	 * @param in Input Stream of the file to upload
	 * @return full path name for the uploaded file
	 * @throws FileAlreadyExistException in case another file with same name
				is present on the server
	 */
	public String uploadFileForDeal(Integer idDeal, String fileName, String path, InputStream in) throws FileAlreadyExistException{
		dealDAO.uploadFileForDeal(idDeal, fileName);
		String fullPath = null;
		try{
			fullPath = ZUtils.uploadOnServer(in, fileName, path);
		}catch(IOException e){
			throw new RuntimeException("IOException during uploading file on server. Converted in RuntimeException to avoid commit.");
		}
		return fullPath;
	}

	/**
	 * Disassociate the file from the given deal and remove the file 
	 * physically from the server.
	 * 
	 * @param dealId deal id
	 * @param fileName filename do delete
	 * @param path path, in the file system server, where the file is located 
	 * @return true if the file has been removed, false otherwise
	 */
	public boolean removeFileForDeal(Integer dealId, String fileName, String path) {
		dealDAO.removeFileForDeal(dealId, fileName);
		return ZUtils.removeFromServer(path, fileName);
	}
	
	@Override
	public List<Deal> getApprovedDeals() {
		return dealDAO.getApprovedDeals();
	}
	@Override
	public List<Deal> getActiveDeals(String category) {
		return dealDAO.getActiveDeals(category);
	}
	@Override
	public List<DealChoice> searchDealChoice(SearchDealChoiceBean sb) {
		return dealChoiceDAO.searchDealChoice(sb);
	}
	@Override
	public List<DealChoice> getActiveDealsChoice() {
		return dealChoiceDAO.getActiveDealsChoice();
	}
	@Override
	public DealChoice getDealChoice(Integer dealId, Integer choiceNr) {
		return dealChoiceDAO.get(dealId, choiceNr);
	}
	@Override
	public void setDealAsConfirmed(Integer id) {
		dealDAO.setDealAsConfirmed(id);
	}
	
	/**
	 * Create the coupons of the specified purchase.
	 * After creating the coupons, prepare the Email to send to the customer.
	 * Add credit to the inviter, if the customer has been invited from another customer
	 * 
	 * @param dc DealChoice to which the purchased belongs to.
	 * @param purchase Purchase for which to create the coupons.
	 * @return prepared Email ready to be sent
	 * @see #prepareCoupons(dc)
	 */
	public Email prepareCouponsForPurchase(DealChoice dc, Purchase purchase) {
		// Creates as many coupons as quantity, and save their html link
		// in a list for later showing in the email.
		int qty = purchase.getQuantity();
		List<Coupon> couponList = new ArrayList<Coupon>();
		Coupon c;
		for (int i = 0; i < qty; i++) {
			//byte[] pdfCouponData = generatePdfCoupon(p, couponCode, securityCode);
			//File pdfFile = new File("");//pdfCouponData
			
			String couponCode = codeGeneratorDAO.generateCouponCode();
			String securityCode = codeGeneratorDAO.generateNextSecurityCode();
			String htmlCouponLink = generateHtmlCoupon(dc, couponCode);
			
			c = new Coupon();
			c.setCode(couponCode);
			c.setSecurityCode(securityCode);
			c.setFrom(ZUtils.getDateAsDDMMYYYY(new Date()));
			c.setTo(dc.getDeal().getExpirationDiscount());
			c.setStatus(Coupon.STATUS_NOT_USED);
			//c.setPdfData(pdfCouponData);
			c.setPurchaseId(purchase.getId());
			c.setHtmlLink(htmlCouponLink);
			couponDAO.insert(c);
			logger.debug("Inserted coupon "+couponCode+" in DB.");
			
			couponList.add(c);
		}
		
        // Add credit to inviter, if any
        addCreditToInviter(purchase);
		
		String cusEmail = purchase.getCustomer().getEmail();		
		Email email = new Email("DealConfirmed");
		email.setSubject("OZone - Konferme Blerje");
        email.addTo(cusEmail);
        email.addParameter("dc", dc);
        email.addParameter("couponList", couponList);
        // email.addAttachment(pdfFile);
        logger.info("Prepared Email for customer "+cusEmail+" with "+couponList.size()+ " coupons (deal: "+dc.getChoiceTitle() +", dealId:"+dc.getDealId()+")");

		return email;
	}
	
	/**
	 * If the customer of the present purchase has been invited in precedence from
	 * another customer, add credit to the inviter
	 * @param purchase
	 */
	private void addCreditToInviter(Purchase purchase){
		Integer invitedId = purchase.getCustomer().getId(); //invited
		String invitedEmail = purchase.getCustomer().getEmail();
		Integer invitedBy = inviteManager.getWhoInvitedCustomer(invitedId);//inviter
		if(invitedBy!=null){
			int defCreditVal = applicationConfig.getBonusValue();
			int creditDuration = applicationConfig.getCreditDuration();
			Credit newCredit = new Credit();
			newCredit.setAssignedDate(new Date());
			newCredit.setValidDate(ZUtils.addMonthsToDate(new Date(), creditDuration)); 
			newCredit.setValue(defCreditVal);
			newCredit.setType(Credit.TYPE_BENEFIT);
			newCredit.setCustomer(new Customer(invitedBy));
			newCredit.setAbout("Shtim "+defCreditVal+" Lek per ftesen e perdoruesit "+invitedEmail+" ("+invitedId+")");
			
			Invite invite = new Invite();
			invite.setInviterId(invitedBy);
			invite.setInvitedId(invitedId);
			invite.setInvitedEmail(invitedEmail);
			inviteManager.insertCreditToInviter(newCredit, invite);
		}
	}
	
	private String generateHtmlCoupon(DealChoice dc, String couponCode) {
		//TODO
		return "www.ozone.al";
	}
	@Override
	public List<Deal> getAllDealsForCity(String cityId, boolean onlyActives) {
		return dealDAO.getAllDealsForCity(cityId, onlyActives);
	}
	
	@Override
	public List<Deal> getDealsNotPublished() {
		return dealDAO.getDealsNotPublished();
	}
	@Override
	public void changeOrder(int dealId, int newOrder) {
		dealDAO.changeOrder(dealId, newOrder);
	}
	@Override
	public Deal getFakeDeal() {
		Deal d = dealDAO.get(1);
		return d;
	}
	@Override
	public List<Deal> getAllClosedDeals() {
		return dealDAO.getAllClosedDeals();
	}
	@Override
	public List<DealChoice> getChoicesForDeal(Integer dealId) {
		return dealChoiceDAO.getChoicesForDeal(dealId);
	}
	@Override
	public DealChoice getFullDealChoice(Integer dealId, Integer choiceNr) {
		return dealChoiceDAO.getFullDealChoice(dealId, choiceNr);
	}

	/**
	 * Get all not closed deals from DB and change their statuses as follows:<br>
	 * (W)aiting -> (A)ctive  <br>
	 * (A)ctive -> (C)losed
	 */
	@Override
	public void changeDealsStatus() {
		List<Deal> activesList = this.getDealsEndToday();
		dealDAO.changeDealsStatusTo(activesList, Deal.STATUS_CLOSED);
		String ids = ZUtils.getDealIdsFromList(activesList);
		logger.info("Closed "+activesList.size()+" deals. ("+ids+")");
		for (Deal d : activesList) {
			logger.info("Deal"+d.getId()+" - "+d.getTitle() +" with "+d.getTotPurchases()+ " coupons sold.");
		}
		
		List<Deal> waitingsList = this.getDealsStartToday();
		dealDAO.changeDealsStatusTo(waitingsList, Deal.STATUS_ACTIVE);
		ids = ZUtils.getDealIdsFromList(waitingsList);
		logger.info("Opened "+waitingsList.size()+" deals. ("+ids+")");
		for (Deal d : waitingsList) {
			logger.info("Deal"+d.getId()+" - "+d.getTitle());
		}
	}

	@Override
	public List<Deal> getDealsEndToday() {
		Date endDate = new Date();
		endDate = ZUtils.addDaysToDate(endDate, -1);
		return dealDAO.getDealsEndToday(endDate);
	}
	@Override
	public List<Deal> getDealsStartToday() {
		Date startDate = new Date();
		return dealDAO.getDealsStartToday(startDate);
	}
	
	/**
	 * Prepare all coupons for the give deal.
	 * 
	 * @param d deal for which creates the coupons and email to be sent.
	 * @return list with all emails to be sent to each customer whom purchased 
	 * 		the deal.
	 * @see #prepareCouponsForPurchase(Deal, Purchase)
	 */
	@Override
	public List<Email> prepareCoupons(Deal deal) {
		List<Email> emailList = new ArrayList<Email>();
		
		//Send email only if not done in precedence. CouponsPrepared means the coupons are
		//generated in DB for this deal. Email sent or not is not handled here!
		if(deal.isCouponsPrepared()){
			logger.info("All coupons of deal "+deal.getId()+" (deal: "+deal.getTitle()+") has ALREADY been prepared and sent to customers.");
		}else{
			//if the coupons are prepared on each purchase do nothing. Only send coupon list to admins
			if(deal.isCouponImmediately()){
				logger.info("Coupons for Deal: "+deal.getId()+" ("+deal.getTitle()+") are created anticipately on each purchase! No email will be send to customers!");
				
				// Notification to admins with coupons prepared
				List<Coupon> couponList = couponDAO.getAllCouponsForDeal(deal.getId());
				Email email = new Email("CouponsList");
				email.setSubject("OZone - "+deal.getPartner().getName()+" - Lista e kuponave");
				List<String> adminEmails = ZUtils.getEmailsFromUsers(userDAO.getUsersByRoleId(Role.ROLE_ADMIN));
		        email.addTo(adminEmails);
		        email.addParameter("deal", deal);
		        email.addParameter("couponList", couponList);
		        emailList.add(email);
		        logger.debug("Coupons List ready to send to admins:"+adminEmails);
			}else{
				//if the coupons are going to be prepared now, check if minimum number of purchases has been reached
				boolean isConfirmed = deal.getMinCustomers() <= deal.getTotPurchases();
				dealDAO.setDealConfirmed(deal.getId(), isConfirmed);
				
				//If confirmed send the coupons to customers, else send notification to customers
				if(isConfirmed){
					//1. coupons to customers
					logger.info("Deal "+deal.getId()+" ("+deal.getTitle()+") confirmed.");
					
					//Get the list of all purchases for the given deal
					List<Purchase> purchList = purchaseDAO.getAllPurchasesForDeal(deal.getId());
					logger.info(purchList.size()+" Purchases for Deal "+deal.getId());
	
					//appends to emailList the email of each purchase
					int totCoupons = 0;
					for (Purchase purchase : purchList) {
						if(purchase.isConfirmed()){
							emailList.add(prepareCouponsForPurchase(purchase.getDealChoice(), purchase));
							totCoupons = totCoupons + purchase.getQuantity();
						} else {
							logger.error("**Purchase "+purchase.getId() + " is not confirmed so the coupons will not be generated");
						}						
					}
					logger.info(totCoupons+" Coupons prepared and ready to send for deal "+deal.getId());
					
					//2. Notification to admins with coupons prepared
					List<Coupon> couponList = couponDAO.getAllCouponsForDeal(deal.getId());
					Email email = new Email("CouponsList");
					email.setSubject("OZone - "+deal.getPartner().getName()+" - Lista e kuponave");
					List<String> adminEmails = ZUtils.getEmailsFromUsers(userDAO.getUsersByRoleId(Role.ROLE_ADMIN));
			        email.addTo(adminEmails);
			        email.addParameter("deal", deal);
			        email.addParameter("couponList", couponList);
			        emailList.add(email);
			        logger.debug("Coupons List ready to send to admins:"+adminEmails);
				}else{
					logger.info("Deal "+deal.getId()+" ("+deal.getTitle()+") not confirmed. Preparing Email notification for all customers that purchased the deal");
					if(deal.getTotPurchases()!=0){
						//1. Give back the credits used on each purchase
						//2. Send a notification email to admins to remind to give back the money used  
						List<String> cusList = dealDAO.getAllCustomerEmailsForDeal(deal.getId());
						for (String cusEmail : cusList) {
							Email email = new Email("DealNotConfirmed");
							email.setSubject("OZone - Oferta nuk u plotesua");
			                email.addTo(cusEmail);
			                email.addParameter("deal", deal);
							emailList.add(email);
						}
		                logger.debug("Prepared email for the list:"+cusList);
		                
		                List<String> adminEmails = ZUtils.getEmailsFromUsers(userDAO.getUsersByRoleId(Role.ROLE_ADMIN));
						for (String ad : adminEmails) {
							Email email = new Email("DealNotConfirmedForAdmin");
							email.setSubject("OZone - "+deal.getPartner().getName()+" nuk u konfermua. Leket per te kthyer mbrapa!");
			                email.addTo(ad);
			                email.addParameter("deal", deal);
							emailList.add(email);
						}
		                logger.debug("Prepared email notification for admins:"+adminEmails);
		                
		                //Give back all credits used on purchases for not confirmed publication
						List<Purchase> purchList = purchaseDAO.getAllPurchasesForDeal(deal.getId());
						for (Purchase pur : purchList) {
							int creditVal = pur.getCreditSpent();
							if(creditVal > 0){
								Credit newCredit = new Credit();
								newCredit.setAssignedDate(new Date());
								newCredit.setValidDate(ZUtils.addMonthsToDate(new Date(), 6));//TODO get the default duration from db 
								newCredit.setValue(creditVal);
								newCredit.setType(Credit.TYPE_REIMBORSEMENT);//
								newCredit.setCustomer(pur.getCustomer());
								newCredit.setAbout("Anullim i blerjes "+pur.getId()+". Kthimi mbrapa i krediteve");
								creditDAO.insert(newCredit);
								logger.info("Restored credit with value "+creditVal+" to customer "+pur.getCustomer().getEmail());
							}else{
								logger.info("No credit used by customer: "+pur.getCustomer().getEmail());
							}
						}
					}else{
						logger.info("Deal "+deal.getId()+" ("+deal.getTitle()+") has 0 purchases, no email will be sent.");
					}
				}
			}
				
			//Set coupons prepared for this deal, to avoid sending email more than
			// 1 time to customer.
			dealDAO.setCouponsPreparedForDeal(deal.getId(), true);
		}
		
		return emailList;
	}
	
	@Override
	public List<Deal> getTodayExpiringDeals() {
		return dealDAO.getTodayExpiringDeals();
	}
	
	@Override
	public List<Deal> getDealsAlreadyActive() {
		return dealDAO.getDealsAlreadyActive();
	}
	@Override
	public List<Newsletter> getDealsStartTodayGroupedByCity() {
		return dealDAO.getDealsStartTodayGroupedByCity();
	}
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}
	@Override
	public void insertDealChoice(DealChoice dc) {
		dealChoiceDAO.insert(dc);
	}
	@Override
	public void updateDealChoice(DealChoice dc) {
		dealChoiceDAO.update(dc);
	}
	@Override
	public void deleteDealChoice(DealChoice dc) {
		dealChoiceDAO.delete(dc);
	}
	@Override
	public void updateDealInCalendar(Deal deal) {
		dealDAO.updateDealInCalendar(deal);
	}
	@Override
	public List<SystemConfigBean> getActiveDealsGroupedByPurchases() {
		return dealDAO.getActiveDealsGroupedByPurchases();
	}
	@Override
	public List<ResultStatisticBean> getDealsGroupByPubDate() {
		return dealDAO.getDealsGroupByPubDate();
	}
	@Override
	public void deleteDealChoicesForDeal(Integer dealId) {
		dealChoiceDAO.deleteDealChoicesForDeal(dealId);
	}
	@Override
	public List<DealReport> searchDealReport(SearchDealReportBean sb) {
		return dealDAO.searchDealReport(sb);
	}
	@Override
	public void changeAccountedDeal(Integer dealId, boolean isAccounted) {
		dealDAO.changeAccountedDeal(dealId, isAccounted);
	}
	@Override
	public List<ContratPublicationBean> getContratsGroupByMonths(SearchDealBean sb) {
		return dealDAO.getContratsGroupByMonths(sb);
	}
	@Override
	public List<ResultStatisticBean> getDealsGroupByCategory(SearchDealBean sb) {
		return dealDAO.getDealsGroupByCategory(sb);
	}
	@Override
	public List<Customer> getAllCustomersPurchasedDeal(Integer dealId) {
		return dealDAO.getAllCustomersPurchasedDeal(dealId);
	}
	@Override
	public List<Deal> getAllDealsForPartner(Integer partnerId) {
		return dealDAO.getAllDealsForPartner(partnerId);
	}
	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
	}
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
	@Override
	public Integer getAllClosedDealsCount() {
		return dealDAO.getAllClosedDealsCount();
	}
	
	@Override
	public Integer getTotActiveDeals() {
		return dealDAO.getTotActiveDeals();
	}
	@Override
	public List<DealPurchasesReport> searchDealPurchases(SearchDealPurchasesBean sb) {
		return dealDAO.searchDealPurchases(sb);
	}
	
}
