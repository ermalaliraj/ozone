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
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CityManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Deal;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 * Controller for Publication Calendar.
 */
@ManagedBean(name="publicationCalController")
@ViewScoped
public class PublicationCalController implements Serializable{

	private static final long serialVersionUID = -9162026794551624256L;
	protected static final transient Log logger = LogFactory.getLog(PublicationCalController.class);
	
	private ScheduleModel eventModel;  
    private List<SelectItem> allCityItems;
    private String cityChoosed;
    private Deal dealSelected;
	private boolean editMode;
	
	//Binding Variables - New Publication form 
	private HtmlSelectOneMenu newCity;
	private HtmlSelectOneMenu newDeal;
	private Date newFrom;  
    private Date newTo;
	private HtmlInputText newOrder;
	
	private String nFromMaxDate;
	private String nToMinDate;
	private String nFromMinDate;
	private String fFromMaxDate;
	private String fToMinDate;
	
	//Binding Variables - Edit Publication form 
	private HtmlSelectOneMenu fCity;
	private HtmlInputText fOrder;
	
	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;	
	@ManagedProperty(value="#{cityManager}") 
	private CityManager cityManager;
	@ManagedProperty(value="#{applicationConfig}") 
	private ApplicationConfig applicationConfig;
	
	@PostConstruct
	public void init(){		
		allCityItems = new ArrayList<SelectItem>();
		List<City> allCities = cityManager.getAllActives();
		for (City c : allCities) {
			allCityItems.add(new SelectItem(c.getId(), c.getName()));
		}
	
		eventModel = new DefaultScheduleModel(); 
		cityChoosed = applicationConfig.getDefaultCity();//TR
		refreshDeals();	
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		nFromMinDate=dateFormat.format(ZUtils.addDaysToDate(new Date(), 1));
	}
	
	/**
	 * Listener called when a city has been changed from the city drop-down on calendar.
	 * The system must refresh the calendar with deals of selected city.
	 * @param e AjaxEvent
	 */
	public void onCityChanged(AjaxBehaviorEvent e){
		SelectOneMenu selMenu = (SelectOneMenu) e.getSource();
		cityChoosed = (String) selMenu.getValue();
		refreshDeals();
	}

	/**
	 * For the chosen city, get from DB all the deals and populate the eventModel of
	 * the Scheduler. The primefaces Scheduler Event is mapped with the Deal as follow: <br>
	 * Event.id = deal.id <br>
	 * Event.title = deal.title <br>
	 * Event.startDate = deal.startDate <br>
	 * Event.endDate = deal.endDate
	 */
	public void refreshDeals() {
		List<Deal> dealsList = dealManager.getAllDealsForCity(cityChoosed, false);		
		//logger.debug("Got "+dealsList.size()+" deals for city "+cityChoosed);
		//ZUtils.printListOnLogger(dealsList, logger, "info");		
		eventModel.clear();
		
		for (Deal d : dealsList) {			
			Date startDate = d.getStartDate();			
			Date endDate = d.getEndDate();	
			if(endDate!=null){
				String colorStyle = d.getColorForCalendar();
				//logger.debug("Event "+p.getId()+"\t"+p.getDeal().getTitle()+"\t"+startDate+"\t"+endDate);
				startDate = ZUtils.addDaysToDate(startDate, 1);
				endDate = ZUtils.addDaysToDate(endDate, 1);
				DefaultScheduleEvent event =  new DefaultScheduleEvent(d.getId()+" - "+d.getTitle(), startDate, endDate, colorStyle);
				event.setAllDay(true);				
				eventModel.addEvent(event);
				//event = new DefaultScheduleEvent();
			} else {
				//if endDate is null means that we have deals with unlimited timing.
				if(ZUtils.isEarlierOrTodayDate(startDate)){			
					DefaultScheduleEvent event = new DefaultScheduleEvent(d.getId()+" - "+d.getTitle(), new Date(), new Date(), "calDealActiveNoEndDate");
					event.setAllDay(true);
					eventModel.addEvent(event);
				} else{
					startDate = ZUtils.addDaysToDate(startDate, 1);
					DefaultScheduleEvent event = new DefaultScheduleEvent(d.getId()+" - "+d.getTitle(), startDate, startDate, "calDealActiveNoEndDate");
					event.setAllDay(true);
					eventModel.addEvent(event);	
				}
			}
		}
	}
	
	/**
	 * Clean the fields of New Publication form.
	 */
	public void cleanNewPublicationForm(){
		newCity = (HtmlSelectOneMenu) JSFUtils.setValueIfNotNull(newCity, "");
		newDeal = (HtmlSelectOneMenu) JSFUtils.setValueIfNotNull(newDeal, null);
		newFrom = null;
		newTo = null;
		newOrder.setValue(null);
	}
	
	/**
	 * Listener called when an event/deal has been clicked.
	 * @param selectEvent 
	 */
    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {  
    	ScheduleEvent event = selectEvent.getScheduleEvent();  
        int id = getIdFromEventTitle(event.getTitle());
        dealSelected = dealManager.get(id);
        editMode = true;
    }

    /**
     * ScheduleEvent Title contains "pubId - pubTitle"
     * From the format "1234 - title", return the string before the "-", so returns "1234".
     * @param stringToSplit String from which to take the id
     * @return id read from ScheduleEvent.Title
     */
	private int getIdFromEventTitle(String stringToSplit) {
		String evTitle = stringToSplit;
        String[] splitedString = evTitle.split("-");
        int id = Integer.parseInt(splitedString[0].trim());
		return id;
	}  
    
	/**
	 * Listener called when a cell is clicked in the Scheduler.
	 * @param selectEvent 
	 */
    public void onDateSelect(DateSelectEvent selectEvent) {
    	Date selectedDate = selectEvent.getDate();
    	if(ZUtils.isFutureDate(selectedDate)){
    		newFrom = selectedDate;
    		newTo = ZUtils.addDaysToDate(selectedDate, applicationConfig.getPublicationDuration()-1);
    		dealSelected = new Deal();
            editMode = false;
    	}           
    }  
	
    /**
     * Add the Event/Deal the Scheduler.
     * Can be added only Deals which doesn't have a start/end date
     * @param actionEvent
     */
    public void addEvent(ActionEvent actionEvent) {  
    	Deal deal = (Deal) newDeal.getValue();
    	
    	//deal.addCityForDeal(new City(cityChoosed));
    	//deal.setCity(new City(cityChoosed));
    	deal.setStatus(Deal.STATUS_WAITING);
    	deal.setStartDate(newFrom);
    	deal.setEndDate(newTo);
    	deal.setOrder(JSFUtils.getIntegerFromUIInput(newOrder));
    	deal.setLastUpdate(new Date());
    	deal.setLastUpdateUser(ZUtils.getLoggedUsername());
		
		//logger.debug("Going to publish the deal in cal in period ["+newFrom+", "+newTo+"]");
		try {
			if(ZUtils.isFutureDate(newFrom)){
				dealManager.updateDealInCalendar(deal);
				logger.info("Deal "+deal.getId()+" updated(updateDealInCalendar) from controller.");
				JSFUtils.addInfoMessage("Deal nr."+deal.getId()+" added successfully.");
				
				DefaultScheduleEvent event = new DefaultScheduleEvent(deal.getId()+" - "+deal.getTitle(), newFrom, newTo, "notApprovedColor");
				event.setAllDay(true);
				eventModel.addEvent(event);
		        cleanNewPublicationForm();
		        refreshDeals();
			}else{
				logger.warn("Deal: "+deal.getId()+" must start in a future date");
				JSFUtils.addWarnMessage("Deal must start in a future date");
			}
		} catch (Exception e) {
			logger.error("Deal "+deal.getId()+" cannot be updated (updateDealInCalendar) from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    } 
    
    public void removeEvent() {  
    	dealSelected.setStatus(Deal.STATUS_WAITING);
    	dealSelected.setStartDate(null);
    	dealSelected.setEndDate(null);
    	dealSelected.setOrder(0);
    	dealSelected.setLastUpdate(new Date());
    	dealSelected.setLastUpdateUser(ZUtils.getLoggedUsername());

    	//logger.debug("Going to remove the deal "+dealSelected.getId()+" from calendar");
		try {
			dealManager.updateDealInCalendar(dealSelected);
			logger.info("Deal "+dealSelected.getId()+" updated(updateDealInCalendar) from controller.");
			JSFUtils.addInfoMessage("Deal nr."+dealSelected.getId()+" removed successfully from calendar.");
			cleanNewPublicationForm();
			refreshDeals();
		} catch (Exception e) {
			logger.error("Deal "+dealSelected.getId()+" cannot be removed from calendar (updateDealInCalendar) from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
    }


	/**
	 * Read the input fields from the form and update the deal in DB.
	 */
	public void updateDeal() {  
		//all other fields are been copied automaticly , on eventSelect
		dealSelected.setOrder(JSFUtils.getIntegerFromUIInput(fOrder));
		
		updateDealInDB(dealSelected);  
		refreshDeals();
    }
	
	public void changePublicationOrder() {  
		int dealId = dealSelected.getId();
		int newOrder = JSFUtils.getIntegerFromUIInput(fOrder);
		
		//logger.debug("going to change order to:"+newOrder+" for Deal: "+dealId);
		try {
			dealManager.changeOrder(dealId, newOrder);
			logger.info("Deal: "+dealId+" will show up in order "+newOrder);
			JSFUtils.addInfoMessage("Deal: "+dealId+" will show up in order "+newOrder);
		} catch (Exception e) {
			logger.error("Can not change order for deal: "+dealId, e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
		refreshDeals();
    }
    
    /**
     * Listener called when an event/deal has been moved from a date to another.
     * @param event
     */
    public void onEventMove(ScheduleEntryMoveEvent event) {  
    	int dealId = getIdFromEventTitle(event.getScheduleEvent().getTitle());
        Deal d = dealManager.get(dealId);
        //logger.debug("Get from DB, for later moving, Deal: "+d);
        
        Date actualStartDate = d.getStartDate();
        Date actualEndDate = d.getEndDate();
        Date targetStartDate = ZUtils.addDaysToDate(actualStartDate, event.getDayDelta());
        Date targetEndDate = ZUtils.addDaysToDate(actualEndDate, event.getDayDelta());

        if(ZUtils.isFutureDate(targetStartDate)){
        	//move only if is in status (W)aiting
            if(d.isMovable()){              	
    	        d.setStartDate(targetStartDate);
    	        d.setEndDate(targetEndDate);
    	        updateDealInDB(d);
    	        logger.info("Moved deal from date ["+actualStartDate+", "+actualEndDate+"] to ["+targetStartDate+", "+targetEndDate+"]");
    	        //JSFUtils.addInfoMessage("Deal moved to "+event.getDayDelta()+ " days");
            }else{
            	logger.warn("Deal "+d.getId()+" can not be moved because is Closed, Active or Unlimited.");
            	JSFUtils.addWarnMessage("Deal "+d.getId()+" can not be moved because is Closed, Active or Unlimited.");
            }
        }else{
        	logger.warn("Deal "+d.getId()+" must start in a future date.");
        	JSFUtils.addWarnMessage("Deal must start in a future date");
        }
        refreshDeals();
    }
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
    	ScheduleEvent ev = event.getScheduleEvent();
    	int dealId = getIdFromEventTitle(ev.getTitle());
        Deal d = dealManager.get(dealId);
        //logger.debug("Get from DB, for later resizing end date, Deal: "+d);
        
        if(d.isMovable()){
        	Date actualEndDate = d.getEndDate();
            Date targetEndDate = ZUtils.addDaysToDate(actualEndDate, event.getDayDelta());
            d.setEndDate(targetEndDate);
            updateDealInDB(d);
            logger.info("Changed end date for deal "+d.getId()+" from "+actualEndDate+" to "+targetEndDate);
            //JSFUtils.addInfoMessage("Added "+event.getDayDelta()+" days to deal.");
        }else{
        	logger.warn("Deal "+d.getId()+" can not be resized because is Closed, Active or Unlimited.");
        	JSFUtils.addWarnMessage("Deal "+d.getId()+" can not be resized because is Closed, Active or Unlimited.");
        }
        refreshDeals();
    }  
    
	/**
	 * Persist the Deal passed as parameter in DB.
	 * @param d Deal to be updated in DB
	 */
	private void updateDealInDB(Deal d) {
		//logger.debug("going to update Deal: "+d);
		try {
			if(ZUtils.isFutureDate(d.getStartDate())){
				dealManager.updateDealInCalendar(d);
				//logger.info("Deal: "+d.getId()+" updated from controller.");
				JSFUtils.addInfoMessage("Deal updated successfully.");
			}else{
				logger.warn("Deal: "+d.getId()+" can not be updated. StartDate is not in the future.");
				JSFUtils.addWarnMessage("Deal must start in a future date");
			}
		} catch (Exception e) {
			logger.error("Deal: "+d.getId()+" can not be updated", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
	}
	
	/**
	 * Put dealSelected.id in session.
	 * The full deal will be retrieved from db in DealController.init()
	 * @return String used for navigation rule.
	 */
	public String goToDeal(){		
		JSFUtils.putObjectInSession("dealInSession", dealSelected.getId());
		return "success";
	}
	
	/**
	 * Get only deals with no start and end date
	 * @return list of approved deals
	 */
    public List<Deal> getDealsList() {
		return dealManager.getDealsNotPublished();
	}

	public String getCityNameChoosed() {
		City c = cityManager.get(cityChoosed);
		return c.getName();
	}

	public void setCityChoosed(String cityChoosed) {
		this.cityChoosed = cityChoosed;
	}
	public ScheduleModel getEventModel() {
		return eventModel;
	}
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	public List<SelectItem> getAllCityItems() {
		return allCityItems;
	}
	public void setAllCityItems(List<SelectItem> allCityItems) {
		this.allCityItems = allCityItems;
	}
	public Deal getDealSelected() {
		return dealSelected;
	}
	public void setDealSelected(Deal dealSelected) {
		this.dealSelected = dealSelected;
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
	public HtmlSelectOneMenu getNewCity() {
		return newCity;
	}
	public void setNewCity(HtmlSelectOneMenu newCity) {
		this.newCity = newCity;
	}
	public HtmlSelectOneMenu getNewDeal() {
		return newDeal;
	}
	public void setNewDeal(HtmlSelectOneMenu newDeal) {
		this.newDeal = newDeal;
	}
	public Date getNewFrom() {
		return newFrom;
	}
	public void setNewFrom(Date newFrom) {
		this.newFrom = newFrom;
	}
	public Date getNewTo() {
		return newTo;
	}
	public void setNewTo(Date newTo) {
		this.newTo = newTo;
	}
	public HtmlInputText getNewOrder() {
		return newOrder;
	}
	public void setNewOrder(HtmlInputText newOrder) {
		this.newOrder = newOrder;
	}
	public DealManager getDealManager() {
		return dealManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public HtmlSelectOneMenu getfCity() {
		return fCity;
	}
	public void setfCity(HtmlSelectOneMenu fCity) {
		this.fCity = fCity;
	}
	public HtmlInputText getfOrder() {
		return fOrder;
	}
	public void setfOrder(HtmlInputText fOrder) {
		this.fOrder = fOrder;
	}
	public String getCityChoosed() {
		return cityChoosed;
	}
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public String getnFromMaxDate() {
		if(newTo != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			nFromMaxDate = dateFormat.format(newTo);
		}
		return nFromMaxDate;
	}

	public void setnFromMaxDate(String nFromMaxDate) {
		this.nFromMaxDate = nFromMaxDate;
	}

	public String getnToMinDate() {
		if(newFrom != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			nToMinDate = dateFormat.format(newFrom);
		}
		return nToMinDate;
	}

	public void setnToMinDate(String nToMinDate) {
		this.nToMinDate = nToMinDate;
	}

	public String getnFromMinDate() {
		return nFromMinDate;
	}

	public void setnFromMinDate(String nFromMinDate) {
		this.nFromMinDate = nFromMinDate;
	}

	public String getfFromMaxDate() {
		if(dealSelected != null){
			Date endDate = dealSelected.getEndDate();
			if (endDate!=null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				fFromMaxDate = dateFormat.format(endDate);
			}
		}
		return fFromMaxDate;
	}

	public void setfFromMaxDate(String fFromMaxDate) {
		this.fFromMaxDate = fFromMaxDate;
	}

	public String getfToMinDate() {
		if(dealSelected != null){
			Date startDate = dealSelected.getStartDate();
			if (startDate!=null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				fToMinDate = dateFormat.format(startDate);
			}
		}
		return fToMinDate;
	}

	public void setfToMinDate(String fToMinDate) {
		this.fToMinDate = fToMinDate;
	}
}
