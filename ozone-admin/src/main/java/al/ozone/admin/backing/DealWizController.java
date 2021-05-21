package al.ozone.admin.backing;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.dao.DuplicateKeyException;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.exception.FileAlreadyExistException;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Partner;
import al.ozone.bl.utils.EnvConfig;
import al.ozone.bl.utils.ZUtils;

/**
 * On the creation time, reads from the session variables <b>dealInSession</b> 
 * and <b>partnerInSession</b>, in order to understand if we are in Deal edit mode, 
 * if we come with a partner already selected or if we have a new deal with 
 * no partner selected until now. On creation time, considering partner
 * coordinates, creates a marker with absolute coordinates for the partner.
 * On each marker drag, primefaces handles all the necessary so, 
 * we have in server side the status updated with new coordinates.
 * For this, on updatePartner(), partner coordinates are read form the marker.
 * On Deal creation the marker has the city coordinates which are copied as
 * partner properties when updateParter(). No other marker can be added so
 * the marker must be dragged, until reached the desired coordinates.
 * Switching from 1)zoom to 2)marker drag is not stable, so updatePartner 
 * before combinating 1 with 2.<br/><br/>
 * 
 * Each tabs is saved with a single dedicated method. From the last tab can 
 * be called the method saveAllTabs() which put all single methods in sequence.
 * In case of an exception we are able to notify the user for the tab which 
 * had the problem.
 * @author Ermal Aliraj
 */
@ManagedBean(name="dealWizController")
@ViewScoped
public class DealWizController implements Serializable{

	private static final long serialVersionUID = 5866673902858855703L;
	protected static final transient Log logger = LogFactory.getLog(DealWizController.class);

	private String uploadsPath;
	private String uploadsUrl;

	//TAB 1 Partner 
	private List<SelectItem> allCityItems;
    private MapModel mapEmptyModel;  
    private String mapTitle; 
//    private double mapLat;  
//    private double mapLng; 
    private int zoomLevel;
    private Marker marker;
    private Partner partnerSelected;

    //TAB 2 Deal
	private HtmlInputText fId;
	private HtmlInputTextarea fTitle;
	private HtmlInputTextarea fTitleNewsletter;
	private HtmlInputTextarea fDealChoiceTitle;
	private HtmlInputText fPrice;
	private HtmlInputText fFullPrice;
	private HtmlInputText fCommission;
	private HtmlInputText fMinCustomers;
	private HtmlInputText fMaxCustomers;
	private HtmlInputTextarea nDealChoiceTitle;
	private HtmlInputText nPrice;
	private HtmlInputText nFullPrice;
	private HtmlInputText nCommission;
	private HtmlInputText nMinCustomers;
	private HtmlInputText nMaxCustomers;		
	private HtmlInputText fDiscountDuration;
	private HtmlInputText fClientFullName;
	private HtmlInputText fClientCel;
	private HtmlInputText fBrokerFullName;
	private HtmlInputText fBrokerCel;
	private Date fContractDate;
	private HtmlInputTextarea fContractComment;
	private HtmlOutputText fPartnerId;
	private Deal dealSelected;
	private DealChoice dealChoiceSelected;	
	private int dealDuration;
	private boolean couponImmediately;
	private Date fFrom;  
    private Date fTo;
	private String fFromMaxDate;
	private String fToMinDate;
	private String fFromMinDate;
	private HtmlInputText fOrder;
      
	//TAB 3 Synthesis/Conditions
	private UploadedFile file;
	private String loadedFileName;
	private boolean skipToLast;
	
	//TAB 4 Description
	private List<String> uploadedFiles;
	
	//TAB 4 Confirmation
	private HtmlInputText fMainImgName;
	private String mainImgName;
	private HtmlSelectBooleanCheckbox fApproved;
	private boolean dealApproved;
	
	private boolean editMode;
	private boolean editModeDC;
	
	private Map<String,String> allCityMap;  
	private List<String> citiesSelected;
	
	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	@ManagedProperty(value="#{partnerManager}") 
	private PartnerManager partnerManager;
	@ManagedProperty(value="#{applicationConfig}") 
	private ApplicationConfig applicationConfig;
	@ManagedProperty(value="#{envConfig}") 
	private EnvConfig envConfig;
	
	/**
	 * Prepares the environment for the ViewScoped bean.
	 * Take from the session "dealInSession" and "partnerInSession",
	 * if present, and get the full data from DB.
	 * Creates the marker with gmap coordinates on the map.
	 */
	@PostConstruct
	public void init(){
		allCityItems = new ArrayList<SelectItem>();
		allCityMap = new HashMap<String, String>();
		List<City> allCities = cityManager.getAllActives();
		for (City c : allCities) {
			allCityItems.add(new SelectItem(c.getId(), c.getName()));
			allCityMap.put(c.getName(), c.getId());
		}
	
		//check if dealInSession present
		Integer dealId = (Integer) JSFUtils.getObjectFromSession("dealInSession");
		if(dealId!=null){
			//we are in editMode for the deal
			dealSelected = dealManager.get(dealId);
			partnerSelected = dealSelected.getPartner();
			uploadedFiles = dealSelected.getUploadedFiles();
			dealApproved = dealSelected.isApprovedForPublish();
			mainImgName = dealSelected.getMainImgName();
			couponImmediately = dealSelected.isCouponImmediately();
			editMode = true;
			//JSFUtils.removeObjectFromSession("dealInSession");
		}else{
			//check if comes from the parter, if not create an empty partner
			Integer partnerId = (Integer) JSFUtils.getObjectFromSession("partnerInSession");
			if(partnerId!=null){
				partnerSelected = partnerManager.get(partnerId);
			}else{
				partnerSelected = ZUtils.getNewPartner();
			}
			dealSelected = new Deal();
			couponImmediately = true;
			editMode = false;
		}
		
		partnerSelected = JSFUtils.updateCategoryNameForPartner(partnerSelected);
		createMarkerOnMap();
		//logger.debug("Wizard started with Deal:"+dealSelected.getId());
		
		uploadsPath = envConfig.getUploadsFolder();
		uploadsUrl = envConfig.getUploadsUrl();
		fContractDate = dealSelected.getContractDate();
		editModeDC = false;
		//logger.debug("env.uploadsPath:"+uploadsPath+" uploadsUrl:"+uploadsUrl);
		
		//based on the deal selected get from DB cities for publication
		//actualy adding in a fake way TR(Tirane)
		citiesSelected = new ArrayList<String>();
		citiesSelected.add("TR");
		
		fFrom = dealSelected.getStartDate();
		fTo = dealSelected.getEndDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fFromMinDate = dateFormat.format(ZUtils.addDaysToDate(new Date(), 1));
		fToMinDate = dateFormat.format(ZUtils.addDaysToDate(new Date(), 1));
	}

	/**
	 * Considering the partner coordinates, creates the marker on the map
	 */
	private void createMarkerOnMap() {
		double mapLat = partnerSelected.getLat();
		double mapLng = partnerSelected.getLng();
		zoomLevel = partnerSelected.getZoomLevel();
		mapEmptyModel = new DefaultMapModel();
	    marker = new Marker(new LatLng(mapLat, mapLng), partnerSelected.getName());
		marker.setDraggable(true);
		mapEmptyModel.addOverlay(marker);  
	}
	
	/**
	 * Clean input fields on DealDataTab
	 */
	private void cleanDealTab(){
		fTitle.setValue(null);
		fTitleNewsletter.setValue(null);
		fPrice.setValue(null);
		fFullPrice.setValue(null);
		fCommission.setValue(null);
		fMinCustomers.setValue(null);
		fMaxCustomers.setValue(null);
		fClientFullName.setValue(null);
		fClientCel.setValue(null);
		fBrokerFullName.setValue(null);
		fBrokerCel.setValue(null);
		fContractComment.setValue(null);
	}
	
	/**
	 * Read the fields in dealDataTab and returns them as a Deal object
	 * @return deal containing the dealData fields
	 */
	private Deal readDealTab() {
		Deal d = new Deal();
		d.setId(JSFUtils.getIntegerFromUIInput(fId));
		d.setPartner(partnerSelected);
		d.setTitle(JSFUtils.getStringFromUIInput(fTitle));
		d.setTitleNewsletter(JSFUtils.getStringFromUIInput(fTitleNewsletter));		
		d.setDiscountDuration(JSFUtils.getIntegerFromUIInput(fDiscountDuration));
		d.setClientFullName(JSFUtils.getStringFromUIInput(fClientFullName));
		d.setClientCel(JSFUtils.getStringFromUIInput(fClientCel));
		d.setBrokerFullName(JSFUtils.getStringFromUIInput(fBrokerFullName));
		d.setBrokerCel(JSFUtils.getStringFromUIInput(fBrokerCel));
		d.setContractDate(fContractDate);
		d.setContractComment(JSFUtils.getStringFromUIInput(fContractComment));
		d.setLastUpdateUser(ZUtils.getLoggedUsername());
		d.setCouponImmediately(couponImmediately);
		d.setDealCities(citiesSelected);
		d.setStartDate(fFrom);
		d.setEndDate(fTo);
		d.setOrder(JSFUtils.getIntegerFromUIInput(fOrder));
		return d;
	}
	
	/**
	 * Add the deal in DB, reading the input fields
	 */
	public void addDeal() {
		Deal d = readDealTab();
		try {
			//logger.debug("Going to insert Deal: "+d);
			dealManager.insert(d);
			logger.info("Deal "+d.getTitle()+" inserted from controller.");
			JSFUtils.addInfoMessage("Deal nr."+d.getId()+" added successfully in the system.");
			editMode = true;
			dealSelected.setId(d.getId());
			updateDealSelectedFromDB();
			cleanDealTab();
		} catch (Exception e) {
			logger.error("Deal "+d.getTitle()+" cannot be inserted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }
	
	/**
	 * Update partner coordinated on DB
	 */
	public void updatePartner() {
		LatLng latLng = marker.getLatlng();
		partnerSelected.setLat(latLng.getLat());
		partnerSelected.setLng(latLng.getLng());
		partnerSelected.setZoomLevel(zoomLevel);
		//System.out.println("update GMap with selected-zoomLevel:"+zoomLevel+" mapLat="+latLng.getLat()+" mapLng="+latLng.getLng());
		//logger.debug("going to update Partner: "+partnerSelected);
		
		try {
			partnerManager.update(partnerSelected);
	        logger.debug("Partner: "+partnerSelected.getId()+" updated from controller."); 
			JSFUtils.addInfoMessage("Partner updated successfully.");
		} catch (Exception e) {
			logger.error("Partner: "+partnerSelected.getId()+" can not be updated. Error:"+e.getMessage());
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }
	
	/**
	 * Update deal with dealDataTab fields
	 */
	public void updateDataDeal() {    
		Deal d = readDealTab();

		if(dealSelected!=null && dealSelected.getDiscountDuration() == 0 && dealDuration != 0){
			d.setDiscountDuration(dealDuration);
		}
		
		try {
			//logger.debug("going to update deal "+d+" from controller.");
			dealManager.updateDataDeal(d);
	        logger.debug("Deal nr."+d.getId()+" Data updated from controller."); 
			JSFUtils.addInfoMessage("Deal's Data updated successfully.");
			updateDealSelectedFromDB();
		} catch (Exception e) {
			logger.error("Deal "+d.getId()+" can not be updated.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}        
    }
	
	/**
	 * Update Synthetic and Conditions editor fields
	 */
	public void updateSynthConditions(){
		Deal d = new Deal();
		d.setId(JSFUtils.getIntegerFromUIInput(fId));
		d.setSynthesis(dealSelected.getSynthesis());
		d.setConditions(dealSelected.getConditions());
		d.setLastUpdateUser(ZUtils.getLoggedUsername());
		
		try {
			//logger.debug("going to update deal "+d+" from controller.");
			dealManager.updateSynthConditions(d);
	        logger.debug("Deal nr."+d.getId()+" Conditions updated from controller.");
	        JSFUtils.addInfoMessage("Deal's Conditions updated successfully.");
			updateDealSelectedFromDB();
		} catch (Exception e) {
			logger.error("Deal "+d.getId()+" can not be updated.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}     
	}
	
	/**
	 * Update description editor field
	 */
	public void updateDescription(){
		Deal d = new Deal();
		d.setId(JSFUtils.getIntegerFromUIInput(fId));
		d.setDescription(dealSelected.getDescription());
		d.setLastUpdateUser(ZUtils.getLoggedUsername());
		
		try {
			//logger.debug("going to update deal "+d+" from controller.");
			dealManager.updateDescription(d);
	        logger.debug("Deal "+d.getId()+" updated from controller.");
			JSFUtils.addInfoMessage("Deal's Description updated successfully.");
			updateDealSelectedFromDB();
		} catch (Exception e) {
			logger.error("Deal "+d.getId()+" can not be updated.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
	}

	/**
	 * Update the content present in tab 5, confirmation tab.
	 */
	public void updateImageAndApproval(){
		Deal d = new Deal();
		d.setId(JSFUtils.getIntegerFromUIInput(fId));
		d.setMainImgName(JSFUtils.getStringFromUIInput(fMainImgName));
		boolean approved = JSFUtils.getPrimitiveBoolFromUIInput(fApproved);
		d.setApprovedForPublish(approved);
		boolean wasApproved = dealSelected.isApprovedForPublish();
		
		//If was not Approved, and now is clicked
		//Trace it as a approval event
		if(!wasApproved && approved){
			d.setApprovedUser(ZUtils.getLoggedUsername());
			d.setApprovedDate(new Date());
		} else
		//If was Approved but now the box is unchecked,
		//Trace as a NON approved deal and delete references of past user approvations.
		//TODO Enhancement: a list with all historic approval state 
		if(wasApproved && !approved){
			d.setApprovedUser(null);	
			d.setApprovedDate(null);
		} else
		//Leave the state as it is
		if(wasApproved && approved){
			d.setApprovedUser(dealSelected.getApprovedUser());	
			d.setApprovedDate(dealSelected.getApprovedDate());
		}
		d.setLastUpdateUser(ZUtils.getLoggedUsername());
		
		try {
			//logger.debug("going to update deal "+d+" from controller.");
			dealManager.updateImageAndApproval(d);
	        logger.debug("Deal "+d.getId()+" final tab, updated from controller.");
			JSFUtils.addInfoMessage("Deal's Confirmation updated successfully.");
			updateDealSelectedFromDB();
		} catch (Exception e) {
			logger.error("Deal "+d.getId()+" can not be updated.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
	}
	
	/**
	 * Lister called on Map State Change.
	 * We need to get only the zoom. The coordinated are taken from the marker.
	 * @param event StateChangeEvent event
	 */ //DO NOT REMOVE commented lines
    public void onMapStateChange(StateChangeEvent event) {  
    	//System.out.println("Were: zoomLevel:"+zoomLevel+" mapLat="+mapLat+" mapLng="+mapLng);
		//LatLngBounds bounds = event.getBounds();  
		//LatLng latLng = bounds.getNorthEast();//getCenter();
		//double mapLat = latLng.getLat();
		//double mapLng = latLng.getLng();
        zoomLevel = event.getZoomLevel();  
		//System.out.println("Are: zoomLevel:"+zoomLevel+" mapLat="+mapLat+" mapLng="+mapLng);
		//createMarkerOnMap();
    }  
    
  
    /**
     * Add a marker on the map model
     * @param event ActionEvent
     *///DO NOT REMOVE commented lines
    public void addMarker(ActionEvent actionEvent) {  
//		Marker marker = new Marker(new LatLng(mapLat, mapLng), partnerSelected.getName());
//		marker.setDraggable(true);
//		mapEmptyModel.addOverlay(marker);  
    } 
    
    /**
     * Listener called when a marker is draged
     */ //DO NOT REMOVE commented lines
    public void onMarkerDrag(MarkerDragEvent event) {  
//    	System.out.println("onMarkerDrag were zoomLevel:"+zoomLevel+" mapLat="+mapLat+" mapLng="+mapLng);
//        Marker marker = event.getMarker();  
//		LatLng latLng = marker.getLatlng();
//		mapLat = latLng.getLat();
//		mapLng = latLng.getLng();
//		System.out.println("onMarkerDrag are zoomLevel:"+zoomLevel+" mapLat="+mapLat+" mapLng="+mapLng);
    }
    
	/**
	 * Load the file physically, associate uploadedFile to the deal, 
	 * refresh uploadedFiles from DB for showing to the client.
	 * @param event FileUploadEvent event
	 */
    public void handleFileUpload(FileUploadEvent event) {  
    	String oldFileName = event.getFile().getFileName();
    	String ext = ZUtils.getFileExtension(oldFileName);
		String fileName = "deal_"+dealSelected.getId()+"_"+System.currentTimeMillis()+ext;
    	
    	try{
       		dealManager.uploadFileForDeal(dealSelected.getId(), fileName, uploadsPath, event.getFile().getInputstream());
    		JSFUtils.addInfoMessage(fileName+" uploaded.");
    	}catch(DuplicateKeyException e){
    		JSFUtils.addWarnMessage("File already present for this deal");
    	}catch (FileAlreadyExistException e) {
			JSFUtils.addWarnMessage("File \""+fileName+"\" already exists on the server");
		} catch (Exception e) {
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    	
		//refresh the list from DB
    	uploadedFiles = dealManager.getUploadedFiles(dealSelected.getId());
    }
    
	/**
	 * Remove the file physically from the server, disassociate the file 
	 * from the deal, refresh uploadedFiles from DB to be shown to the client.
	 */
	public void deleteUploadedFile(){
		dealManager.removeFileForDeal(dealSelected.getId(), loadedFileName, uploadsPath);
		uploadedFiles = dealManager.getUploadedFiles(dealSelected.getId());
	}

	/**
	 * Upload the file on the server copying the name of file in mainImgName.
	 * On updateDeal, field dealSelected.mainImgName will be valorized from mainImgName.
	 * Before uploading the file removes any file with same name.
	 * @param event FileUploadEvent
	 */
	public void uploadMainImg(FileUploadEvent event) { 
		String oldName = event.getFile().getFileName();
		String ext = ZUtils.getFileExtension(oldName);
		String fileName = "deal_" + dealSelected.getId()+"_mainImg"+ "_"+System.currentTimeMillis()+ext;
    	//String fileName = "deal_" + dealSelected.getId()+"_mainImg_"+event.getFile().getFileName();
    	try {
    		ZUtils.removeFromServer(uploadsPath, fileName);
			ZUtils.uploadOnServer(event.getFile().getInputstream(), fileName, uploadsPath);
			mainImgName = fileName;
		} catch (IOException e) {
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		} catch (FileAlreadyExistException e) {
			JSFUtils.addWarnMessage("File \""+fileName+"\" already exists on the server");
		} 
    }
    
	/**
	 * Update all TABs
	 */
	public void saveAllTabs(){
		updatePartner();
		updateDataDeal();
		updateSynthConditions();
		updateDescription();
		updateImageAndApproval();
	}
	
    /**
     * Flow Listener called on each tab changed during the navigation.
     * @param event FlowEvent
     * @return next tab-id to navigate to
     */
    public String onFlowProcess(FlowEvent event) {  
//        logger.debug("Current wizard step:" + event.getOldStep());  
//        logger.debug("Next step:" + event.getNewStep());  
        if(skipToLast) {  
        	skipToLast = false;
            return "dealWizConfirmTab";  
        }
        
        if(event.getOldStep().equals("dealWizDataTab") &&
        		event.getNewStep().equals("dealWizCondTab")){
            
        	if(!editMode){
                JSFUtils.addWarnMessage("Must Save the Deal before you go on.");
                return event.getOldStep();
            }
            
            if(dealSelected.getChoices().size()==0){
            	 JSFUtils.addWarnMessage("The deal must have at least one choice. Please use the button 'Shto Zgjedhje'");
                 return event.getOldStep();
            }            
        }
          
        if(event.getOldStep().equals("dealWizPartnerTab") && 
        		event.getNewStep().equals("dealWizDataTab") &&
        		partnerSelected.getId() == null){
            JSFUtils.addWarnMessage("Must choose the partner before you go on.");
            return event.getOldStep();
        }
        return event.getNewStep();  
    } 
    
	private void updateDealSelectedFromDB() {
		dealSelected = dealManager.get(dealSelected.getId());
		dealApproved = dealSelected.isApprovedForPublish();
		couponImmediately = dealSelected.isCouponImmediately();
	}
	
    /**
     * Get the contextWebUrl for the uploadedFile
     * @return url for the uploadedFile
     */
    public String getUrlFileName(){
    	String imgUrl = uploadsUrl + loadedFileName;
    	//logger.debug("image url to display:"+imgUrl);
    	return imgUrl;
    }
    
    public void addDealChoice() {
		DealChoice dc = new DealChoice();
    	dc.setDealId(dealSelected.getId());
    	dc.setChoiceNr(dealSelected.getChoices().size()+1); //increment choice nr
    	dc.setChoiceTitle(JSFUtils.getStringFromUIInput(nDealChoiceTitle));
    	dc.setFullPrice(JSFUtils.getIntegerFromUIInput(nFullPrice));
    	dc.setPrice(JSFUtils.getIntegerFromUIInput(nPrice));
    	dc.setCommission(JSFUtils.getDoubleFromUIInput(nCommission));    	
    	dc.setMaxCustomers(JSFUtils.getIntegerFromUIInput(nMaxCustomers));
    	dc.setMinCustomers(JSFUtils.getIntegerFromUIInput(nMinCustomers));

		try {
			//logger.debug("Going to insert Deal: "+d);
			dealManager.insertDealChoice(dc);
			logger.info("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' inserted from controller.");
			JSFUtils.addInfoMessage("Deal nr."+dc.getId()+" added successfully in the system.");
			
			List<DealChoice> dcList = dealManager.getChoicesForDeal(dealSelected.getId());
			dealSelected.setChoices(dcList);
			
			nDealChoiceTitle.setValue(null);
			nFullPrice.setValue(null);
			nPrice.setValue(null);
			nCommission.setValue(null);
			nMaxCustomers.setValue(null);
			nMinCustomers.setValue(null);
		} catch (Exception e) {
			logger.error("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' cannot be inserted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }
    
    public void updateDealChoice(){
		DealChoice dc = new DealChoice();
    	dc.setDealId(dealChoiceSelected.getDealId());
    	dc.setChoiceNr(dealChoiceSelected.getChoiceNr());
    	dc.setChoiceTitle(JSFUtils.getStringFromUIInput(fDealChoiceTitle));
    	dc.setFullPrice(JSFUtils.getIntegerFromUIInput(fFullPrice));
    	dc.setPrice(JSFUtils.getIntegerFromUIInput(fPrice));
    	dc.setCommission(JSFUtils.getDoubleFromUIInput(fCommission));
    	dc.setMaxCustomers(JSFUtils.getIntegerFromUIInput(fMaxCustomers));
    	dc.setMinCustomers(JSFUtils.getIntegerFromUIInput(fMinCustomers));

		try { 
			//logger.debug("Going to update DealChoice: "+dc);
			dealManager.updateDealChoice(dc);
			logger.info("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' updated from controller.");
			JSFUtils.addInfoMessage("DealChoice nr."+dc.getId()+" updated successfully.");
			
			List<DealChoice> dcList = dealManager.getChoicesForDeal(dealSelected.getId());
			dealSelected.setChoices(dcList);
			
			fDealChoiceTitle.setValue(null);
			fFullPrice.setValue(null);
			fPrice.setValue(null);
			fCommission.setValue(null);
			fMaxCustomers.setValue(null);
			fMinCustomers.setValue(null);
		} catch (Exception e) {
			logger.error("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' cannot be updated from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }
    
    public void deleteDealChoice(){
		DealChoice dc = new DealChoice();
		try {
	    	dc.setDealId(dealChoiceSelected.getDealId());
	    	dc.setChoiceNr(dealChoiceSelected.getChoiceNr());
			//logger.debug("Going to delete DealChoice: "+dc);
			dealManager.deleteDealChoice(dc);
			logger.info("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' deleted from controller.");
			JSFUtils.addInfoMessage("DealChoice nr."+dc.getId()+" deleted successfully.");
		
			List<DealChoice> dcList = dealManager.getChoicesForDeal(dealSelected.getId());
			dealSelected.setChoices(dcList);
		} catch (Exception e) {
			logger.error("DealChoice "+dc.getId()+": '"+dc.getChoiceTitle()+"' cannot be deleted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }
    
	public String getMainImg(){
		String mainImgUrl = uploadsUrl + mainImgName;
		//logger.debug("mainImgUrl:"+mainImgUrl);
		return mainImgUrl;
	}
	
	public Deal getDealSelected() {	
		return dealSelected;
	}
	
	/**
	 * No setDeal() will be call since the dealSelected will be created only in the init time
	 */
	public void setDealSelected(Deal dealSelected) {
	}
	
	public HtmlInputText getfDiscountDuration() {
		if(dealSelected!=null && dealSelected.getDiscountDuration() == 0){
			dealSelected.setDiscountDuration(applicationConfig.getDealDuration());
		}
		return fDiscountDuration;
	}

	public void setfDiscountDuration(HtmlInputText fDiscountDuration) {
		dealDuration=JSFUtils.getIntegerFromUIInput(fDiscountDuration);
		this.fDiscountDuration = fDiscountDuration;
	}

	
	public void setPartnerSelected(Partner partnerSelected) {
		this.partnerSelected = partnerSelected;
		createMarkerOnMap();
	}

	public Partner getPartnerSelected() {
		return partnerSelected;
	}
	
	public MapModel getMapEmptyModel() {
		return mapEmptyModel;
	}

	public void setMapEmptyModel(MapModel mapEmptyModel) {
		this.mapEmptyModel = mapEmptyModel;
	}

	public String getMapTitle() {
		return mapTitle;
	}

	public void setMapTitle(String mapTitle) {
		this.mapTitle = mapTitle;
	}
	
	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public HtmlInputText getfId() {
		return fId;
	}

	public void setfId(HtmlInputText fId) {
		this.fId = fId;
	}

	public HtmlInputTextarea getfTitle() {
		return fTitle;
	}

	public void setfTitle(HtmlInputTextarea fTitle) {
		this.fTitle = fTitle;
	}

	public HtmlInputTextarea getfTitleNewsletter() {
		return fTitleNewsletter;
	}

	public void setfTitleNewsletter(HtmlInputTextarea fTitleNewsletter) {
		this.fTitleNewsletter = fTitleNewsletter;
	}

	public HtmlInputText getfPrice() {
		return fPrice;
	}

	public void setfPrice(HtmlInputText fPrice) {
		this.fPrice = fPrice;
	}

	public HtmlInputText getfFullPrice() {
		return fFullPrice;
	}

	public void setfFullPrice(HtmlInputText fFullPrice) {
		this.fFullPrice = fFullPrice;
	}

	public HtmlInputText getfMinCustomers() {
		return fMinCustomers;
	}

	public void setfMinCustomers(HtmlInputText fMinCustomers) {
		this.fMinCustomers = fMinCustomers;
	}

	public HtmlInputText getfMaxCustomers() {
		return fMaxCustomers;
	}

	public void setfMaxCustomers(HtmlInputText fMaxCustomers) {
		this.fMaxCustomers = fMaxCustomers;
	}

	public HtmlInputText getfClientFullName() {
		return fClientFullName;
	}

	public void setfClientFullName(HtmlInputText fClientFullName) {
		this.fClientFullName = fClientFullName;
	}

	public HtmlInputText getfClientCel() {
		return fClientCel;
	}

	public void setfClientCel(HtmlInputText fClientCel) {
		this.fClientCel = fClientCel;
	}

	public HtmlInputText getfBrokerFullName() {
		return fBrokerFullName;
	}

	public void setfBrokerFullName(HtmlInputText fBrokerFullName) {
		this.fBrokerFullName = fBrokerFullName;
	}

	public HtmlInputTextarea getfContractComment() {
		return fContractComment;
	}

	public void setfContractComment(HtmlInputTextarea fContractComment) {
		this.fContractComment = fContractComment;
	}

	public HtmlInputText getfBrokerCel() {
		return fBrokerCel;
	}

	public void setfBrokerCel(HtmlInputText fBrokerCel) {
		this.fBrokerCel = fBrokerCel;
	}

	public HtmlOutputText getfPartnerId() {
		return fPartnerId;
	}

	public void setfPartnerId(HtmlOutputText fPartnerId) {
		this.fPartnerId = fPartnerId;
	}

	public HtmlSelectBooleanCheckbox getfApproved() {
		return fApproved;
	}

	public void setfApproved(HtmlSelectBooleanCheckbox fApproved) {
		this.fApproved = fApproved;
	}

	public boolean isDealApproved() {
		return dealApproved;
	}

	public void setDealApproved(boolean dealApproved) {
		this.dealApproved = dealApproved;
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getLoadedFileName() {
		return loadedFileName;
	}

	public void setLoadedFileName(String loadedFileName) {
		this.loadedFileName = loadedFileName;
	}

	public boolean isSkipToLast() {
		return skipToLast;
	}

	public void setSkipToLast(boolean skipToLast) {
		this.skipToLast = skipToLast;
	}

	public DealManager getDealManager() {
		return dealManager;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}

	public CityManager getCityManager() {
		return cityManager;
	}

	public void setCityManager(CityManager cityManager) {
		this.cityManager = cityManager;
	}

	public PartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(PartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}

	public List<String> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<String> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public HtmlInputText getfMainImgName() {
		return fMainImgName;
	}

	public void setfMainImgName(HtmlInputText fMainImgName) {
		this.fMainImgName = fMainImgName;
	}

	public String getMainImgName() {
		return mainImgName;
	}

	public void setMainImgName(String mainImgName) {
		this.mainImgName = mainImgName;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public int getDealDuration() {
		return dealDuration;
	}

	public void setDealDuration(int dealDuration) {
		this.dealDuration = dealDuration;
	}

	public EnvConfig getEnvConfig() {
		return envConfig;
	}
	public void setEnvConfig(EnvConfig envConfig) {
		this.envConfig = envConfig;
	}
	public String getUploadsUrl() {
		return uploadsUrl;
	}
	public void setUploadsUrl(String uploadsUrl) {
		this.uploadsUrl = uploadsUrl;
	}
	public Date getfContractDate() {
		return fContractDate;
	}
	public void setfContractDate(Date fContractDate) {
		this.fContractDate = fContractDate;
	}
	public boolean isCouponImmediately() {
		return couponImmediately;
	}
	public void setCouponImmediately(boolean couponImmediately) {
		this.couponImmediately = couponImmediately;
	}
	public DealChoice getDealChoiceSelected() {
		return dealChoiceSelected;
	}
	public void setDealChoiceSelected(DealChoice dealChoiceSelected) {
		this.dealChoiceSelected = dealChoiceSelected;
	}
	public HtmlInputTextarea getfDealChoiceTitle() {
		return fDealChoiceTitle;
	}
	public void setfDealChoiceTitle(HtmlInputTextarea fDealChoiceTitle) {
		this.fDealChoiceTitle = fDealChoiceTitle;
	}
	public boolean isEditModeDC() {
		return editModeDC;
	}
	public void setEditModeDC(boolean editModeDC) {
		this.editModeDC = editModeDC;
	}
	public HtmlInputTextarea getnDealChoiceTitle() {
		return nDealChoiceTitle;
	}
	public void setnDealChoiceTitle(HtmlInputTextarea nDealChoiceTitle) {
		this.nDealChoiceTitle = nDealChoiceTitle;
	}
	public HtmlInputText getnPrice() {
		return nPrice;
	}
	public void setnPrice(HtmlInputText nPrice) {
		this.nPrice = nPrice;
	}
	public HtmlInputText getnCommission() {
		return nCommission;
	}
	public void setnCommission(HtmlInputText nCommission) {
		this.nCommission = nCommission;
	}
	public HtmlInputText getnFullPrice() {
		return nFullPrice;
	}
	public void setnFullPrice(HtmlInputText nFullPrice) {
		this.nFullPrice = nFullPrice;
	}
	public HtmlInputText getnMinCustomers() {
		return nMinCustomers;
	}
	public void setnMinCustomers(HtmlInputText nMinCustomers) {
		this.nMinCustomers = nMinCustomers;
	}
	public HtmlInputText getnMaxCustomers() {
		return nMaxCustomers;
	}
	public void setnMaxCustomers(HtmlInputText nMaxCustomers) {
		this.nMaxCustomers = nMaxCustomers;
	}
	public Map<String, String> getAllCityMap() {
		return allCityMap;
	}
	public void setAllCityMap(Map<String, String> allCityMap) {
		this.allCityMap = allCityMap;
	}
	public List<String> getCitiesSelected() {
		return citiesSelected;
	}
	public void setCitiesSelected(List<String> citiesSelected) {
		this.citiesSelected = citiesSelected;
	}
	public HtmlInputText getfCommission() {
		return fCommission;
	}
	public void setfCommission(HtmlInputText fCommission) {
		this.fCommission = fCommission;
	}

	public Date getfFrom() {
		return fFrom;
	}

	public void setfFrom(Date fFrom) {
		this.fFrom = fFrom;
	}

	public Date getfTo() {
		return fTo;
	}

	public void setfTo(Date fTo) {
		this.fTo = fTo;
	}

	public String getfFromMinDate() {		
		return fFromMinDate;
	}
	
	public String getfFromMaxDate() {
		if(fTo!=null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			fFromMaxDate = dateFormat.format(fTo);
		}
		return fFromMaxDate;
	}	
	
	public String getfToMinDate() {		
		if (fFrom!=null && ZUtils.isFutureDate(fFrom)){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			fToMinDate = dateFormat.format(fFrom);
		}
		return fToMinDate;
	}

	public void setfFromMaxDate(String fFromMaxDate) {
		this.fFromMaxDate = fFromMaxDate;
	}

	public void setfToMinDate(String fToMinDate) {
		this.fToMinDate = fToMinDate;
	}

	public void setfFromMinDate(String fFromMinDate) {
		this.fFromMinDate = fFromMinDate;
	}

	public HtmlInputText getfOrder() {
		return fOrder;
	}

	public void setfOrder(HtmlInputText fOrder) {
		this.fOrder = fOrder;
	}
	
}
