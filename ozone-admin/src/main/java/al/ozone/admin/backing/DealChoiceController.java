package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchDealChoiceBean;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.DealChoice;

/**
 * @author Ermal Aliraj
 * 
 */
@ManagedBean(name="dealChoiceController")
@ViewScoped
public class DealChoiceController implements Serializable{

	private static final long serialVersionUID = -4021937767937839734L;
	protected static final transient Log logger = LogFactory.getLog(DealChoiceController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sDealNr;
	private HtmlInputText sDealTitle;	
	private HtmlInputText sChoiceNr;	
	private HtmlInputText sChoiceTitle;	
	private HtmlInputText sPartner;

	private List<DealChoice> dealChoiceList;
	private DealChoice dealChoiceSelected;	
	
	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	
	@PostConstruct
	public void init(){		
		//check if dealInSession is present
		Integer dealId = (Integer) JSFUtils.getObjectFromSession("dealInSession");
		//logger.debug("dealInSession:"+dealId);
		if(dealId!=null){
			//Show only choices of dealId
			SearchDealChoiceBean searchChoiceBean = new SearchDealChoiceBean();
			searchChoiceBean.setDealId(dealId);
			List<DealChoice> list = dealManager.searchDealChoice(searchChoiceBean);
			//logger.debug("Got from DB "+list.size()+" choiceDeals");
			//ZUtils.printListOnLogger(list, logger, "debug");
			setDealChoiceList(list);
			JSFUtils.removeObjectFromSession("dealInSession");
		}else{
			searchDealChoice();
		}
	}
	
	public void searchDealChoice(){		
		SearchDealChoiceBean searchChoiceBean = new SearchDealChoiceBean();
		searchChoiceBean.setPartnerName(JSFUtils.getStringFromUIInput(sPartner));
		searchChoiceBean.setDealId(JSFUtils.getIntegerFromUIInput(sDealNr));
		searchChoiceBean.setDealTitle(JSFUtils.getStringFromUIInput(sDealTitle));
		searchChoiceBean.setChoiceNr(JSFUtils.getIntegerFromUIInput(sChoiceNr));
		searchChoiceBean.setChoiceTitle(JSFUtils.getStringFromUIInput(sChoiceTitle));
		List<DealChoice> list = dealManager.searchDealChoice(searchChoiceBean);
		//logger.debug("Got from DB "+list.size()+" choiceDeals");
		//ZUtils.printListOnLogger(list, logger, "debug");
		setDealChoiceList(list);
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sPartner.setValue(null);
		sDealNr.setValue(null);
		sDealTitle.setValue(null);
		sChoiceNr.setValue(null);
		sChoiceTitle.setValue(null);
	}

	/**
	 * Put dealSelected.id in session.
	 * The full deal will be retrieved from db in DealController.init()
	 * @return String used for navigation rule.
	 */
	public String goToDeal(){
		JSFUtils.putObjectInSession("dealInSession", dealChoiceSelected.getDealId());
		return "success";
	}
	
	public HtmlInputText getsDealNr() {
		return sDealNr;
	}

	public void setsDealNr(HtmlInputText sDealNr) {
		this.sDealNr = sDealNr;
	}

	public HtmlInputText getsDealTitle() {
		return sDealTitle;
	}

	public void setsDealTitle(HtmlInputText sDealTitle) {
		this.sDealTitle = sDealTitle;
	}

	public HtmlInputText getsChoiceNr() {
		return sChoiceNr;
	}

	public void setsChoiceNr(HtmlInputText sChoiceNr) {
		this.sChoiceNr = sChoiceNr;
	}

	public HtmlInputText getsChoiceTitle() {
		return sChoiceTitle;
	}

	public void setsChoiceTitle(HtmlInputText sChoiceTitle) {
		this.sChoiceTitle = sChoiceTitle;
	}

	public HtmlInputText getsPartner() {
		return sPartner;
	}

	public void setsPartner(HtmlInputText sPartner) {
		this.sPartner = sPartner;
	}

	public List<DealChoice> getDealChoiceList() {
		return dealChoiceList;
	}

	public void setDealChoiceList(List<DealChoice> dealChoiceList) {
		this.dealChoiceList = dealChoiceList;
	}

	public DealChoice getDealChoiceSelected() {
		return dealChoiceSelected;
	}

	public void setDealChoiceSelected(DealChoice dealChoiceSelected) {
		this.dealChoiceSelected = dealChoiceSelected;
	}

	public DealManager getDealManager() {
		return dealManager;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	
	
}
