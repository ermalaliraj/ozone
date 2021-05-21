package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.model.City;
import al.ozone.bl.utils.ZUtils;


/**
 * @author Florjan Gogaj
 * 
 * ViewScoped Managed Bean.
 * This class is the backing bean of "citiesList.jsp". The class takes the data needed
 * in DB, using BL methods, and save them for later showing in "partnerDetail.jsp".
 */

@ManagedBean(name="cityController")
@ViewScoped
public class CityController implements Serializable{

	private static final long serialVersionUID = 1342984578732340382L;

	protected static final transient Log logger = LogFactory.getLog(CityController.class);

	//Binding variables - Edit city form
	private HtmlOutputText fId;
	private HtmlOutputText fName;
	private HtmlInputText fLat;
	private HtmlInputText fLng;
	private HtmlInputText fZoomLevel;
	private HtmlSelectBooleanCheckbox fActive;
	
	//Binding Variables - New city form 
	private HtmlInputText newName;
	private HtmlInputText newId;
	private HtmlInputText newLat;
	private HtmlInputText newLng;
	private HtmlInputText newZoomLevel;
	private HtmlSelectBooleanCheckbox newActive;
	

	
	//Session data
	private List<City> citiesList;
	private City citySelected;	
	private boolean editMode;

	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	
	/**
	 * Initializes the page. It calls first getAllCities() method.
	 */
	@PostConstruct
	public void init(){
		getAllCities();
	}
	
	/**
	 * It gets the list of all the cities found in the database.
	 */
	public void getAllCities(){		
		List<City> list = cityManager.getAll();
		setCitiesList(list);
	}
	
	/**
	 * Inserts a new city in the database. Is is used by the new city dialog form.
	 */
	public void addCity(){    
		City c = new City();
		c.setId(newId.getValue().toString());
		c.setName(JSFUtils.getStringFromUIInput(newName));
		c.setActive(JSFUtils.getBooleanFromUIInput(newActive));
		c.setLat(JSFUtils.getDoubleFromUIInput(newLat));
		c.setLng(JSFUtils.getDoubleFromUIInput(newLng));
		c.setZoomLevel(JSFUtils.getIntegerFromUIInput(newZoomLevel));

		try {
			cityManager.insert(c);
			logger.info("City "+c.getName()+" with ID "+c.getId()+" inserted");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "City Added",  "New City added successfully in database.");  
			FacesContext.getCurrentInstance().addMessage(null, message);		
		} catch (Exception e) {
			logger.error("City "+c.getName()+" with ID "+c.getId()+" cannot be inserted", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot insert the city",  ZUtils.getShortString(e.getCause().toString(), 50));  
			FacesContext.getCurrentInstance().addMessage(null, message); 
		}
		cleanNewCityForm();
		getAllCities(); 
    }

	/**
	 * Cleans the new city dialog form, so the previous inserted values
	 * will not be shown in the next insert attempt.
	 */
	
	public void cleanNewCityForm(){
		newName.setValue(null);
		newId.setValue(null);
		newActive.setValue(null);
		newLat.setValue(null);
		newLng.setValue(null);
		newZoomLevel.setValue(null);
	}
	
	/**
	 * Cleans the edit city dialog form, so the previous inserted values
	 * will not be shown in the next edit attempt.
	 */
	public void cleanEditCityForm(){
		fName.setValue(null);
		fId.setValue(null);
		fLat.setValue(null);
		fLng.setValue(null);
		fZoomLevel.setValue(null);
		fActive.setValue(null);
	}
	
	/**
	 * Updates the city selected information. Is used by the edit dialog form.
	 */
	public void updateCity(){    
		City c = new City();
		c.setId(citySelected.getId());
		c.setName(citySelected.getName());
		c.setActive(JSFUtils.getBooleanFromUIInput(fActive));
		c.setLat(JSFUtils.getDoubleFromUIInput(fLat));
		c.setLng(JSFUtils.getDoubleFromUIInput(fLng));
		c.setZoomLevel(JSFUtils.getIntegerFromUIInput(fZoomLevel));

		try {
			cityManager.update(c);
	        logger.info("City: "+c.getId()+" updated");
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "City Updated",  "City updated successfully with no errors.");  
			FacesContext.getCurrentInstance().addMessage(null, message);  
		} catch (Exception e) {
			logger.error("City: "+c.getId()+" can not be updated. Error:"+e.getMessage());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating the city", ZUtils.getShortString(e.getCause().toString(), 50));  
			FacesContext.getCurrentInstance().addMessage(null, message);
		}  
		
        //cleanEdit
        cleanEditCityForm();
        getAllCities(); 
        setEditMode(false);        
    }
	
	/**
	 * Deletes the selected city from the data base. The operation is not reversible,
	 * so there is not any reference of the deleted city saved somewhere.  
	 */
	public void deleteCity(){
		try {
			cityManager.delete(citySelected);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "City Deleted",  "City deleted successfully with no errors.");  
			FacesContext.getCurrentInstance().addMessage(null, message);  
			logger.info("City: "+citySelected.getId()+" deleted");
		}catch (Exception e) {
			logger.error("City: "+citySelected.getId()+" can not be deleted.", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete the city", ZUtils.getShortString(e.getCause().toString(), 50));  
			FacesContext.getCurrentInstance().addMessage(null, message);
		}  	
		getAllCities();
	}
	

	public HtmlInputText getfLat() {
		return fLat;
	}

	public void setfLat(HtmlInputText fLat) {
		this.fLat = fLat;
	}

	public HtmlInputText getfLng() {
		return fLng;
	}

	public void setfLng(HtmlInputText fLng) {
		this.fLng = fLng;
	}

	public HtmlInputText getfZoomLevel() {
		return fZoomLevel;
	}

	public void setfZoomLevel(HtmlInputText fZoomLevel) {
		this.fZoomLevel = fZoomLevel;
	}

	public HtmlSelectBooleanCheckbox getfActive() {
		return fActive;
	}

	public void setfActive(HtmlSelectBooleanCheckbox fActive) {
		this.fActive = fActive;
	}
	
	public HtmlInputText getNewZoomLevel() {
		return newZoomLevel;
	}

	public void setNewZoomLevel(HtmlInputText newZoomLevel) {
		this.newZoomLevel = newZoomLevel;
	}

	public HtmlInputText getNewLat() {
		return newLat;
	}

	public void setNewLat(HtmlInputText newLat) {
		this.newLat = newLat;
	}

	public HtmlInputText getNewLng() {
		return newLng;
	}

	public void setNewLng(HtmlInputText newLng) {
		this.newLng = newLng;
	}


	public HtmlSelectBooleanCheckbox getNewActive() {
		return newActive;
	}

	public void setNewActive(HtmlSelectBooleanCheckbox newActive) {
		this.newActive = newActive;
	}

	public HtmlOutputText getfId() {
		return fId;
	}

	public void setfId(HtmlOutputText fId) {
		this.fId = fId;
	}

	public HtmlInputText getNewName() {
		return newName;
	}

	public void setNewName(HtmlInputText newName) {
		this.newName = newName;
	}

	public HtmlInputText getNewId() {
		return newId;
	}

	public void setNewId(HtmlInputText newId) {
		this.newId = newId;
	}
	
	public static Log getLogger() {
		return logger;
	}

	public List<City> getCitiesList() {
		return citiesList;
	}

	public void setCitiesList(List<City> citiesList) {
		this.citiesList = citiesList;
	}

	public City getCitySelected() {
		return citySelected;
	}

	public void setCitySelected(City citySelected) {
		this.citySelected = citySelected;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public CityManager getCityManager() {
		return cityManager;
	}

	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}

	public HtmlOutputText getfName() {
		return fName;
	}

	public void setfName(HtmlOutputText fName) {
		this.fName = fName;
	}
}
