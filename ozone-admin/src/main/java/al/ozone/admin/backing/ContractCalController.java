package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.chart.PieChartModel;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Deal;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 * Controller for Publication Calendar.
 */
@ManagedBean(name="contractCalController")
@ViewScoped
public class ContractCalController implements Serializable{

	private static final long serialVersionUID = -9162026794551624256L;
	protected static final transient Log logger = LogFactory.getLog(ContractCalController.class);
	
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate; 
    
	private ScheduleModel eventModel;  
    private ScheduleEvent event = new DefaultScheduleEvent(); 
    private List<ContratPublicationBean> contractPubsList;
    //private List<ContratPublicationRow> contractPubsList;
    
    private int allContrats;
    private int allStillWaitingContrats;
    
    private PieChartModel pieModel;  
    private Integer dealId;
	
	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;	
	
	@PostConstruct
	public void init(){		
		eventModel = new DefaultScheduleModel(); 
		search();
	}
	
	public void search(){	
		SearchDealBean sb = new SearchDealBean();
		sb.setStartedFrom(sFrom);
		sb.setStartedTo(sTo);
		//contractPubsList = dealManager.getContratsByDate(searchBean);		
		contractPubsList = dealManager.getContratsGroupByMonths(sb);
		
		refreshDeals();	
		createPieModel();
	}
	
	public void cleanSearchForm(){
		sFrom = null;
		sTo = null;
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
		SearchDealBean sb = new SearchDealBean();
		sb.setStartedFrom(sFrom);
		sb.setStartedTo(sTo);
		
		contractPubsList = dealManager.getContratsGroupByMonths(sb);
		allContrats = 0;
		allStillWaitingContrats = 0;
		for (ContratPublicationBean c : contractPubsList) {
			allContrats += c.getTotContrats();
			allStillWaitingContrats += c.getStillWaiting();
		}
		
		List<Deal> dealsList = dealManager.getAll();
		//logger.debug("Got "+dealsList.size()+" contrats");
		
		eventModel.clear();
		for (Deal d : dealsList) {
			String colorStyle = d.getColorForCalendar();
			Date startDate = d.getContractDate();
			Date endDate = d.getContractDate();
			//Primefaces3 use endDate as not included limit, so we slit to 1 day.
			startDate = ZUtils.addDaysToDate(startDate, -1);
			endDate = ZUtils.addDaysToDate(endDate, 0);
			//logger.debug("["+startDate+", "+endDate+"]");
			DefaultScheduleEvent defEvent = new DefaultScheduleEvent(d.getId()+" - "+d.getPartner().getName(), ZUtils.addDaysToDate(startDate,1), endDate, colorStyle);
			defEvent.setAllDay(true);
			event = defEvent;
			eventModel.addEvent(event);
		}
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {  
	   refreshDeals();
	} 
	
    private void createPieModel() {  
		SearchDealBean sb = new SearchDealBean();
		sb.setStartedFrom(sFrom);
		sb.setStartedTo(sTo);
		
        pieModel = new PieChartModel();  
  
        List<ResultStatisticBean> list = dealManager.getDealsGroupByCategory(sb);
    	for (ResultStatisticBean res : list) {
    		pieModel.set(res.getKey(), res.getValue()); 
		}
    }  
    
	/**
	 * Listener called when an event has been clicked.
	 * @param selectEvent 
	 */
    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {  
        event = selectEvent.getScheduleEvent();  
        String[] splitedString = event.getTitle().split("-");
        dealId = Integer.parseInt(splitedString[0].trim());
    }
    
	/**
	 * Put dealId in session.
	 * The full deal will be retrieved from db in DealWizard.init()
	 * @return String used for navigation rule.
	 */
	public String goToDealWiz(){
		JSFUtils.putObjectInSession("dealInSession", dealId);
		return "success";
	}
	
    public void itemPieSelect(ItemSelectEvent event) {  
       //System.out.println(event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());  

    }  
	
	/**
	 * Get only deals with no start and end date
	 * @return list of approved deals
	 */
    public List<Deal> getDealsList() {
		return dealManager.getDealsNotPublished();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	public ScheduleEvent getEvent() {
		return event;
	}
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	public DealManager getDealManager() {
		return dealManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public List<ContratPublicationBean> getContractPubsList() {
		return contractPubsList;
	}
	public void setContractPubsList(List<ContratPublicationBean> contractPubsList) {
		this.contractPubsList = contractPubsList;
	}
	public int getAllContrats() {
		return allContrats;
	}
	public void setAllContrats(int allContrats) {
		this.allContrats = allContrats;
	}
	public int getAllStillWaitingContrats() {
		return allStillWaitingContrats;
	}
	public void setAllStillWaitingContrats(int allStillWaitingContrats) {
		this.allStillWaitingContrats = allStillWaitingContrats;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
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
		return sToMinDate;
	}

	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}

	public String getsFromMaxDate() {
		return sFromMaxDate;
	}

	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	
}
