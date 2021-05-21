package al.ozone.admin.backing;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Partner;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 */

@ManagedBean(name="partnerBacking")
@ViewScoped //first edit call is empty
public class PartnerBacking implements Serializable{

	private static final long serialVersionUID = 5739774383969792012L;
	protected static final transient Log logger = LogFactory.getLog(PartnerBacking.class);
	
	//Binding Variables - Partner's detail form
//	private HtmlInputText fId;
//	private HtmlInputText fName;
//	private HtmlSelectOneMenu fCity;
//	//private String idCityChoosed;
//	private HtmlInputTextarea fAddress;
//	private HtmlInputText fTel;
//	private HtmlInputText fCel;
//	private HtmlInputText fEmail;
//	
//    private MapModel mapEmptyModel;  
//    private String mapTitle; 
//    private double mapLat;  
//    private double mapLng; 
//    private int zoomLevel;
//    
//	//Binding Variables - New Partner form 
//	private HtmlInputText newName;
//	private HtmlSelectOneMenu newCity;
//	private HtmlInputTextarea newAddress;
//	private HtmlInputText newTel;
//	private HtmlInputText newCel;
//	private HtmlInputText newEmail;
	
	private Partner partnerSelected;
	
	
//	private boolean editMode;
//	
//	// City drop-down
//	private List<SelectItem> allCityItems;
//	
//	// Injected properties
//	@ManagedProperty(value="#{partnerManager}") 
//	private PartnerManager partnerManager;	
//	@ManagedProperty(value="#{cityManager}") 
//	private CityManager cityManager;
//	
//	//Page initialization
//	@PostConstruct
//	public void init(){	
//		System.out.println("dentro PartnerBacking*************");
//		allCityItems = new ArrayList<SelectItem>();
//		List<City> allCities = cityManager.getAll();
//		for (City c : allCities) {
//			allCityItems.add(new SelectItem(c.getId(), c.getName()));
//		}
//	}
//
//	public void cleanNewPartnerForm(){
//		newName.setValue(null);
//		newCity.setValue("");
//		newAddress.setValue(null);
//		newTel.setValue(null);
//		newCel.setValue(null);
//		newEmail.setValue(null);
//	}
	
//	public void addPartner() {
//		Partner p = new Partner();
//		p.setName(newName.getValue().toString());
//		p.setAddress(newAddress.getValue().toString());
//		p.setTel(newTel.getValue().toString());
//		p.setCel(newCel.getValue().toString());
//		p.setEmail(newEmail.getValue().toString());
//		
//		String cityId = JSFUtils.getStringFromUIInput(newCity);			
//		City c = cityManager.get(cityId);
//		p.setCity(c);
//		p.setLat(c.getLat());
//		p.setLng(c.getLng());
//		p.setZoomLevel(c.getZoomLevel());
//		logger.info("going to insert Partner: "+p);
//		
//		try {
//			partnerManager.insert(p);
//			logger.info("Partner "+p.getName()+" with ID "+p.getId()+" inserted from Backing.");
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success -",  "Partner "+p.getId()+" added successfully in the system.");  
//			FacesContext.getCurrentInstance().addMessage(null, message);
//			editMode = !editMode;
//			partnerSelected = (Partner) p.clone();
//		} catch (Exception e) {
//			logger.error("Partner "+p.getName()+" with ID "+p.getId()+" cannot be inserted from controller.");
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error insering the partner", JSFUtils.getShortString(e.getCause().toString(), 50));  
//			FacesContext.getCurrentInstance().addMessage(null, message); 
//		}
//		cleanNewPartnerForm();
//    }
//	
//	public void updatePartner() {    
//		Partner p = new Partner();
//		p.setId(partnerSelected.getId());
//		p.setName(fName.getValue().toString());
//		p.setAddress(fAddress.getValue().toString());
//		p.setTel(fTel.getValue().toString());
//		p.setCel(fCel.getValue().toString());
//		p.setEmail(fEmail.getValue().toString());
//		
//		String cityId = JSFUtils.getStringFromUIInput(fCity);			
//		// City c = cityManager.get(cityId);
//		p.setCity(new City(cityId));
//		p.setLat(mapLat);
//		p.setLng(mapLng);
//		p.setZoomLevel(zoomLevel);
//		logger.info("going to update Partner: "+p);
//		
//		try {
//			partnerManager.update(p);
//	        logger.info("Partner: "+p.getId()+" updated from controller."); 
//	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success -",  "Partner "+p.getId()+" updated successfully.");  
//			FacesContext.getCurrentInstance().addMessage(null, message);  
//			partnerSelected = (Partner) p.clone();
//		} catch (Exception e) {
//			logger.error("Partner: "+p.getId()+" can not be updated. Error:"+e.getMessage());
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating the partner", JSFUtils.getShortString(e.getCause().toString(), 50));  
//			FacesContext.getCurrentInstance().addMessage(null, message);
//		}        
//    }
//	
//	public void deletePartner() {   
//		try {
//			partnerManager.delete(partnerSelected);
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success -",  "Partner "+partnerSelected.getId()+" deleted successfully from the system.");  
//			FacesContext.getCurrentInstance().addMessage(null, message);  
//			logger.info("Partner: "+partnerSelected.getId()+" deleted from controller.");
//		}catch (Exception e) {
//			logger.error("Partner: "+partnerSelected.getId()+" can not be deleted.", e);
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete the partner", JSFUtils.getShortString(e.getCause().toString(), 50));  
//			FacesContext.getCurrentInstance().addMessage(null, message);
//		}  		
//    }
	
//    public void onMapStateChange(StateChangeEvent event) {  
//    	LatLngBounds bounds = event.getBounds();  
//        LatLng latLng = bounds.getCenter();
//        mapLat = latLng.getLat();
//        mapLng = latLng.getLng();
//        zoomLevel = event.getZoomLevel();  
//        System.out.println("zoomLevel to :"+zoomLevel+" mapLat="+mapLat+" mapLng="+mapLng);
//    }  
//    
//    public void insertNewPartner(){
//    	partnerSelected = new Partner();
//    	partnerSelected.setLat(0);
//    	partnerSelected.setLng(0);
//    	partnerSelected.setZoomLevel(0);    	
//    }
//	
//    
//    public void cityChangeListener(ValueChangeEvent e) throws AbortProcessingException {
//    	String newCityChoosed = (String) e.getNewValue();
//    	System.out.println("newCityChoosed:"+newCityChoosed);
//    	City c = cityManager.get(newCityChoosed);
//    	if(c!=null){
//	    	mapLat = c.getLat();
//	    	mapLng = c.getLng();
//	    	zoomLevel = c.getZoomLevel();
//    	}
//    }
//    
//	public HtmlInputText getfId() {
//		return fId;
//	}
//
//	public void setfId(HtmlInputText fId) {
//		this.fId = fId;
//	}
//
//	public HtmlInputText getfName() {
//		return fName;
//	}
//
//	public void setfName(HtmlInputText fName) {
//		this.fName = fName;
//	}
//
//	public HtmlSelectOneMenu getfCity() {
//		return fCity;
//	}
//
//	public void setfCity(HtmlSelectOneMenu fCity) {
//		this.fCity = fCity;
//	}
//
//	public HtmlInputTextarea getfAddress() {
//		return fAddress;
//	}
//
//	public void setfAddress(HtmlInputTextarea fAddress) {
//		this.fAddress = fAddress;
//	}
//
//	public HtmlInputText getfTel() {
//		return fTel;
//	}
//
//	public void setfTel(HtmlInputText fTel) {
//		this.fTel = fTel;
//	}
//
//	public HtmlInputText getfCel() {
//		return fCel;
//	}
//
//	public void setfCel(HtmlInputText fCel) {
//		this.fCel = fCel;
//	}
//
//	public HtmlInputText getfEmail() {
//		return fEmail;
//	}
//
//	public void setfEmail(HtmlInputText fEmail) {
//		this.fEmail = fEmail;
//	}
//
//	public HtmlInputText getNewName() {
//		return newName;
//	}
//
//	public void setNewName(HtmlInputText newName) {
//		this.newName = newName;
//	}
//
//	public HtmlSelectOneMenu getNewCity() {
//		return newCity;
//	}
//
//	public void setNewCity(HtmlSelectOneMenu newCity) {
//		this.newCity = newCity;
//	}
//
//	public HtmlInputTextarea getNewAddress() {
//		return newAddress;
//	}
//
//	public void setNewAddress(HtmlInputTextarea newAddress) {
//		this.newAddress = newAddress;
//	}
//
//	public HtmlInputText getNewTel() {
//		return newTel;
//	}
//
//	public void setNewTel(HtmlInputText newTel) {
//		this.newTel = newTel;
//	}
//
//	public HtmlInputText getNewCel() {
//		return newCel;
//	}
//
//	public void setNewCel(HtmlInputText newCel) {
//		this.newCel = newCel;
//	}
//
//	public HtmlInputText getNewEmail() {
//		return newEmail;
//	}
//
//	public void setNewEmail(HtmlInputText newEmail) {
//		this.newEmail = newEmail;
//	}
//
	public Partner getPartnerSelected() {
		if(partnerSelected!=null){
			//System.out.println("getPartnerSelected:"+partnerSelected);
			return partnerSelected;
		}else{
			//System.out.println("getPartnerSelected_new:"+ZUtils.getNewPartner());
			return ZUtils.getNewPartner();
		}
	}
//
	public void setPartnerSelected(Partner partnerSelected) {
		//System.out.println("*setPartnerSelected:"+partnerSelected);
		this.partnerSelected = partnerSelected;
		//idCityChoosed = partnerSelected.getCity().getId();
	}
//
//	public boolean isEditMode() {
//		return editMode;
//	}
//
//	public void setEditMode(boolean editMode) {
//		this.editMode = editMode;
//	}
//
//	public List<SelectItem> getAllCityItems() {
//		return allCityItems;
//	}
//
//	public void setAllCityItems(List<SelectItem> allCityItems) {
//		this.allCityItems = allCityItems;
//	}

//	public PartnerManager getPartnerManager() {
//		return partnerManager;
//	}
//
//	public void setPartnerManager(PartnerManager partnerManager) {
//		this.partnerManager = partnerManager;
//	}
//
//	public CityManager getCityManager() {
//		return cityManager;
//	}
//
//	public void setCityManager(CityManager cityManager) {
//		this.cityManager = cityManager;
//	}
//
//	public MapModel getMapEmptyModel() {
//		return mapEmptyModel;
//	}
//
//	public void setMapEmptyModel(MapModel mapEmptyModel) {
//		this.mapEmptyModel = mapEmptyModel;
//	}
//
//	public String getMapTitle() {
//		return mapTitle;
//	}
//
//	public void setMapTitle(String mapTitle) {
//		this.mapTitle = mapTitle;
//	}
//
//	public double getMapLat() {
//		return mapLat;
//	}
//
//	public void setMapLat(double mapLat) {
//		this.mapLat = mapLat;
//	}
//
//	public double getMapLng() {
//		return mapLng;
//	}
//
//	public void setMapLng(double mapLng) {
//		this.mapLng = mapLng;
//	}
//
//	public int getZoomLevel() {
//		return zoomLevel;
//	}
//
//	public void setZoomLevel(int zoomLevel) {
//		this.zoomLevel = zoomLevel;
//	}

//	public String getIdCityChoosed() {
//		System.out.println("getIdCityChoosed:"+idCityChoosed);
//		return idCityChoosed;
//	}
//
//	public void setIdCityChoosed(String idCityChoosed) {
//		System.out.println("setIdCityChoosed:"+idCityChoosed);
//		System.out.println("partnerSelected.getCity():"+partnerSelected.getCity());
//		this.idCityChoosed = partnerSelected.getCity().getId();
//	}

}
