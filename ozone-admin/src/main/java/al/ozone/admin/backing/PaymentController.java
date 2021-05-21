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
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchPaymentBean;
import al.ozone.bl.manager.PaymentManager;
import al.ozone.bl.model.Payment;

/**
 * @author Ermal Aliraj
 * 
 * Session Managed Bean.
 */
@ManagedBean(name="paymentController")
@ViewScoped
public class PaymentController implements Serializable{
	
	private static final long serialVersionUID = 5866673902858855703L;
	protected static final transient Log logger = LogFactory.getLog(PaymentController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sPaymentId;
	private HtmlInputText sPurchaseId;
	private SelectOneMenu sPayType;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;
    private List<SelectItem> typeItems;
    
    //data table
	private List<Payment> paymentsList;
	private Payment paymentSelected;
	
	// Injected properties
	@ManagedProperty(value="#{paymentManager}") 
	private PaymentManager paymentManager;	
	
	//Page initialization
	@PostConstruct
	public void init(){		
		typeItems = new ArrayList<SelectItem>();
		typeItems.add(new SelectItem(Payment.TYPE_CASH, "Kesh"));
		typeItems.add(new SelectItem(Payment.TYPE_PAYPAL, "PayPal"));
		typeItems.add(new SelectItem(Payment.TYPE_EASY_PAY, "EasyPay"));
		typeItems.add(new SelectItem(Payment.TYPE_BANK, "Bank"));
		
		searchPayment();
	}
	
	public void searchPayment(){		
		SearchPaymentBean sb = new SearchPaymentBean();
		sb.setPaymentId(JSFUtils.getIntegerFromUIInput(sPaymentId));
		sb.setPurchaseId(JSFUtils.getIntegerFromUIInput(sPurchaseId));
		sb.setInsertedFrom(sFrom);
		sb.setInsertedTo(sTo);
		sb.setPaymentType(JSFUtils.getStringFromUIInput(sPayType));
		
		//logger.debug("Searching with searchBean:"+sb);
		List<Payment> list = paymentManager.search(sb);
		//ZUtils.printListOnLogger(list, logger, "debug");
		setPaymentsList(list);
		//logger.debug("Got from DB "+list.size()+" payments");
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
	    sPaymentId.setValue(null);
	    sPurchaseId.setValue(null);
		sFrom = null;
	    sTo = null;
	    sToMinDate=null;
	    sPayType.setValue(null);
		//refresh the session bean partnerBacking(pop-up of Partner choose)
//		PartnerBacking partnerBck = JSFUtils.findBean("partnerBacking");
//		partnerBck.setPartnerSelected(ZUtils.getNewPartner());
	}
	
	public HtmlInputText getsPaymentId() {
		return sPaymentId;
	}
	public void setsPaymentId(HtmlInputText sPaymentId) {
		this.sPaymentId = sPaymentId;
	}
	public HtmlInputText getsPurchaseId() {
		return sPurchaseId;
	}
	public void setsPurchaseId(HtmlInputText sPurchaseId) {
		this.sPurchaseId = sPurchaseId;
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
	public List<Payment> getPaymentsList() {
		return paymentsList;
	}
	public void setPaymentsList(List<Payment> paymentsList) {
		this.paymentsList = paymentsList;
	}
	public Payment getPaymentSelected() {
		return paymentSelected;
	}
	public void setPaymentSelected(Payment paymentSelected) {
		this.paymentSelected = paymentSelected;
	}
	public PaymentManager getPaymentManager() {
		return paymentManager;
	}
	public void setPaymentManager(PaymentManager paymentManager) {
		this.paymentManager = paymentManager;
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
	
	public void setPaymentOfPurchase(Integer purchaseId){
		this.paymentSelected = paymentManager.getPaymentForPurchase(purchaseId);
	}
	
}
