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
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchCustomerLog;
import al.ozone.bl.manager.CustomerLogManager;
import al.ozone.bl.model.CustomerLog;

/**
 */

@ManagedBean(name="customerLogController")
@ViewScoped
public class CustomerLogController implements Serializable{
	
	private static final long serialVersionUID = -517465668126635840L;
	protected static final transient Log logger = LogFactory.getLog(CustomerLogController.class);
	
	private HtmlInputText sEmail;
	private HtmlSelectOneMenu sOperationType;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;
    private boolean sNoCustomer;
    
    private List<SelectItem> allOpTypeItems;
    private List<CustomerLog> customerLogsList;
	
	@ManagedProperty(value="#{customerLogManager}") 
	private CustomerLogManager customerLogManager;
	
	/**
	 * Initializes the page. It calls first the search() method.
	 */
	@PostConstruct
	public void init(){			
		allOpTypeItems = new ArrayList<SelectItem>();
		List<String> allTypes = CustomerLog.getAllOperationTypes();
		for (String op : allTypes) {
			allOpTypeItems.add(new SelectItem(op, op));
		}
		search();					
	}
	
	/**
	 * Resets the search form cleaning all fields values. 
	 */
	public void cleanSearchForm(){
		sEmail.setValue(null);
		sOperationType.setValue("");
		sFrom = null;
		sTo = null;
		sToMinDate=null;
		sFromMaxDate=null;
		sNoCustomer=false;
	}
	
	/**
	 * This method performs a search in the database filtering by the field values inserted
	 * in the search form.
	 */
	public void search(){		
		String em = JSFUtils.getStringFromUIInput(sEmail);
		String op = JSFUtils.getStringFromUIInput(sOperationType);
		
		SearchCustomerLog sb = new SearchCustomerLog();
		sb.setNoCustomer(Boolean.valueOf(sNoCustomer));
		sb.setCusEmail(em);
		sb.setOpType(op); 
		sb.setFrom(sFrom);
		sb.setTo(sTo);
		customerLogsList = customerLogManager.search(sb);
	}

	public HtmlInputText getsEmail() {
		return sEmail;
	}

	public void setsEmail(HtmlInputText sEmail) {
		this.sEmail = sEmail;
	}

	public HtmlSelectOneMenu getsOperationType() {
		return sOperationType;
	}

	public void setsOperationType(HtmlSelectOneMenu sOperationType) {
		this.sOperationType = sOperationType;
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
	public List<CustomerLog> getCustomerLogsList() {
		return customerLogsList;
	}
	public void setCustomerLogsList(List<CustomerLog> customerLogsList) {
		this.customerLogsList = customerLogsList;
	}
	public CustomerLogManager getCustomerLogManager() {
		return customerLogManager;
	}
	public void setCustomerLogManager(CustomerLogManager customerLogManager) {
		this.customerLogManager = customerLogManager;
	}
	public List<SelectItem> getAllOpTypeItems() {
		return allOpTypeItems;
	}
	public void setAllOpTypeItems(List<SelectItem> allOpTypeItems) {
		this.allOpTypeItems = allOpTypeItems;
	}
	public boolean issNoCustomer() {
		return sNoCustomer;
	}
	public void setsNoCustomer(boolean sNoCustomer) {
		this.sNoCustomer = sNoCustomer;
	}

}
