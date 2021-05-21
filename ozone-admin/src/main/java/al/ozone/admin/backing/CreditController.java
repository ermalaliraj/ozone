package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchCreditBean;
import al.ozone.bl.manager.CreditManager;
import al.ozone.bl.model.Credit;

/**
 *
 * View managed backing bean of "creditsList.jsp". It provides a list view of the credits
 * present in the db and a search (filter) functionality by customer mail, credit type, and 
 * credit used status.
 * 
 * @author Florjan Gogaj
 */
@ManagedBean(name="creditController")
@ViewScoped
public class CreditController implements Serializable{
	
	private static final long serialVersionUID = 7457687888557341895L;
	protected static final transient Log logger = LogFactory.getLog(CreditController.class);
	
	//Binding Variables - Search form 
	private HtmlInputText sCustomerEmail;
	private HtmlSelectOneMenu sUsed;
	private HtmlSelectOneMenu sType;

	//View necessary variables
	private List<Credit> creditsList;
	private List <String> creditTypes; 
 
	//Injected properties
	@ManagedProperty(value="#{creditManager}") 
	private CreditManager creditManager;
	
	/**
	 * Initializes the page. It calls first the search() method.
	 */
	@PostConstruct
	public void init(){
		search();
		creditTypes=getCreditTypeList();
	}

	/**
	 * Returns the credit type labels corresponding to the db's single char of the credit types.
	 * @return List<String>
	 */
	private List<String> getCreditTypeList() {
		List<String> list = new ArrayList<String>();
		list.add(JSFUtils.getCreditType_Label((Credit.TYPE_BENEFIT)));
		list.add(JSFUtils.getCreditType_Label((Credit.TYPE_REIMBORSEMENT)));
		return list;
	}

	/**
	 * This method performs a search in the database filtering by the field values inserted
	 * in the search form. A dedicated searching bean is used. 
	 */
	public void search(){
		SearchCreditBean sb = new SearchCreditBean(); 
		sb.setCustomerEmail(JSFUtils.getStringFromUIInput(sCustomerEmail));
		sb.setUsed(JSFUtils.getBooleanFromUIInput((sUsed)));
		sb.setType(JSFUtils.getCreditType_Value(JSFUtils.getStringFromUIInput(sType)));
		List<Credit> list = creditManager.search(sb);
		setCreditsList(list);
	}

	public void getAllCredits(){
		List<Credit> list = creditManager.getAll();
		setCreditsList(list);
	}

	/**
	 * Resets the search form cleaning all fields values. 
	 */
	public void cleanSearchForm(){
		sCustomerEmail.setValue(null);
		sUsed.setValue(null);
		sType.setValue(null);
	}

	public CreditManager getCreditManager() {
		return creditManager;
	}

	public void setCreditManager(CreditManager creditManager) {
		this.creditManager = creditManager;
	}

	public List<Credit> getCreditsList() {
		return creditsList;
	}

	public void setCreditsList(List<Credit> creditsList) {
		this.creditsList = creditsList;
	}
	
	public List<String> getCreditTypes() {
		return creditTypes;
	}

	public void setCreditTypes(List<String> creditTypes) {
		this.creditTypes = creditTypes;
	}

	public HtmlInputText getsCustomerEmail() {
		return sCustomerEmail;
	}

	public void setsCustomerEmail(HtmlInputText sCustomerEmail) {
		this.sCustomerEmail = sCustomerEmail;
	}

	public HtmlSelectOneMenu getsUsed() {
		return sUsed;
	}

	public void setsUsed(HtmlSelectOneMenu sUsed) {
		this.sUsed = sUsed;
	}

	public HtmlSelectOneMenu getsType() {
		return sType;
	}

	public void setsType(HtmlSelectOneMenu sType) {
		this.sType = sType;
	}
}
