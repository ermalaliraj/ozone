package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;

import al.ozone.admin.lazymodel.CustomerLazyDataModel;
import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Florjan Gogaj
 * 
 */
@ViewScoped
@ManagedBean(name="customerController")
public class CustomerController implements Serializable{

	private static final long serialVersionUID = 1084273051395619799L;
	protected static final transient Log logger = LogFactory.getLog(CustomerController.class);
	
	private LazyDataModel<Customer> lazyModel;
	private int totalUsers;
	
	private SelectItem[] sexOptions;
	private SelectItem[] activeOptions;
	
	private HtmlInputText newName;
	private HtmlInputText newSurname;
	private Date newBirthdate;
	private HtmlInputText newEmail;
	private HtmlInputText newPassword;
	private HtmlInputText newPhone;
	private HtmlSelectBooleanCheckbox newActive;
	private HtmlSelectOneMenu newSex;
	
	//data needed for the session
	//private List<Customer> customersList;
	private Customer customerSelected;
	private boolean editMode;
	
	@ManagedProperty(value="#{customerManager}") 
	private CustomerManager customerManager;
	
	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	

	@PostConstruct
	public void init(){ 	
		sexOptions = new SelectItem[3];  
		sexOptions[0] = new SelectItem("", "ALL");
		sexOptions[1] = new SelectItem("M", "M");
		sexOptions[2] = new SelectItem("F", "F");
		
		activeOptions = new SelectItem[3];  
		activeOptions[0] = new SelectItem("", "ALL");
		activeOptions[1] = new SelectItem("true", JSFUtils.getMessageFromBundle("yes"));
		activeOptions[2] = new SelectItem("false", JSFUtils.getMessageFromBundle("no"));
		
		lazyModel = new CustomerLazyDataModel(customerManager);
		totalUsers = customerManager.getCountCustomers();
	}
	
	protected String getManagedObjectName(){
		String className=this.getClass().getSimpleName();
		int length=className.length();
		return className.substring(0,length-10);
	}
	
	public void addCustomer(){    
		Customer c = new Customer();
		c.setName(JSFUtils.getStringFromUIInput(newName));
		c.setSurname(JSFUtils.getStringFromUIInput(newSurname));
		c.setBirthdate(newBirthdate);
		c.setEmail(JSFUtils.getStringFromUIInput(newEmail));
		c.setPassword(JSFUtils.getStringFromUIInput(newPassword));
		c.setPhone(JSFUtils.getStringFromUIInput(newPhone));
		c.setActive(JSFUtils.getBooleanFromUIInput(newActive));
		String sex=JSFUtils.getStringFromUIInput(newSex);
		if(!sex.equals("")){
			c.setSex(sex);
		}	

		try {
			customerManager.insert(c);
			List<City> activeCities = cityManager.getAllActives();
			List<String> newsletters = new ArrayList<String>(); 
			int size=activeCities.size();
			for(int i=0; i<size; i++){
				newsletters.add(activeCities.get(i).getId());
			}
			customerManager.insertNewsletter(c.getId(), newsletters);
			
			logger.info(getManagedObjectName()+" "+c.getName()+" with ID "+c.getId()+" inserted from controller.");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, getManagedObjectName()+" Added",  "New "+getManagedObjectName().toLowerCase()+" added successfully in database.");  
			FacesContext.getCurrentInstance().addMessage(null, message);		
		} catch (Exception e) {
			logger.error(getManagedObjectName()+" "+c.getName()+" with ID "+c.getId()+" cannot be inserted from controller.", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot insert the "+getManagedObjectName().toLowerCase(),  ZUtils.getShortString(e.getCause().toString(), 50));  
			FacesContext.getCurrentInstance().addMessage(null, message); 
		}
		
		cleanNewCustomerForm();
    }
	
	public void cleanNewCustomerForm() {
		newName.setValue(null);
		newSurname.setValue(null);
		newBirthdate=null;
		newEmail.setValue(null);
		newPassword.setValue(null);
		newPhone.setValue(null);
		newActive.setValue(null);
		newSex.setValue(null);
	}

	public void deleteCustomer(){
		try {
			customerManager.delete(customerSelected);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, getManagedObjectName()+" Deleted",  getManagedObjectName()+" deleted successfully with no errors.");  
			FacesContext.getCurrentInstance().addMessage(null, message);  
			logger.info(getManagedObjectName()+" "+customerSelected.getId()+" deleted from controller.");
		}catch (Exception e) {
			logger.error(getManagedObjectName()+" "+customerSelected.getId()+" can not be deleted.", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete the "+getManagedObjectName().toLowerCase(), ZUtils.getShortString(e.getCause().toString(), 50));  
			FacesContext.getCurrentInstance().addMessage(null, message);
		} 
	}
	
//	public void getAllCustomers(){
//		List<Customer> list = customerManager.getAll();
//		setCustomersList(list);
//	}
//	
//	public List<Customer> getCustomersList() {
//		return customersList;
//	}
//
//	public void setCustomersList(List<Customer> customersList) {
//		this.customersList = customersList;
//	}
	
	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public HtmlInputText getNewName() {
		return newName;
	}

	public void setNewName(HtmlInputText newName) {
		this.newName = newName;
	}

	public HtmlInputText getNewSurname() {
		return newSurname;
	}

	public void setNewSurname(HtmlInputText newSurname) {
		this.newSurname = newSurname;
	}

	public HtmlInputText getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(HtmlInputText newEmail) {
		this.newEmail = newEmail;
	}

	public HtmlInputText getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(HtmlInputText newPassword) {
		this.newPassword = newPassword;
	}

	public HtmlInputText getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(HtmlInputText newPhone) {
		this.newPhone = newPhone;
	}

	public static Log getLogger() {
		return logger;
	}

	public HtmlSelectBooleanCheckbox getNewActive() {
		return newActive;
	}

	public void setNewActive(HtmlSelectBooleanCheckbox newActive) {
		this.newActive = newActive;
	}

	public Date getNewBirthdate() {
		return newBirthdate;
	}

	public void setNewBirthdate(Date newBirthdate) {
		this.newBirthdate = newBirthdate;
	}
	
	public HtmlSelectOneMenu getNewSex() {
		return newSex;
	}

	public void setNewSex(HtmlSelectOneMenu newSex) {
		this.newSex = newSex;
	}
	
	public Customer getCustomerSelected() {
		return customerSelected;
	}

	public void setCustomerSelected(Customer customerSelected) {
		this.customerSelected = customerSelected;
	}

	public LazyDataModel<Customer> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Customer> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public SelectItem[] getSexOptions() {
		return sexOptions;
	}

	public void setSexOptions(SelectItem[] sexOptions) {
		this.sexOptions = sexOptions;
	}

	public SelectItem[] getActiveOptions() {
		return activeOptions;
	}

	public void setActiveOptions(SelectItem[] activeOptions) {
		this.activeOptions = activeOptions;
	}

	public CityManager getCityManager() {
		return cityManager;
	}

	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

}
