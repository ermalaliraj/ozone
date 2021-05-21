package al.ozone.admin.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.TabChangeEvent;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.manager.CreditManager;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.CustomerInvited;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentBank;
import al.ozone.bl.model.PaymentCash;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailEngine;

/**
 * @author Ermal Aliraj
 * 
 */
@SessionScoped
@ManagedBean(name="customerDetailController")
public class CustomerDetailController implements Serializable {

	private static final long serialVersionUID = 6182699385001184856L;
	protected static final transient Log logger = LogFactory.getLog(CustomerDetailController.class);

	private Customer customerSelected;
	
	//TAB1 Customer's detail
	private HtmlInputText fName;
	private HtmlInputText fSurname;
	private HtmlInputText fEmail;
	private HtmlInputText fPassword;
	private HtmlInputText fPhone;
	private HtmlSelectBooleanCheckbox fActive;
	private HtmlSelectOneMenu fSex;
	private HtmlInputTextarea fAddress;
	private HtmlSelectOneMenu fCity;
	private boolean editMode;
	
	//TAB2 Customer's Purchases
	private List<Purchase> purchasesList;
	private String couponToCheck;
	private Purchase purchaseSelected;
	private int purchaseQuantity;
	//new Purchase
	private String cityChoosed;
	private List<DealChoice> activeDealsChoice;
	private DealChoice dealChoiceSelected;
	private String sDealItemId;

	private List<String> quantityList;
	private boolean useCreditChecked;
	private int totalCredits;
	private int remainCredits;
	private HtmlInputText nSellerFullName;
	private HtmlInputText nBuyerFullName;
	private HtmlInputText nBuyerTel;
	private HtmlInputTextarea nNote;
	//bank mode
	private HtmlSelectOneMenu nBkName;
	private HtmlInputText nBkPayerFullName;
	private HtmlInputText nBkRefNr;
	private Date nBkTransferDate;
	private HtmlInputTextarea nBkNote;
	private int selectedPayTypeTab;

	//TAB3 Customer's credit
	private List<Credit> creditsList;
	private Credit creditSelected;
	//New Credit
	private List <String> creditTypes; 
	private Date newCreditAssignedDate;
	private Date newCreditValidDate;
	private HtmlInputText newCreditValue;
	private HtmlSelectOneMenu newCreditType;
	private HtmlInputTextarea newCreditAbout;
	private String nToMinDate;
	private String nToMaxDate;

	//Use credit
	private HtmlInputTextarea creditAboutUse;
	
	//TAB4 Newsletter 
	private List<String> newslettersList;
	private List<City> cities;
	private List<City> allCities;
	
	//TAB5 Invitations 
	private List<CustomerInvited> invitationsList;
	
	@ManagedProperty(value="#{applicationConfig}") 
	private ApplicationConfig applicationConfig;
	@ManagedProperty(value="#{customerManager}") 
	private CustomerManager customerManager;
	@ManagedProperty(value="#{creditManager}") 
	private CreditManager creditManager;
	@ManagedProperty(value="#{purchaseManager}") 
	private PurchaseManager purchaseManager;
	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;	
	@ManagedProperty(value="#{inviteManager}") 
	private InviteManager inviteManager;
	@ManagedProperty(value="#{emailEngine}") 
	private EmailEngine emailEngine;
	
	@PostConstruct
	public void init(){
		creditTypes = getCreditTypeList();
		
		cities = cityManager.getAllActives();
		allCities = cityManager.getAll();
		cityChoosed = "TR";//TODO add to system variables

		dealChoiceSelected = new DealChoice();
		activeDealsChoice = dealManager.getActiveDealsChoice();
		quantityList = new ArrayList<String>();
		for (int i = 1; i < 31; i++) {
			quantityList.add(i+"");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		newCreditAssignedDate=new Date();
		nToMinDate=dateFormat.format(newCreditAssignedDate);
		
		newCreditValidDate=ZUtils.addMonthsToDate(newCreditAssignedDate, applicationConfig.getCreditDuration());
		//System.out.println("*********************************"+applicationConfig.getCreditDuration());
	}
		
	protected String getManagedObjectName(){
		String className=this.getClass().getSimpleName();
		int length=className.length();
		return className.substring(0,length-10);
	}
	
	public void updateCustomer(){    
		Customer c = new Customer();
		c.setId(customerSelected.getId());
		c.setName(JSFUtils.getStringFromUIInput(fName));
		c.setSurname(JSFUtils.getStringFromUIInput(fSurname));
		c.setBirthdate(customerSelected.getBirthdate());
		c.setEmail(JSFUtils.getStringFromUIInput(fEmail));
		c.setPassword(JSFUtils.getStringFromUIInput(fPassword));
		c.setPhone(JSFUtils.getStringFromUIInput(fPhone));
		c.setActive(JSFUtils.getBooleanFromUIInput(fActive));
		c.setSex(JSFUtils.getStringFromUIInput(fSex));	
		c.setAddress(JSFUtils.getStringFromUIInput(fAddress));	
		c.setCityId(JSFUtils.getStringFromUIInput(fCity));
		
		try {
			customerManager.update(c);
			logger.info(getManagedObjectName()+" with ID "+c.getId()+" updated");
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", getManagedObjectName()+" updated successfully.");  
		} catch (Exception e) {
			logger.error(getManagedObjectName()+" with ID "+c.getId()+" cannot be updated", e);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getShortString(e.getCause().toString(), 50));
		}
    }

	public void addPurchaseCash(){
		PaymentCash payment = new PaymentCash();
		payment.setAmount(purchaseSelected.getMoneySpent());
		payment.setSellerFullName(JSFUtils.getStringFromUIInput(nSellerFullName));
		payment.setBuyerFullName(JSFUtils.getStringFromUIInput(nBuyerFullName));
		payment.setBuyerTel(JSFUtils.getStringFromUIInput(nBuyerTel));
		payment.setNote(JSFUtils.getStringFromUIInput(nNote));		
		
		doPurchase(payment);
		cleanNewPurchaseForm();
	}
	
	public void addPurchaseBank(){		
		PaymentBank payment = new PaymentBank();
		payment.setAmount(purchaseSelected.getMoneySpent());
		payment.setBankName(JSFUtils.getStringFromUIInput(nBkName));
		payment.setRefNr(JSFUtils.getStringFromUIInput(nBkRefNr));
		payment.setFullPayerName(JSFUtils.getStringFromUIInput(nBkPayerFullName));
		payment.setTransferDate(nBkTransferDate);
		payment.setNote(JSFUtils.getStringFromUIInput(nBkNote));
		
		doPurchase(payment);
		cleanNewPurchaseForm();
	}

	private void doPurchase(Payment payment) {
//		purchaseSelected.setQuantity(1);
//		purchaseSelected.setAmount(dealSelected.getPrice());
//		purchaseSelected.setTotAmount(dealSelected.getPrice());
//		purchaseSelected.setMoneySpent(dealSelected.getPrice());
//		purchaseSelected.setDealChoices();
		customerSelected.setCredits(creditsList);//refresh customer's credit List
		purchaseSelected.setCustomer(customerSelected);
		purchaseSelected.setPurchDate(new Date());
		purchaseSelected.setPayment(payment);
		purchaseSelected.setQuantity(purchaseQuantity);
		purchaseSelected.setConfirmed(true);
		
		//TODO .update() must be ACID inside purchaseManager.insert();	
		DealChoice dc = purchaseSelected.getDealChoice();
		int minPurchases = dc.getMinCustomers();
		int maxPurchases = dc.getMaxCustomers();
		//System.out.println("*************minPurchases = " + minPurchases);
		int totPurchases = dc.getTotPurchases();
		//System.out.println("*************totPurchases = " + totPurchases);
		//System.out.println("*************confirmed = " + deal.isConfirmed());
		
		if (totPurchases + purchaseQuantity > maxPurchases){
			int quantityPermitted = maxPurchases - totPurchases; 
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ","The maximum number of purchases for this deal has been exceeded! You can perform at most other "+ quantityPermitted+" coupons.");
		}else{	
			//logger.info("Inserting purchase in DB. " + purchaseSelected);
			try {
				purchaseManager.insert(purchaseSelected, useCreditChecked);						
				logger.info("Purchase with ID "+purchaseSelected.getId()+" added for customer "+ customerSelected.getEmail());
				JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", "New purchase added successfully in database.");
				
				Deal deal = dealManager.get(dc.getDealId());
				dc.setDeal(deal);
				
				//in case we want to produce the coupons immediately after the purchase
				if(deal.isCouponImmediately()) {
					// DealConfirmed.vm email will be send to the customer 
					Email e = dealManager.prepareCouponsForPurchase(dc, purchaseSelected);
					emailEngine.addEmail(e);
				} else {
					Email email = new Email("PurchaseConfirmation");
					email.setSubject("OZone - Konferme Pagese");
	                email.addTo(customerSelected.getEmail());
	                email.addParameter("dc", purchaseSelected.getDealChoice());//ermal was p
	                email.addParameter("moneySpent", purchaseSelected.getMoneySpent());
	                emailEngine.addEmail(email);
				}
				
				//check if minimum number has been reached to set the deal as confirmed
				//TODO do the update every time after minPurchases
				if(totPurchases + purchaseQuantity >= minPurchases){
					deal.setConfirmed(true); 
					try {
						//logger.info("Maximum purchases reached.Set deal as Confirmed. "+deal.getId());
						dealManager.setDealAsConfirmed(deal.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				refreshListsFromDB();
			} catch (Exception e) {
				logger.error("Can not add Purchase for customer "+customerSelected.getEmail(), e);
				JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getMessageFromException(e, 50));
			}
		}
	}

	private void refreshListsFromDB() {
		purchasesList = purchaseManager.getByCustomer(customerSelected.getId());
		creditsList = creditManager.getByCustomer(customerSelected.getId());
		remainCredits = ZUtils.calculateRemainingCredits(creditsList);
		invitationsList = inviteManager.getInvitationsForCustomer(customerSelected.getId());
		totalCredits = 0;
	}
	
	private void cleanNewPurchaseForm() {
		purchaseSelected = new Purchase();
		dealChoiceSelected = new DealChoice();
		useCreditChecked = false;
		nSellerFullName.setValue(null);
		nBuyerFullName.setValue(null);
		nBuyerTel.setValue(null);
		nNote.setValue(null);
		sDealItemId = "0";
		nBkName.setValue(null);
		nBkPayerFullName.setValue(null);
		nBkRefNr.setValue(null);
		nBkTransferDate=null;
		nBkNote.setValue(null);
		selectedPayTypeTab = 0;
	}

	public void addCredit(){    
		Credit c = new Credit();
		c.setAssignedDate(newCreditAssignedDate);
		c.setValidDate(newCreditValidDate);
		c.setValue(JSFUtils.getIntegerFromUIInput(newCreditValue));
		c.setType(JSFUtils.getCreditType_Value(JSFUtils.getStringFromUIInput(newCreditType)));
		c.setCustomer(customerSelected);
		c.setAbout(JSFUtils.getStringFromUIInput(newCreditAbout));

		try {
			creditManager.insert(c);
			logger.info("Credit with ID "+c.getId()+" value "+c.getValue()+"L added for customer "+customerSelected.getEmail()+" ("+customerSelected.getId()+")");
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", "New credit added successfully in database.");
			refreshListsFromDB();
		} catch (Exception e) {
			logger.error("Credit "+c.getId()+" with ID "+c.getId()+" cannot be inserted", e);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getShortString(e.getCause().toString(), 50));
		}
		cleanNewCreditForm();
    }

	private void cleanNewCreditForm() {
		//newCreditAssignedDate=null;
		newCreditValidDate=null;
		newCreditValue.setValue(null);
		newCreditType.setValue(null);
		newCreditAbout.setValue(null);
	}
	
	public void useCredit(){
		Credit c = creditSelected;
		String reason = JSFUtils.getStringFromUIInput(creditAboutUse);

		try {
			//logger.debug("going to update: "+c);
			creditManager.setCreditAsUsed(c.getId(), reason);
			logger.info("Credit nr."+ c.getId()+" with value "+c.getValue()+"L set as used for customer "+customerSelected.getEmail()+" ("+customerSelected.getId()+")");
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", getManagedObjectName()+" updated successfully in database.");
		} catch (Exception e) {
			logger.error("Credit "+c.getId()+" with ID "+c.getId()+" cannot be updated", e);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getShortString(e.getCause().toString(), 50));
		}
		
		cleanSetCreditAsUsedform();
		creditsList = creditManager.getByCustomer(customerSelected.getId());
	}	
	
	public void saveNewsletters(){
		Integer customerId = customerSelected.getId();
		
		try {
			customerManager.deleteNewsletters(customerId);
			customerManager.insertNewsletter(customerId, newslettersList);
			
			logger.info("Newsletters updated for the customer with id "+customerId +".");		
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", "Newsletters updated successfully for the customer.");
		} catch (Exception e) {
			logger.error("Newslettersfor the customer with id "+customerId +" cannot be updated.", e);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getShortString(e.getCause().toString(), 50));
		}		
	}
	
	private void cleanSetCreditAsUsedform(){
		creditAboutUse.setValue(null);
	}
	
	public void onDealChanged(AjaxBehaviorEvent e){
		SelectOneMenu selM = (SelectOneMenu) e.getSource();
		String dealChangeId = JSFUtils.getStringFromUIInput(selM);
		if(dealChangeId.equals("0")){
			dealChoiceSelected = new DealChoice();
			purchaseSelected = new Purchase();
		}else{
			Integer[] twoIds = getDealChoiceIds(dealChangeId);
			
			dealChoiceSelected = dealManager.getDealChoice(twoIds[0], twoIds[1]);
			purchaseSelected = new Purchase();
			purchaseSelected.setDealChoice(dealChoiceSelected);
			purchaseQuantity=1;
			purchaseSelected.setQuantity(purchaseQuantity);
			purchaseSelected.setAmount(dealChoiceSelected.getPrice());
			purchaseSelected.setTotAmount(dealChoiceSelected.getPrice());
			purchaseSelected.setMoneySpent(dealChoiceSelected.getPrice());
			purchaseSelected.setCreditSpent(0);
		}
		
		remainCredits = remainCredits + totalCredits;
		totalCredits = 0;
		useCreditChecked = false;
	}
	
	private Integer[] getDealChoiceIds(String dealChangeId) {
		Integer[] rv = new Integer[2];
		try {
			String[] ids = dealChangeId.split("-");
			rv[0] = Integer.parseInt(ids[0]);
			rv[1] = Integer.parseInt(ids[1]);
			return rv;
		} catch (Exception e) {
			JSFUtils.addErrorMessage("Error spliting Id of DealChoice from drop-down.");
			return null;
		}
	}

	public void onQuantityChanged(AjaxBehaviorEvent e){
		HtmlSelectOneMenu sel = (HtmlSelectOneMenu) e.getSource();
		int qty = JSFUtils.getIntegerFromUIInput(sel);
		purchaseQuantity=qty;
		int toPay = qty * purchaseSelected.getAmount();
		int totalToPay = toPay;
		//System.out.println("[before]remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", toPay:"+toPay);
		if(useCreditChecked){
			totalToPay = calculateTotToPay(totalCredits + remainCredits, toPay);
		}
		//System.out.println("[after]remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", toPay:"+toPay);
		
		purchaseSelected.setQuantity(qty);
		purchaseSelected.setTotAmount(toPay);
		purchaseSelected.setMoneySpent(totalToPay);
		purchaseSelected.setCreditSpent(totalCredits);
	}
	
	/**
	 * Considering availableCredits and the amount to pay, calculates the effective
	 * amount to pay.
	 * @param availableCredit available credit
	 * @param toPay amount to pay
	 * @return |toPay - availableCredit|
	 */
	private int calculateTotToPay(int availableCredits, int toPay){
		//System.out.println("availableCredits:"+availableCredits+", toPay:"+toPay);
		int totalToPay = 0;
		int restCredit = toPay - availableCredits;
		if(restCredit < 0){
			remainCredits = Math.abs(restCredit);
			totalCredits = toPay;
			totalToPay = 0;
			//System.out.println("[true](restCredit<0) remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", restCredit:"+restCredit);
		}
		else{
			remainCredits = 0;
			totalCredits = availableCredits;
			totalToPay = toPay - availableCredits;
			//System.out.println("[true](restCredit>0) remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", restCredit:"+restCredit);
		}
		logger.debug("Considering availableCredits="+availableCredits+", toPay="+toPay+" returns totalToPay="+totalToPay);
		return totalToPay;
	}
	
	public void onCreditCkChanged(AjaxBehaviorEvent e){
		HtmlSelectBooleanCheckbox ck = (HtmlSelectBooleanCheckbox) e.getSource();
		useCreditChecked = JSFUtils.getPrimitiveBoolFromUIInput(ck);
		
		int toPay = purchaseSelected.getMoneySpent();
		int restCredit = toPay - remainCredits;
		int totalToPay = 0;
		System.out.println("[before]remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", toPay:"+toPay);
		if(useCreditChecked){
			if(restCredit < 0){
				remainCredits = Math.abs(restCredit);
				totalCredits = toPay;
				totalToPay = 0;
				System.out.println("[true](restCredit<0) remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay);
			}else{
				totalCredits = remainCredits;
				remainCredits = 0;
				totalToPay = toPay - totalCredits;
				System.out.println("[true](restCredit>0) remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay);
			}
		}
		else{
			remainCredits = remainCredits + totalCredits;
			totalToPay = toPay + totalCredits;
			totalCredits = 0;
			System.out.println("[false](restCredit<0) remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay);
		}
		purchaseSelected.setMoneySpent(totalToPay);
		purchaseSelected.setCreditSpent(totalCredits);
		System.out.println("[after]remainCredits: "+remainCredits+", totalCredits:"+totalCredits+", totalToPay:"+totalToPay+", toPay:"+toPay);
	}
	
	public void onTabChangePayMode(TabChangeEvent event) { 
		String tabName = event.getTab().getId();   
        if(tabName.equals("cashTab")){
        	selectedPayTypeTab = 0;
        }
        if(tabName.equals("bankTab")){
        	selectedPayTypeTab = 1;
        }
    }  
	
	public void onTabChange(TabChangeEvent event) {  
		//TODO Future enhancement - on tab selected refresh the data of only that specific TAB
//		String tabId = event.getTab().getId();
	}
	
	public Customer getCustomerSelected() {
		return customerSelected;
	}
	public void setCustomerSelected(Customer customerSelected) {
		this.customerSelected = customerSelected;
		refreshListsFromDB();
	}
	
	public List<Purchase> getPurchasesList() {
		return purchasesList;
	}
	public void setPurchasesList(List<Purchase> purchasesList) {
		this.purchasesList = purchasesList;
	}
	private List<String> getCreditTypeList(){
		List<String> list = new ArrayList<String>();
		list.add(JSFUtils.getCreditType_Label((Credit.TYPE_BENEFIT)));
		list.add(JSFUtils.getCreditType_Label((Credit.TYPE_REIMBORSEMENT)));
		return list;
	}
	
	public String checkCoupon(){
		//System.out.println("puting in session:"+couponToCheck);
		JSFUtils.putObjectInSession("couponToCheck", couponToCheck);
		return "success";
	}
	
	public List<Credit> getCreditsList() {
		return creditsList;
	}
	public void setCreditsList(List<Credit> creditsList) {
		this.creditsList = creditsList;
	}
	public CustomerManager getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	public CreditManager getCreditManager() {
		return creditManager;
	}
	public void setCreditManager(CreditManager creditManager) {
		this.creditManager = creditManager;
	}
	public PurchaseManager getPurchaseManager() {
		return purchaseManager;
	}
	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}
	public boolean isEditMode() {
		return editMode;
	}
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public HtmlInputText getfName() {
		return fName;
	}
	public void setfName(HtmlInputText fName) {
		this.fName = fName;
	}
	public HtmlInputText getfSurname() {
		return fSurname;
	}
	public void setfSurname(HtmlInputText fSurname) {
		this.fSurname = fSurname;
	}
	public HtmlInputText getfEmail() {
		return fEmail;
	}
	public void setfEmail(HtmlInputText fEmail) {
		this.fEmail = fEmail;
	}
	public HtmlInputText getfPhone() {
		return fPhone;
	}
	public void setfPhone(HtmlInputText fPhone) {
		this.fPhone = fPhone;
	}
	public HtmlSelectBooleanCheckbox getfActive() {
		return fActive;
	}
	public void setfActive(HtmlSelectBooleanCheckbox fActive) {
		this.fActive = fActive;
	}
	public HtmlSelectOneMenu getfSex() {
		return fSex;
	}
	public void setfSex(HtmlSelectOneMenu fSex) {
		this.fSex = fSex;
	}
	public Date getNewCreditAssignedDate() {
		newCreditAssignedDate=new Date();
		return newCreditAssignedDate;
	}
	public void setNewCreditAssignedDate(Date newCreditAssignedDate) {
		this.newCreditAssignedDate = newCreditAssignedDate;
	}
	public Date getNewCreditValidDate() {
		newCreditValidDate=ZUtils.addMonthsToDate(newCreditAssignedDate, applicationConfig.getCreditDuration());
		return newCreditValidDate;
	}
	public void setNewCreditValidDate(Date newCreditValidDate) {
		this.newCreditValidDate = newCreditValidDate;
	}
	public HtmlInputText getNewCreditValue() {
		return newCreditValue;
	}
	public void setNewCreditValue(HtmlInputText newCreditValue) {
		this.newCreditValue = newCreditValue;
	}
	public HtmlSelectOneMenu getNewCreditType() {
		return newCreditType;
	}
	public void setNewCreditType(HtmlSelectOneMenu newCreditType) {
		this.newCreditType = newCreditType;
	}
	public HtmlInputTextarea getNewCreditAbout() {
		return newCreditAbout;
	}
	public void setNewCreditAbout(HtmlInputTextarea newCreditAbout) {
		this.newCreditAbout = newCreditAbout;
	}
	public List<String> getCreditTypes() {
		return creditTypes;
	}
	public void setCreditTypes(List<String> creditTypes) {
		this.creditTypes = creditTypes;
	}
	public HtmlInputTextarea getCreditAboutUse() {
		return creditAboutUse;
	}
	public void setCreditAboutUse(HtmlInputTextarea creditAboutUse) {
		this.creditAboutUse = creditAboutUse;
	}
	public Credit getCreditSelected() {
		return creditSelected;
	}
	public void setCreditSelected(Credit creditSelected) {
		this.creditSelected = creditSelected;
	}
	public List<String> getNewslettersList() {
		List<City> list = customerManager.getCustomerNewsletters(customerSelected.getId());
		newslettersList = new ArrayList<String>();
		for(City c: list){
			newslettersList.add(c.getId());
		}
				
		return newslettersList;
	}
	public CityManager getCityManager() {
		return cityManager;
	}
	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}
	public String getCityChoosed() {
		return cityChoosed;
	}
	public void setCityChoosed(String cityChoosed) {
		this.cityChoosed = cityChoosed;
	}
	public HtmlInputText getnSellerFullName() {
		return nSellerFullName;
	}
	public void setnSellerFullName(HtmlInputText nSellerFullName) {
		this.nSellerFullName = nSellerFullName;
	}
	public HtmlInputText getnBuyerFullName() {
		return nBuyerFullName;
	}
	public void setnBuyerFullName(HtmlInputText nBuyerFullName) {
		this.nBuyerFullName = nBuyerFullName;
	}
	public HtmlInputText getnBuyerTel() {
		return nBuyerTel;
	}
	public void setnBuyerTel(HtmlInputText nBuyerTel) {
		this.nBuyerTel = nBuyerTel;
	}
	public HtmlInputTextarea getnNote() {
		return nNote;
	}
	public void setnNote(HtmlInputTextarea nNote) {
		this.nNote = nNote;
	}
	public Purchase getPurchaseSelected() {
		return purchaseSelected;
	}
	public void setPurchaseSelected(Purchase purchaseSelected) {
		this.purchaseSelected = purchaseSelected;
	}
	public boolean isUseCreditChecked() {
		return useCreditChecked;
	}
	public void setUseCreditChecked(boolean useCreditChecked) {
		this.useCreditChecked = useCreditChecked;
	}
	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}
	public int getTotalCredits() {
		return totalCredits;
	}
	public int getRemainCredits() {
		//remainCredits = ZUtils.calculateRemainingCredits(creditsList);
		return remainCredits;
	}
	public void setRemainCredits(int remainCredits) {
		this.remainCredits = remainCredits;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public List<CustomerInvited> getInvitationsList() {
		return invitationsList;
	}
	public void setInvitationsList(List<CustomerInvited> invitationsList) {
		this.invitationsList = invitationsList;
	}
	public void setNewslettersList(List<String> newslettersList) {
		this.newslettersList = newslettersList;
	}
	public List<String> getQuantityList() {
		return quantityList;
	}
	public void setQuantityList(List<String> quantityList) {
		this.quantityList = quantityList;
	}

	public List<DealChoice> getActiveDealsChoice() {
		return activeDealsChoice;
	}

	public void setActiveDealsChoice(List<DealChoice> activeDealsChoice) {
		this.activeDealsChoice = activeDealsChoice;
	}

	public DealChoice getDealChoiceSelected() {
		return dealChoiceSelected;
	}

	public void setDealChoiceSelected(DealChoice dealChoiceSelected) {
		this.dealChoiceSelected = dealChoiceSelected;
	}

	public DealManager getDealManager() {
		return dealManager;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}

	public String getnToMinDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		nToMinDate=dateFormat.format(newCreditAssignedDate);
		return nToMinDate;
	}

	public void setnToMinDate(String nToMinDate) {
		this.nToMinDate = nToMinDate;
	}

	public String getnToMaxDate() {
		return nToMaxDate;
	}

	public void setnToMaxDate(String nToMaxDate) {
		this.nToMaxDate = nToMaxDate;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	public HtmlInputText getfPassword() {
		return fPassword;
	}

	public void setfPassword(HtmlInputText fPassword) {
		this.fPassword = fPassword;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public List<City> getAllCities() {
		return allCities;
	}

	public void setAllCities(List<City> allCities) {
		this.allCities = allCities;
	}

	public HtmlInputTextarea getfAddress() {
		return fAddress;
	}

	public void setfAddress(HtmlInputTextarea fAddress) {
		this.fAddress = fAddress;
	}

	public HtmlSelectOneMenu getfCity() {
		return fCity;
	}

	public void setfCity(HtmlSelectOneMenu fCity) {
		this.fCity = fCity;
	}

	public InviteManager getInviteManager() {
		return inviteManager;
	}

	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
	}

	public EmailEngine getEmailEngine() {
		return emailEngine;
	}

	public void setEmailEngine(EmailEngine emailEngine) {
		this.emailEngine = emailEngine;
	}

	public String getCouponToCheck() {
		return couponToCheck;
	}

	public void setCouponToCheck(String couponToCheck) {
		this.couponToCheck = couponToCheck;
	}

	public String getsDealItemId() {
		return sDealItemId;
	}

	public void setsDealItemId(String sDealItemId) {
		this.sDealItemId = sDealItemId;
	}

	public HtmlSelectOneMenu getnBkName() {
		return nBkName;
	}

	public void setnBkName(HtmlSelectOneMenu nBkName) {
		this.nBkName = nBkName;
	}

	public HtmlInputText getnBkPayerFullName() {
		return nBkPayerFullName;
	}

	public void setnBkPayerFullName(HtmlInputText nBkPayerFullName) {
		this.nBkPayerFullName = nBkPayerFullName;
	}

	public HtmlInputText getnBkRefNr() {
		return nBkRefNr;
	}

	public void setnBkRefNr(HtmlInputText nBkRefNr) {
		this.nBkRefNr = nBkRefNr;
	}

	public Date getnBkTransferDate() {
		return nBkTransferDate;
	}

	public void setnBkTransferDate(Date nBkTransferDate) {
		this.nBkTransferDate = nBkTransferDate;
	}

	public HtmlInputTextarea getnBkNote() {
		return nBkNote;
	}

	public void setnBkNote(HtmlInputTextarea nBkNote) {
		this.nBkNote = nBkNote;
	}

	public int getSelectedPayTypeTab() {
		return selectedPayTypeTab;
	}

	public void setSelectedPayTypeTab(int selectedPayTypeTab) {
		this.selectedPayTypeTab = selectedPayTypeTab;
	}
	
}
