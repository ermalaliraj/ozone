package al.ozone.admin.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;


import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchPurchaseBean;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Invite;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 */
@ManagedBean(name="purchaseController")
@ViewScoped
public class PurchaseController implements Serializable{

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(PurchaseController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sPurchaseId;

	private HtmlInputText sDealId;
	private HtmlInputText sDealChoiceTitle;
	//private HtmlInputText sPartner;
	private HtmlInputText sCusEmail;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;
    private SelectOneMenu sPayType;
    private HtmlInputText sOrderId;
    private SelectOneMenu  sConfirmed;
    private boolean sNoFake = true;
    
    private List<SelectItem> typeItems;
    
    //data table
	private List<Purchase> purchasesList;
	private Purchase purchaseSelected;
	private List<Coupon> couponsList;
	private List<Credit> creditsList;
	private String couponToCheck;
	private boolean editMode;
	
	// purchase change
	private HtmlInputText sCustomerEmail;
	private HtmlInputTextarea sPaymentNote;
	
	@ManagedProperty(value="#{purchaseManager}") 
	private PurchaseManager purchaseManager;
	@ManagedProperty(value="#{customerManager}") 
	private CustomerManager customerManager;
	@ManagedProperty(value="#{inviteManager}") 
	private InviteManager inviteManager;
	@ManagedProperty(value="#{applicationConfig}") 
	private ApplicationConfig applicationConfig;

	//Page initialization
	@PostConstruct
	public void init(){		
		typeItems = new ArrayList<SelectItem>();
		typeItems.add(new SelectItem(Payment.TYPE_CASH, "Kesh"));
		typeItems.add(new SelectItem(Payment.TYPE_PAYPAL, "PayPal"));
		typeItems.add(new SelectItem(Payment.TYPE_EASY_PAY, "EasyPay"));
		typeItems.add(new SelectItem(Payment.TYPE_BANK, "Bank"));
		
		searchPurchase();
	}

	public void changeCustomer(){
		int cusId = 0;
		String cusEmail = JSFUtils.getStringFromUIInput(sCustomerEmail);
		
		try {
			Customer cus = customerManager.getByEmail(cusEmail);
			if (cus == null){
				JSFUtils.addErrorMessage("Perdoruesi " + cusEmail + " nuk gjendet ne sistem.");
				logger.debug("Perdoruesi " + cusEmail + " nuk gjendet ne sistem.");
			}
			else{
				cusId = cus.getId();
				String loggedUser = ((UserPreferences) JSFUtils.getObjectFromSession("userPreferences")).getLoggedUser();
				String pNote = JSFUtils.getStringFromUIInput(sPaymentNote);
				if (purchaseSelected.getCustomer().getId() == cusId) {
					JSFUtils.addErrorMessage("Blerja nuk mund ti kalohet te njejtit perdorues.");
					logger.debug("Perdoruesi " + loggedUser
							+ " po kalon blerjen te njejtit klient cusId:" + cusId);
				} else {
					// Change the purchase
					String head = "<br /> <b>" + ZUtils.getDateAsString(new Date())
							+ "</b> perdoruesi: " + loggedUser + "<br/>";
					String note = "blerje kaluar nga cus "
							+ purchaseSelected.getCustomer().getId() + " ne cus "
							+ cusId;
					String msg = "<br /> msg: " + pNote + "<br/>";
					Purchase p = new Purchase();
					p.setId(purchaseSelected.getId());
					p.setCustomer(cus);
					p.setPayment(purchaseSelected.getPayment());
					p.setPurchDate(new Date());
	
					// changes customer and date
					String allMsg = head + note + (ZUtils.isEmptyString(pNote) ? "" : msg);
					purchaseManager.changePurchaseCustomer(p, allMsg);
					JSFUtils.addInfoMessage("Blerja " + p.getId()
							+ " u kalua me sukses tek klienti:"
							+ p.getCustomer().getEmail());
					
					addCreditToInviter(p);
					searchPurchase();
				}
			}
		} catch (Exception e) {
			logger.error("Error changing customer " + cusId + " for purchase " + purchaseSelected.getId(), e);
		}
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
	
	public void searchPurchase() {
		SearchPurchaseBean sb = new SearchPurchaseBean();
		sb.setPurId(JSFUtils.getIntegerFromUIInput(sPurchaseId));
		sb.setDealId(JSFUtils.getIntegerFromUIInput(sDealId));
		sb.setDealChoiceTitle(JSFUtils.getStringFromUIInput(sDealChoiceTitle));
		//sb.setPartnerName(JSFUtils.getStringFromUIInput(sPartner));
		sb.setCusEmail(JSFUtils.getStringFromUIInput(sCusEmail));
		sb.setPaymentType(JSFUtils.getStringFromUIInput(sPayType));
		sb.setInsertedFrom(sFrom);
		sb.setInsertedTo(sTo);
		sb.setOrderId(JSFUtils.getIntegerFromUIInput(sOrderId));
		sb.setNoFake(Boolean.valueOf(sNoFake));
		sb.setConfirmed(JSFUtils.getBooleanFromUIInput(sConfirmed));		
		
		//logger.debug("Searching with searchBean:"+sb);
		List<Purchase> list = purchaseManager.search(sb);
		setPurchasesList(list);
		//logger.debug("Got from DB "+list.size()+" purchases");
	}

	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
	    sPurchaseId.setValue(null);
	    sDealId.setValue(null);
	    sDealChoiceTitle.setValue(null);
	    //sPartner.setValue(null);
	    sCusEmail.setValue(null);
		sFrom = null;
	    sTo = null;
	    sToMinDate=null;
	    sPayType.setValue(null);
	    sOrderId.setValue(null);
	    sConfirmed.setValue(null);
	    sNoFake = true;
	}
	
	public void choosePaymentType(){
		
	}
	
	public String checkCoupon(){
		JSFUtils.putObjectInSession("couponToCheck", couponToCheck);
		return "success";
	}
	
	public HtmlInputText getsPurchaseId() {
		return sPurchaseId;
	}
	public void setsPurchaseId(HtmlInputText sPurchaseId) {
		this.sPurchaseId = sPurchaseId;
	}
	public HtmlInputText getsDealId() {
		return sDealId;
	}
	public void setsDealId(HtmlInputText sDealId) {
		this.sDealId = sDealId;
	}
	public HtmlInputText getsDealChoiceTitle() {
		return sDealChoiceTitle;
	}
	public void setsDealChoiceTitle(HtmlInputText sDealChoiceTitle) {
		this.sDealChoiceTitle = sDealChoiceTitle;
	}
	public HtmlInputText getsCusEmail() {
		return sCusEmail;
	}
	public void setsCusEmail(HtmlInputText sCusEmail) {
		this.sCusEmail = sCusEmail;
	}
	public Date getsFrom() {
		return sFrom;
	}
	public void setsFrom(Date sFrom) {
		this.sFrom = sFrom;
	}
	public Date getsTo() {
		return sTo;
	}
	public void setsTo(Date sTo) {
		this.sTo = sTo;
	}
	public List<Purchase> getPurchasesList() {
		return purchasesList;
	}
	public void setPurchasesList(List<Purchase> purchasesList) {
		this.purchasesList = purchasesList;
	}
	public Purchase getPurchaseSelected() {
		return purchaseSelected;
	}
	public void setPurchaseSelected(Purchase purchaseSelected) {
		this.purchaseSelected = purchaseSelected;
		creditsList = purchaseManager.getCreditsUsedByPurchase(purchaseSelected.getId());
	}
	public HtmlInputText getsOrderId() {
		return sOrderId;
	}
	public void setsOrderId(HtmlInputText sOrderId) {
		this.sOrderId = sOrderId;
	}
	public boolean isEditMode() {
		return editMode;
	}
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public PurchaseManager getPurchaseManager() {
		return purchaseManager;
	}
	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}
	public List<Coupon> getCouponsList() {
		return couponsList;
	}
	public void setCouponsList(List<Coupon> couponsList) {
		this.couponsList = couponsList;
	}
	public List<Credit> getCreditsList() {
		return creditsList;
	}
	public void setCreditsList(List<Credit> creditsList) {
		this.creditsList = creditsList;
	}

	public static Log getLogger() {
		return logger;
	}

	public String getsToMinDate() {
		if(sFrom != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sToMinDate = dateFormat.format(sFrom);
		}
		return sToMinDate;
	}
	
	public void onFromFieldClean(){
		sFrom = null;
		sTo = null;
		sToMinDate=null;
	}

	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}

	public String getsFromMaxDate() {
		if(sTo != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sFromMaxDate = dateFormat.format(sTo);
		}
		return sFromMaxDate;
	}

	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}

	public String getCouponToCheck() {
		return couponToCheck;
	}

	public void setCouponToCheck(String couponToCheck) {
		this.couponToCheck = couponToCheck;
	}

	public SelectOneMenu getsPayType() {
		return sPayType;
	}

	public void setsPayType(SelectOneMenu sPayType) {
		this.sPayType = sPayType;
	}

	public List<SelectItem> getTypeItems() {
		return typeItems;
	}

	public void setTypeItems(List<SelectItem> typeItems) {
		this.typeItems = typeItems;
	}

	public boolean issNoFake() {
		return sNoFake;
	}

	public void setsNoFake(boolean sNoFake) {
		this.sNoFake = sNoFake;
	}

	public SelectOneMenu getsConfirmed() {
		return sConfirmed;
	}

	public void setsConfirmed(SelectOneMenu sConfirmed) {
		this.sConfirmed = sConfirmed;
	}
	
	public HtmlInputText getsCustomerEmail() {
		return sCustomerEmail;
	}

	public void setsCustomerEmail(HtmlInputText sCustomerEmail) {
		this.sCustomerEmail = sCustomerEmail;
	}

	public HtmlInputTextarea getsPaymentNote() {
		return sPaymentNote;
	}

	public void setsPaymentNote(HtmlInputTextarea sPaymentNote) {
		this.sPaymentNote = sPaymentNote;
	}
	
	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
}
