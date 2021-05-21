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
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchOrderBean;
import al.ozone.bl.manager.CreditManager;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.OrdersManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.manager.ReportManager;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Orders;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentBank;
import al.ozone.bl.model.PaymentCash;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailEngine;

@ManagedBean(name="orderController")
@ViewScoped
public class OrderController implements Serializable{

	private static final long serialVersionUID = 1440196996715587481L;
	protected static final transient Log logger = LogFactory.getLog(OrderController.class);
	
	private HtmlInputText sOrderId;
	private HtmlInputText sDealId;
	private HtmlInputText sDealChoiceTitle;
	private HtmlInputText sCustomer;
	private SelectOneMenu sType;
	private List<SelectItem> typeItems;
	
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;
    private HtmlSelectOneMenu sWithPurchase;
    private HtmlSelectOneMenu sContacted;
    private HtmlSelectOneMenu sCanceled;
    
	private HtmlInputText nSellerFullName;
	private HtmlInputText nBuyerFullName;
	private HtmlInputText nBuyerTel;
	private HtmlInputTextarea nNote;
	
	private HtmlSelectOneMenu nBkName;
	private HtmlInputText nBkPayerFullName;
	private HtmlInputText nBkRefNr;
	private Date nBkTransferDate;
	private HtmlInputTextarea nBkNote;
	
	private HtmlInputTextarea nOrderNote;
	private HtmlSelectBooleanCheckbox fCanceled;
    
	//Session data
	private List<Orders> ordersList;
	private Orders orderSelected;
	private boolean editMode;
	
	@ManagedProperty(value="#{ordersManager}") 
	private OrdersManager ordersManager;
	@ManagedProperty(value="#{reportManager}") 
	private ReportManager reportManager;
	@ManagedProperty(value="#{purchaseManager}") 
	private PurchaseManager purchaseManager;
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	@ManagedProperty(value="#{emailEngine}") 
	private EmailEngine emailEngine;
	@ManagedProperty(value="#{customerManager}") 
	private CustomerManager customerManager;
	@ManagedProperty(value="#{creditManager}")
	private CreditManager creditManager;

	@PostConstruct
	public void init(){

		typeItems = new ArrayList<SelectItem>();
		typeItems.add(new SelectItem(Orders.TYPE_COURIER, Orders.TYPE_COURIER));
		typeItems.add(new SelectItem(Orders.TYPE_OFFICE, Orders.TYPE_OFFICE));
		typeItems.add(new SelectItem(Orders.TYPE_PAYPAL, Orders.TYPE_PAYPAL));		
		typeItems.add(new SelectItem(Orders.TYPE_EASYPAY, Orders.TYPE_EASYPAY));
		typeItems.add(new SelectItem(Orders.TYPE_BANK, Orders.TYPE_BANK));
		
		searchOrder(); 
	}

	public void searchOrder() {
		SearchOrderBean sb = new SearchOrderBean();
		sb.setOrderId(JSFUtils.getIntegerFromUIInput(sOrderId));
		sb.setDealId(JSFUtils.getIntegerFromUIInput(sDealId));
		sb.setDealChoiceTitle(JSFUtils.getStringFromUIInput(sDealChoiceTitle));		
		sb.setCustomer(JSFUtils.getStringFromUIInput(sCustomer));
		sb.setType(JSFUtils.getStringFromUIInput(sType));
		sb.setWithPurchase(JSFUtils.getBooleanFromUIInput(sWithPurchase));
		sb.setContacted(JSFUtils.getBooleanFromUIInput(sContacted));
		sb.setCanceled(JSFUtils.getBooleanFromUIInput(sCanceled));
		sb.setFrom(sFrom);
		sb.setTo(sTo);
		
		//logger.debug("Searching with searchBean:"+sb);
		ordersList = ordersManager.search(sb);
		//logger.debug("Got from DB "+ordersList.size()+" orders");
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sOrderId.setValue(null);
		sDealId.setValue(null);
		sDealChoiceTitle.setValue(null);
		sCustomer.setValue(null);
		sType.setValue(null);
		sFrom = null;
	    sTo = null;
	    sToMinDate = null;
	    sFromMaxDate = null;
	    sWithPurchase.setValue("");
	    sContacted.setValue("");
	    sCanceled.setValue("");
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanNewPurchaseCashForm(){
		nSellerFullName.setValue(null);
		nBuyerFullName.setValue(null);
		nBuyerTel.setValue(null);
		nNote.setValue(null);
	}
	
	public void cleanNewPurchaseBankForm(){
		nBkName.setValue(null);
		nBkPayerFullName.setValue(null);
		nBkRefNr.setValue(null);
		nBkTransferDate=null;
		nBkNote.setValue(null);
	}

	public void addPurchaseCash(){		
		PaymentCash payment = new PaymentCash();
		payment.setAmount(orderSelected.getMoneySpent());
		payment.setSellerFullName(JSFUtils.getStringFromUIInput(nSellerFullName));
		payment.setBuyerFullName(JSFUtils.getStringFromUIInput(nBuyerFullName));
		payment.setBuyerTel(JSFUtils.getStringFromUIInput(nBuyerTel));
		payment.setNote(JSFUtils.getStringFromUIInput(nNote));
		doPurchase(payment);
		searchOrder();
		cleanNewPurchaseCashForm();
	}
	
	public void addPurchaseBank(){
		logger.debug("Going to insert new purchase from order:"+orderSelected);
		PaymentBank payment = new PaymentBank();
		payment.setAmount(orderSelected.getMoneySpent());
		payment.setBankName(JSFUtils.getStringFromUIInput(nBkName));
		payment.setFullPayerName(JSFUtils.getStringFromUIInput(nBkPayerFullName));
		payment.setRefNr(JSFUtils.getStringFromUIInput(nBkRefNr));
		payment.setTransferDate(nBkTransferDate);		
		payment.setNote(JSFUtils.getStringFromUIInput(nBkNote));
		
		doPurchase(payment);			
		searchOrder();
		cleanNewPurchaseBankForm();
	}

	private void doPurchase(Payment payment) {
		Purchase pur = new Purchase();
		Customer c = orderSelected.getCustomer();
		List<Credit> creditsList = creditManager.getByCustomer(c.getId());
		c.setCredits(creditsList);
		pur.setCustomer(c);
		pur.setPurchDate(new Date());
		pur.setPayment(payment);
		pur.setQuantity(orderSelected.getQuantity());
		pur.setMoneySpent(orderSelected.getMoneySpent());
		pur.setCreditSpent(orderSelected.getCreditUsed());
		pur.setAmount(orderSelected.getSinglePrice());
		pur.setTotAmount(orderSelected.getQuantity() * orderSelected.getSinglePrice());
		pur.setConfirmed(true);
		pur.setOrderId(orderSelected.getId());
		
		// consider to get full dc // is ok dealId and choiceNr
		// System.out.println(orderSelected.getDealChoice());
		DealChoice dc = dealManager.getDealChoice(orderSelected.getDealChoice().getDealId(), orderSelected.getDealChoice().getChoiceNr());
		pur.setDealChoice(dc);
		
		int minPurchases = dc.getMinCustomers();
		int maxPurchases = dc.getMaxCustomers();
		int totPurchases = dc.getTotPurchases();
		
		if (totPurchases + orderSelected.getQuantity() > maxPurchases){
			int quantityPermitted = maxPurchases - totPurchases; 
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ","The maximum number of purchases for this deal has been exceeded! You can perform at most other "+ quantityPermitted+" coupons.");
			logger.error("Maximum number of purchases for this deal has been exceeded! You can perform at most other "+ quantityPermitted+" coupons.");
		}else{				
			try {
				boolean useCredits = orderSelected.getCreditUsed() != 0;
				purchaseManager.insert(pur, useCredits);	
				logger.info("Purchase with ID "+pur.getId()+" added for customer "+ orderSelected.getCustomer().getEmail()+" ("+orderSelected.getCustomer().getId()+")");
				JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success - ", "New purchase added successfully in database.");
				
				Deal deal = dealManager.get(dc.getDealId());
				//dc.setDeal(deal);
				
				//in case we want to produce the coupons immediately after the purchase
				if(deal.isCouponImmediately()) {
					// DealConfirmed.vm email will be send to the customer 
					Email e = dealManager.prepareCouponsForPurchase(dc, pur);
					emailEngine.addEmail(e); 
				} else {
					Email email = new Email("PurchaseConfirmation");
					email.setSubject("OZone - Konferme Pagese");
	                email.addTo(orderSelected.getCustomer().getEmail());
	                email.addParameter("dc", dc);
	                email.addParameter("moneySpent", pur.getMoneySpent());
	                emailEngine.addEmail(email);
				}
				
				//check if minimum number has been reached to set the deal as confirmed
				if(totPurchases + orderSelected.getQuantity() >= minPurchases){
					deal.setConfirmed(true); 
					try {
						//logger.info("Maximum purchases reached. Set deal as Confirmed. "+deal.getId());
						dealManager.setDealAsConfirmed(deal.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				logger.error("Can not add Purchase for customer "+orderSelected.getCustomer().getEmail(), e);
				JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", ZUtils.getMessageFromException(e, 50));
			}
		}
	}
	
	public void showPdf(){		
//        String invoiceJasperFile = "invoiceReport.jrxml";
//        HashMap<String, Object> orderParameters = new HashMap<String, Object>();
//		byte[] pdfData = reportManager.createReportPdf(invoiceJasperFile, orderParameters);
//		
//		System.out.println("pdfData:"+pdfData);
//        
//		//String dirName = ROOT + SEPARATORE + atf.getStudy().getStudioNumber()+"-"+atf.getStudy().getStudioSuffix();
//		String dirName = pdfDirectory+SEPARATORE + "deal_"+orderSelected.getDealChoice().getDealId();
//	
//        int createResult = createDirectoryForDeal(dirName);
//        
//        if (createResult == DIR_NOT_CREATED) {
//            RuntimeException re = new RuntimeException("The directory " + dirName + " cannot be created");
//            logger.error("The directory " + dirName + " has not been created", re);
//        } else {
//            String fileName = dirName + SEPARATORE + orderSelected.getDealChoice().getId() + ".pdf";
//            createPdf(fileName, pdfData, orderSelected.getDealChoice().getId());
//            
//            //show the file created in url
//        }
		
//		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//	    String uuid = UUID.randomUUID().toString();
//	    ec.getSessionMap().put(uuid, orderSelected.getId());
//	    try {
//			ec.redirect("/printInvoice?orderId=" + uuid);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		JSFUtils.redirectToPage("/printInvoice?orderId="+orderSelected.getId());
	}
	
	
	public void addComment(){
		String oldNote = orderSelected.getNote();
		if(oldNote == null){
			oldNote = "";
		}
		
		String comment = JSFUtils.getStringFromUIInput(nOrderNote);
		try {
			String loggedUser = ((UserPreferences) JSFUtils.getObjectFromSession("userPreferences")).getLoggedUser();
			String head = "<b>" + ZUtils.getDateAsString(new Date())+"</b> " 
						+ loggedUser 
						+ "<br/>";
			comment = oldNote + head + comment + "<br/>";
						
			boolean isCanceled = Boolean.parseBoolean( fCanceled.getValue().toString());
			
			ordersManager.addNote(orderSelected.getId(), comment, isCanceled);
			JSFUtils.addInfoMessage("Komenti u shtua me sukses per porosine "+orderSelected.getId()+" perdoruesin "+orderSelected.getCustomer().getEmail());
		} catch (Exception e) {			
			logger.error("Can not add note for order:"+orderSelected.getId(), e);
			JSFUtils.addErrorMessage(ZUtils.getShortString(e.getCause().toString(), 50));
		}
		
		nOrderNote.setValue(null);
		//orderSelected.setNote(comment);
		searchOrder();
	}

	public List<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public OrdersManager getOrdersManager() {
		return ordersManager;
	}

	public void setOrdersManager(OrdersManager ordersManager) {
		this.ordersManager = ordersManager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public HtmlInputText getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(HtmlInputText sOrderId) {
		this.sOrderId = sOrderId;
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

	public HtmlInputText getsCustomer() {
		return sCustomer;
	}

	public void setsCustomer(HtmlInputText sCustomer) {
		this.sCustomer = sCustomer;
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
	
	public String getsToMinDate() {
		if(sFrom != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sToMinDate = dateFormat.format(sFrom);
		}
		return sToMinDate;
	}

	public String getsFromMaxDate() {
		if(sTo != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sFromMaxDate = dateFormat.format(sTo);
		}
		return sFromMaxDate;
	}
	
	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}
	
	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}

	public SelectOneMenu getsType() {
		return sType;
	}

	public void setsType(SelectOneMenu sType) {
		this.sType = sType;
	}

	public List<SelectItem> getTypeItems() {
		return typeItems;
	}

	public void setTypeItems(List<SelectItem> typeItems) {
		this.typeItems = typeItems;
	}
	public Orders getOrderSelected() {
		return orderSelected;
	}
	public void setOrderSelected(Orders orderSelected) {
		this.orderSelected = orderSelected;
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
	public boolean isEditMode() {
		return editMode;
	}
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}

	public void setEmailEngine(EmailEngine emailEngine) {
		this.emailEngine = emailEngine;
	}

	public HtmlSelectOneMenu getsWithPurchase() {
		return sWithPurchase;
	}

	public void setsWithPurchase(HtmlSelectOneMenu sWithPurchase) {
		this.sWithPurchase = sWithPurchase;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setCreditManager(CreditManager creditManager) {
		this.creditManager = creditManager;
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

	public HtmlInputTextarea getnOrderNote() {
		return nOrderNote;
	}

	public void setnOrderNote(HtmlInputTextarea nOrderNote) {
		this.nOrderNote = nOrderNote;
	}

	public HtmlSelectOneMenu getsContacted() {
		return sContacted;
	}

	public void setsContacted(HtmlSelectOneMenu sContacted) {
		this.sContacted = sContacted;
	}

	public HtmlSelectBooleanCheckbox getfCanceled() {
		return fCanceled;
	}

	public void setfCanceled(HtmlSelectBooleanCheckbox fCanceled) {
		this.fCanceled = fCanceled;
	}

	public HtmlSelectOneMenu getsCanceled() {
		return sCanceled;
	}

	public void setsCanceled(HtmlSelectOneMenu sCanceled) {
		this.sCanceled = sCanceled;
	}

}
