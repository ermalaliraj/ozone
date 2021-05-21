package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataIntegrityViolationException;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.City;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.model.Partner;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 * Backing bean of "partnersList.xhtml" and "partnerNew.xhtml".
 * The class acts as a Controller using the Business Logic Layer to get and save data in 
 * database.
 */

@ManagedBean(name="partnerController")
@ViewScoped
public class PartnerController implements Serializable{

	private static final long serialVersionUID = 5739774383969792012L;
	protected static final transient Log logger = LogFactory.getLog(PartnerController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sPartnerName;	
	private HtmlSelectOneMenu sCityId;
	private HtmlSelectOneMenu sCategoryId;
	private LazyDataModel<Partner> lazyModel;
	private List<SelectItem> categoryItems;
	private List<SelectItem> allCityItems;

	//Binding Variables - Partner's detail form
	private HtmlInputText fId;
	private HtmlInputText fName;
	private HtmlSelectOneMenu fCity;
	private HtmlSelectOneMenu fCategory;
	private HtmlInputTextarea fAddress;
	private HtmlInputText fTel;
	private HtmlInputText fCel;
	private HtmlInputText fEmail;
	private HtmlInputText fPwd;
	private HtmlInputText fWebsite;
	
	//Binding Variables - New Partner form 
	private HtmlInputText newName;
	private HtmlSelectOneMenu newCity;
	private HtmlSelectOneMenu newCategory;
	private HtmlInputTextarea newAddress;
	private HtmlInputText newTel;
	private HtmlInputText newCel;
	private HtmlInputText newEmail;
	private HtmlInputText newPwd;
	private HtmlInputText newWebsite;
	
	//data needed for the session
	private List<Partner> partnersList;
	private Partner partnerSelected;	
	private boolean editMode;

	// Injected properties
	@ManagedProperty(value="#{partnerManager}") 
	private PartnerManager partnerManager;	
	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	
	//Page initialization
	@PostConstruct
	public void init(){			
		categoryItems = new ArrayList<SelectItem>();
		allCityItems = new ArrayList<SelectItem>();
		List<City> allCities = cityManager.getAllActives();
		for (City c : allCities) {
			allCityItems.add(new SelectItem(c.getId(), c.getName()));
		}
		searchPartner();
		updateCategoryItems();
		//Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		//System.out.println("locale get from context:"+locale);
	}
	
	public void searchPartner(){
		Partner p = readSearchForm();
		int count = partnerManager.searchCount(p);
		lazyModel = null;
		getLazyModel().setRowCount(count);
	}
	
	private Partner readSearchForm() {
		Partner p = new Partner();
		p.setName(JSFUtils.getStringFromUIInput(sPartnerName));
		String cityId = JSFUtils.getStringFromUIInput(sCityId);
		p.setCity(new City(cityId));
		Integer categoryId = JSFUtils.getIntegerFromUIInput(sCategoryId);
		p.setCategory(new Category(categoryId));
		return p;
	}
	
	/**
	 * Get Drop-down category items depending on the language
	 */
	public void updateCategoryItems(){
		List<Category> cat = partnerManager.getCategories();
		String language = JSFUtils.getSystemLanguage();
		if(language.equals("al")){
			for(Category c: cat){
				categoryItems.add(new SelectItem(c.getId(),c.getNameAl()));
			}
		}
		
		if(language.equals("en")){
			for(Category c: cat){
				categoryItems.add(new SelectItem(c.getId(),c.getNameEn()));
			}
		}	
	}
	
	/**
	 * Update Category Name fields on partner list depending on the language
	 */
	public void updateCategoryNameFields(){
		String language = JSFUtils.getSystemLanguage();
		if(language.equals("al")){
			//categoryName on list
			for(Partner p: partnersList){
		    	Category c = p.getCategory();
		    	c.setName(c.getNameAl());
		    	p.setCategory(c);
		    }	
		}
		
		if(language.equals("en")){
			for(Partner p: partnersList){
	          Category c = p.getCategory();
	          c.setName(c.getNameEn());
	          p.setCategory(c);
	      }
		}	
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sPartnerName.setValue(null);
		sCityId.setValue("");
		if(sCategoryId!=null){
			sCategoryId.setValue(null);
		}
	}

	public void cleanNewPartnerForm(){
		newName.setValue(null);
		newCategory.setValue(null);
		newCity.setValue("");
		newAddress.setValue(null);
		newTel.setValue(null);
		newCel.setValue(null);
		newEmail.setValue(null);
		newWebsite.setValue(null);
	}
	
	public void addPartner() {
		Partner p = new Partner();
		p.setName(newName.getValue().toString());	
		String cityId = JSFUtils.getStringFromUIInput(newCity);
		p.setCity(new City(cityId));			
		p.setAddress(newAddress.getValue().toString());
		p.setWebSite(JSFUtils.getStringFromUIInput(newWebsite));
		p.setTel(newTel.getValue().toString());
		p.setCel(newCel.getValue().toString());
		String email = JSFUtils.getStringFromUIInput(newEmail).trim();
		// to respect uniqueness in the DB email field 
		if(email.equals("")){
			p.setEmail(null);
		} else{
			p.setEmail(email);
		}
		String pwd = JSFUtils.getStringFromUIInput(newPwd).trim();
		if(pwd.equals("")){
			p.setPassword(null);
		} else{
			p.setPassword(pwd);
		}

		Integer categoryId = JSFUtils.getIntegerFromUIInput(newCategory);
		p.setCategory(new Category(categoryId));
	
		City c = cityManager.get(cityId);
		p.setLat(c.getLat());
		p.setLng(c.getLng());
		p.setZoomLevel(c.getZoomLevel());
		
		try {
			partnerManager.insert(p);
			logger.info("Partner "+p.getName()+" with ID "+p.getId()+" inserted from controller.");
			JSFUtils.addInfoMessage("New Partner added successfully in database.");
		} catch (Exception e) {
			logger.error("Partner "+p.getName()+" with ID "+p.getId()+" cannot be inserted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
		cleanSearchForm();
		cleanNewPartnerForm();
		searchPartner(); 
    }
	
	public void updatePartner() {    
		Partner p = new Partner();
		p.setId(partnerSelected.getId());
		p.setName(fName.getValue().toString());
		String cityId = JSFUtils.getStringFromUIInput(fCity);
		p.setCity(new City(cityId));			
		p.setAddress(fAddress.getValue().toString());
		p.setWebSite(JSFUtils.getStringFromUIInput(fWebsite));
		p.setTel(fTel.getValue().toString());
		p.setCel(fCel.getValue().toString());
		
		String email = JSFUtils.getStringFromUIInput(fEmail).trim();
		// to respect uniqueness in the DB email field 
		if(email.equals("")){
			p.setEmail(null);
		} else{
			p.setEmail(email);
		}
		String pwd = JSFUtils.getStringFromUIInput(fPwd).trim();
		if(pwd.equals("")){
			p.setPassword(null);
		} else{
			p.setPassword(pwd);
		}
		
		Integer categoryId = JSFUtils.getIntegerFromUIInput(fCategory);
		p.setCategory(new Category(categoryId));	
		
		if(partnerSelected.getLat()!=0.0  
			|| partnerSelected.getLng()!=0.0 
			|| partnerSelected.getZoomLevel()!=0){
			
			p.setLat(partnerSelected.getLat());
			p.setLng(partnerSelected.getLng());
			p.setZoomLevel(partnerSelected.getZoomLevel());
		}else{
			City c = cityManager.get(cityId);
			p.setLat(c.getLat());
			p.setLng(c.getLng());
			p.setZoomLevel(c.getZoomLevel());
		}
			

		//logger.info("going to update Partner: "+p);
		
		try {
			partnerManager.update(p);
			this.editMode = false;
	        logger.info("Partner: "+p.getId()+" updated from controller.");
			JSFUtils.addInfoMessage("Partner updated successfully with no errors.");
		} catch (Exception e) {
			logger.error("Partner: "+p.getId()+" can not be updated", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}        
		cleanSearchForm();
        searchPartner(); 
    }
	
	public void deletePartner() {
		try {
			partnerManager.delete(partnerSelected);
			JSFUtils.addInfoMessage("Partner deleted successfully with no errors.");
			logger.info("Partner: "+partnerSelected.getId()+" deleted from controller.");
		}catch(DataIntegrityViolationException e1){
			logger.error("Partner: "+partnerSelected.getId()+" can not be deleted. The parnter is used from a deal. Error:"+e1.getMessage());
			JSFUtils.addErrorMessage("Parter busy from a Deal");
		}
		catch (Exception e) {
			logger.error("Partner: "+partnerSelected.getId()+" can not be deleted.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}  		
		searchPartner();		
    }
    
	public LazyDataModel<Partner> getLazyModel() {
		if (lazyModel==null){  
			lazyModel = new LazyDataModel<Partner>() {
				private static final long serialVersionUID = 1545429440052790946L;

				@Override
				public List<Partner> load(int first, int pageSize, String sortField,
						SortOrder sortOrder, Map<String, String> filters) {
			        
					String sortDir = "";
			        if (SortOrder.ASCENDING.equals(sortOrder)) {
			            sortDir = "ASC";
			        } else if (SortOrder.DESCENDING.equals(sortOrder)) {
			            sortDir = "DESC";
			        }
			        
			        Partner p = readSearchForm();
			        p.setSortColumn(sortField);
					p.setSortDirection(sortDir);

					PagedResult<Partner> pagedResult = partnerManager.loadLazy(p, first, pageSize);
					setRowCount(pagedResult.getTotalCount());  
					setWrappedData(pagedResult.getRowList());
					setPartnersList(pagedResult.getRowList());
					
					//ZUtils.printListOnLogger(pagedResult.getRowList(), logger, "debug");
					//logger.debug("Got from DB "+pagedResult.getRowList().size()+" partners");
					updateCategoryNameFields();
					return pagedResult.getRowList(); 
				}
				
			    @Override
			    /**
			     * Override setRowIndex() as workaround for exception on RequestScope
			     * Issue 1544: LazyDataTable.setRowIndex throws arithmetic exception: division by 0.
			     */
			    public void setRowIndex(int rowIndex) {
			        /*
			         * The following is in ancestor (LazyDataModel):
			         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
			         */
			        if (rowIndex == -1 || getPageSize() == 0) {
			            super.setRowIndex(-1);
			        }
			        else
			            super.setRowIndex(rowIndex % getPageSize());
			    }

	        };
		}
        return lazyModel;
	}
	
	/**
	 * Put partnerSelected.id in session.
	 * The full Partner will be retrieved from db in DealWizard.init()
	 * @return String used for navigation rule.
	 */
	public String newDealForPartner(){
		JSFUtils.putObjectInSession("partnerInSession", partnerSelected.getId());
		//remove dealSelectedId if present, because a new Deal will be create
		JSFUtils.removeObjectFromSession("dealInSession");
		return "success";
	}
	
	// Getters and Setters
	public HtmlInputText getsPartnerName() {
		return sPartnerName;
	}

	public void setsPartnerName(HtmlInputText sPartnerName) {
		this.sPartnerName = sPartnerName;
	}

	public HtmlSelectOneMenu getsCityId() {
		return sCityId;
	}

	public void setsCityId(HtmlSelectOneMenu sCityId) {
		this.sCityId = sCityId;
	}

	public void setLazyModel(LazyDataModel<Partner> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public HtmlInputText getfName() {
		return fName;
	}

	public void setfName(HtmlInputText fName) {
		this.fName = fName;
	}

	public HtmlInputTextarea getfAddress() {
		return fAddress;
	}

	public void setfAddress(HtmlInputTextarea fAddress) {
		this.fAddress = fAddress;
	}

	public HtmlInputText getfTel() {
		return fTel;
	}

	public void setfTel(HtmlInputText fTel) {
		this.fTel = fTel;
	}

	public HtmlInputText getfCel() {
		return fCel;
	}

	public void setfCel(HtmlInputText fCel) {
		this.fCel = fCel;
	}

	public HtmlInputText getfEmail() {
		return fEmail;
	}

	public void setfEmail(HtmlInputText fEmail) {
		this.fEmail = fEmail;
	}

	public List<Partner> getPartnersList() {
		return partnersList;
	}

	public void setPartnersList(List<Partner> partnersList) {
		this.partnersList = partnersList;
	}

	public Partner getPartnerSelected() {
		return partnerSelected;
	}

	public void setPartnerSelected(Partner partnerSelected) {
		this.partnerSelected = partnerSelected;
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<SelectItem> getAllCityItems() {
		return allCityItems;
	}

	public void setAllCityItems(List<SelectItem> allCityItems) {
		this.allCityItems = allCityItems;
	}

	public PartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(PartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}

	public CityManager getCityManager() {
		return cityManager;
	}

	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}

	public HtmlSelectOneMenu getfCity() {
		return fCity;
	}

	public void setfCity(HtmlSelectOneMenu fCity) {
		this.fCity = fCity;
	}

	public HtmlInputText getNewName() {
		return newName;
	}

	public void setNewName(HtmlInputText newName) {
		this.newName = newName;
	}

	public HtmlSelectOneMenu getNewCity() {
		return newCity;
	}

	public void setNewCity(HtmlSelectOneMenu newCity) {
		this.newCity = newCity;
	}

	public HtmlInputTextarea getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(HtmlInputTextarea newAddress) {
		this.newAddress = newAddress;
	}

	public HtmlInputText getNewTel() {
		return newTel;
	}

	public void setNewTel(HtmlInputText newTel) {
		this.newTel = newTel;
	}

	public HtmlInputText getNewCel() {
		return newCel;
	}

	public void setNewCel(HtmlInputText newCel) {
		this.newCel = newCel;
	}

	public HtmlInputText getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(HtmlInputText newEmail) {
		this.newEmail = newEmail;
	}

	public HtmlInputText getfId() {
		return fId;
	}

	public void setfId(HtmlInputText fId) {
		this.fId = fId;
	}

	public HtmlInputText getfWebsite() {
		return fWebsite;
	}

	public void setfWebsite(HtmlInputText fWebsite) {
		this.fWebsite = fWebsite;
	}

	public HtmlInputText getNewWebsite() {
		return newWebsite;
	}

	public void setNewWebsite(HtmlInputText newWebsite) {
		this.newWebsite = newWebsite;
	}

	public HtmlSelectOneMenu getsCategoryId() {
		return sCategoryId;
	}

	public void setsCategoryId(HtmlSelectOneMenu sCategoryId) {
		this.sCategoryId = sCategoryId;
	}

	public List<SelectItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<SelectItem> categoryItems) {
		this.categoryItems = categoryItems;
	}

	public HtmlSelectOneMenu getfCategory() {
		return fCategory;
	}

	public void setfCategory(HtmlSelectOneMenu fCategory) {
		this.fCategory = fCategory;
	}

	public HtmlSelectOneMenu getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(HtmlSelectOneMenu newCategory) {
		this.newCategory = newCategory;
	}

	public HtmlInputText getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(HtmlInputText newPwd) {
		this.newPwd = newPwd;
	}

	public HtmlInputText getfPwd() {
		return fPwd;
	}

	public void setfPwd(HtmlInputText fPwd) {
		this.fPwd = fPwd;
	}

}
