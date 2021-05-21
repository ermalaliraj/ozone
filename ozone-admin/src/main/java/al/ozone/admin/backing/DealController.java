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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.dao.DataIntegrityViolationException;

import al.ozone.admin.converter.StatusPubFormatter;
import al.ozone.admin.converter.YesNoFormatter;
import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchDealBean;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.Deal;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 */
@ManagedBean(name="dealController")
@ViewScoped
public class DealController implements Serializable{

	private static final long serialVersionUID = 5866673902858855703L;
	protected static final transient Log logger = LogFactory.getLog(DealController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sDealNr;
	private HtmlInputText sDealTitle;	
	private HtmlSelectOneMenu sApprovePublish;
	private HtmlSelectOneMenu sStatus;
	private HtmlInputText sPartner;
	private HtmlSelectOneMenu sCategoryId;
	private List<SelectItem> categoryItems;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;  
	private Date sFromStartDate;  
    private Date sToStartDate;
    private String sToMinStartDate;
    private String sFromMaxStartDate;

	private List<Deal> dealsList;
	private Deal dealSelected;	
	
	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	@ManagedProperty(value="#{partnerManager}") 
	private PartnerManager partnerManager;	
	
	@PostConstruct
	public void init(){		
		categoryItems = new ArrayList<SelectItem>();
		updateCategoryItems();
		
		//check if dealInSession present
		Integer dealId = (Integer) JSFUtils.getObjectFromSession("dealInSession");
		//logger.debug("dealInSession: "+dealId);
		if(dealId!=null){
			//Show only choices of dealId
			SearchDealBean sb = new SearchDealBean();
			sb.setDealId(dealId);
			List<Deal> list = dealManager.search(sb);
			//logger.debug("Got from DB "+list.size()+" choiceDeals");
			setDealsList(list);
			JSFUtils.removeObjectFromSession("dealInSession");
		}else{
			searchDeal();
		}
	}
	
	public void searchDeal(){		
		SearchDealBean searchBean = new SearchDealBean();
		searchBean.setPartnerName(JSFUtils.getStringFromUIInput(sPartner));
		searchBean.setDealId(JSFUtils.getIntegerFromUIInput(sDealNr));
		searchBean.setDealTitle(JSFUtils.getStringFromUIInput(sDealTitle));
		searchBean.setIsApprovedForPub(JSFUtils.getBooleanFromUIInput(sApprovePublish));
		searchBean.setStatus(JSFUtils.getStringFromUIInput(sStatus));
		searchBean.setInsertedFrom(sFrom);
		searchBean.setInsertedTo(sTo);
		searchBean.setStartedFrom(sFromStartDate);
		searchBean.setStartedTo(sToStartDate);
		Integer categoryId = JSFUtils.getIntegerFromUIInput(sCategoryId);
		searchBean.setCategoryId(categoryId);
		//logger.debug(searchBean);
		List<Deal> list = dealManager.search(searchBean);
		//logger.debug("Got from DB "+list.size()+" deals");
		//ZUtils.printListOnLogger(list, logger, "debug");
		setDealsList(list);
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sPartner.setValue(null);
		sDealNr.setValue(null);
		sDealTitle.setValue(null);
		sApprovePublish.setValue("");
		sStatus.setValue("");
		sFrom = null;
		sTo = null;
		sToMinDate=null;
		sFromMaxDate=null;
		sFromStartDate=null;  
	    sToStartDate=null;
	    sToMinStartDate=null;
	    sFromMaxStartDate=null;  
	    sCategoryId.setValue(null);
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
	
	public void deleteDeal() {   	
		try {
			dealManager.delete(dealSelected);
			logger.debug("Deal "+dealSelected.getId()+" deleted from controller.");
			JSFUtils.addInfoMessage("Deal deleted successfully.");
			searchDeal();
		}catch(DataIntegrityViolationException e1){
			logger.error("Deal: "+dealSelected.getId()+" can not be deleted because...(tell Ermal to investigate why)");
			JSFUtils.addWarnMessage("Deal can not be deleted because has ... (tell Ermal to investigate why).");
		}catch (Exception e) {
			logger.debug("Deal "+dealSelected.getId()+" can not be deleted from controller.");
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}  		
    }
	
	/**
	 * Put dealSelected.id in session.
	 * The full deal will be retrieved from db in DealWizard.init()
	 * @return String used for navigation rule.
	 */
	public String editDealWiz(){
		JSFUtils.putObjectInSession("dealInSession", dealSelected.getId());
		return "success";
	}
	
	/**
	 * Clean the session, ready to create a new Deal with no partnerSelected
	 * Remove dealInSession and partnerIsSession from the session.
	 * 
	 * @return String used for navigation rule.
	 */
	public String newDealWiz(){
		JSFUtils.removeObjectFromSession("dealInSession");
		JSFUtils.removeObjectFromSession("partnerInSession");
		return "success";
	}
	
	/**
	 * Put dealSelected.id in session.
	 * The full deal will be retrieved from db in DealChoiceController.init()
	 * @return String used for navigation rule.
	 */
	public String goToDealChoice(){
		JSFUtils.putObjectInSession("dealInSession", dealSelected.getId());
		return "success";
	}
	
	/**
	 * Create a new deal as a copy of dealSelected
	 */
	public void copyDeal(){
		try {
			logger.debug("Creating a copy for deal "+dealSelected.getId());
			Deal d = dealManager.get(dealSelected.getId());
			d.setApprovedForPublish(false);
			d.setApprovedDate(null);
			d.setContractDate(null);
			d.setApprovedUser(null);
			d.setLastUpdateUser(ZUtils.getLoggedUsername());
			d.setLastUpdate(null);
	
			dealManager.insert(d);
//			dealManager.updateDataDeal(dealSelected);
//			dealManager.updateSynthConditions(dealSelected);
//			dealManager.updateDescription(dealSelected);
//			dealManager.updateImageAndApproval(dealSelected);
			
			logger.debug("Deal "+d.getId()+" inserted from controller.");
			JSFUtils.addInfoMessage("New Deal "+d.getId()+" created successfully");
			searchDeal();
		} catch (Exception e) {
			logger.debug("Deal "+dealSelected.getId()+" can not be copied from controller.");
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}  		
	}

	public void onFromFieldClean(){
		sFrom = null;
		sTo = null;
		sToMinDate=null;
		sFromMaxDate=null;
		sFromStartDate=null;  
	    sToStartDate=null;
	    sToMinStartDate=null;
	    sFromMaxStartDate=null;  
	}
	
//	public void postProcessXLS(Object document) {  
//	    HSSFWorkbook wb = (HSSFWorkbook) document;  
//	    HSSFSheet sheet = wb.getSheetAt(0);  
//
//	    HSSFRow header = sheet.getRow(0);  
//	    header.setHeightInPoints(28);
//	    
//	    sheet.setColumnWidth(1, 1500);
//	    sheet.setColumnWidth(2, 20000);
//	    sheet.setColumnWidth(3, 7800);
//	    sheet.setColumnWidth(4, 4000);
//	    sheet.setColumnWidth(5, 2600);
//	    sheet.setColumnWidth(6, 2600);
//	    
//	    HSSFCellStyle cellStyle = wb.createCellStyle();    
//	    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
//	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
//	    
//	    for(int i=0; i < header.getPhysicalNumberOfCells();i++) {  
//	        HSSFCell cell = header.getCell(i);  
//	        cellStyle.setWrapText(true);
//	        cell.setCellStyle(cellStyle);
//	        
//	        // normalize column name
//	        cell = changeColumnHeaderName(i, cell);
//	    }  
//	}
	
	public void postProcessXLS(Object document) {  
	    HSSFWorkbook wb = (HSSFWorkbook) document;  
	    CreationHelper createHelper = wb.getCreationHelper();
	    
	    //remove actual sheet, and create a new one
	    wb.removeSheetAt(0);
	    HSSFSheet mysheet = wb.createSheet();
	    
	    //create the header
	    mysheet.setColumnWidth(0, 1500);
	    mysheet.setColumnWidth(1, 15000);
	    mysheet.setColumnWidth(2, 7500);
	    mysheet.setColumnWidth(3, 4000);
	    mysheet.setColumnWidth(4, 2600);
	    mysheet.setColumnWidth(5, 2600);
	    mysheet.setColumnWidth(6, 2600);
	    mysheet.setColumnWidth(7, 2100);
	    mysheet.setColumnWidth(8, 2000);
	    mysheet.setColumnWidth(9, 1800);
	    mysheet.setColumnWidth(10, 40000);
	    HSSFCellStyle cellStyle = wb.createCellStyle();    
	    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
	    HSSFRow header = mysheet.createRow(0);
	    header.setHeightInPoints(28);
	    for(int i=0; i <= 10; i++) {  
	        HSSFCell cell = header.createCell(i);  
	        cellStyle.setWrapText(true);
	        cell.setCellStyle(cellStyle);
	        cell = changeColumnHeaderName(i, cell);
	    }  
	    
	    //create rows
	    int i=1;
	    for (Deal d : dealsList) {
	    	HSSFRow row = mysheet.createRow(i);
	    	
	    	HSSFCell cell = row.createCell(0);
	    	cell.setCellValue(d.getId());
	    	
	    	cell = row.createCell(1);
	    	cell.setCellValue(d.getTitle());
	    	
	    	cell = row.createCell(2);
	    	cell.setCellValue(d.getPartner().getName());
	    	
	    	cell = row.createCell(3);
	    	cell.setCellValue(d.getBrokerFullName());
	    	
	    	cell = row.createCell(4);
	    	if(d.getContractDate()!=null){
		    	cell.setCellValue(d.getContractDate());
		    	cellStyle = wb.createCellStyle();
		    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		    	cell.setCellStyle(cellStyle);
	    	}
	    	
	    	cell = row.createCell(5);
	    	if(d.getStartDate()!=null){
		    	cell.setCellValue(d.getStartDate());
		    	cellStyle = wb.createCellStyle();
		    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		    	cell.setCellStyle(cellStyle);
	    	}
	    	
	    	cell = row.createCell(6);
	    	if(d.getEndDate()!=null){
		    	cell.setCellValue(d.getEndDate());
		    	cellStyle = wb.createCellStyle();
		    	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		    	cell.setCellStyle(cellStyle);
	    	}
	    	
	    	cell = row.createCell(7);
	    	cell.setCellValue(YesNoFormatter.convertToString(d.isApprovedForPublish()));
	    	
	    	cell = row.createCell(8);
	    	cell.setCellValue(StatusPubFormatter.convertToString(d.getStatus()));
	    	
	    	cell = row.createCell(9);
	    	cell.setCellValue(YesNoFormatter.convertToString(d.isCouponImmediately()));
	    	
	    	cell = row.createCell(10);
	    	cell.setCellValue(d.getContractComment());
	    	
	    	i++;
		}	    
	}
	 

	private HSSFCell changeColumnHeaderName(int i, HSSFCell cell) {
		switch (i) {
		case 0:
			cell.setCellValue("ID");
			break;
		case 1:
			cell.setCellValue("Titulli ofertes");
			break;
		case 2:
			cell.setCellValue("Partneri");
			break;
		case 3:
			cell.setCellValue("Agjentet");
			break;
		case 4:
			cell.setCellValue("Data kontrates");
			break;
		case 5:
			cell.setCellValue("Pub. From");
			break;		
		case 6:
			cell.setCellValue("Pub. To");
			break;	
		case 7:
			cell.setCellValue("Aprovuar publikimi");
			break;	
		case 8:
			cell.setCellValue("Statusi");
			break;	
		case 9:
			cell.setCellValue("Kupon imediat");
			break;	
		case 10:
			cell.setCellValue("Koment mbi kontraten");
			break;	
		default:
			break;
		}
		
		return cell;
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
	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}
	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}
	public String getsToMinStartDate() {
		if(sFromStartDate != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sToMinStartDate = dateFormat.format(sFromStartDate);
		}
		return sToMinStartDate;
	}
	public String getsFromMaxStartDate() {
		if(sToStartDate != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sFromMaxStartDate = dateFormat.format(sToStartDate);
		}
		return sFromMaxStartDate;
	}
	public Date getsFromStartDate() {
		return sFromStartDate;
	}

	public void setsFromStartDate(Date sFromStartDate) {
		this.sFromStartDate = sFromStartDate;
	}

	public Date getsToStartDate() {
		return sToStartDate;
	}

	public void setsToStartDate(Date sToStartDate) {
		this.sToStartDate = sToStartDate;
	}

	public void setsToMinStartDate(String sToMinStartDate) {
		this.sToMinStartDate = sToMinStartDate;
	}

	public void setsFromMaxStartDate(String sFromMaxStartDate) {
		this.sFromMaxStartDate = sFromMaxStartDate;
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
	public HtmlSelectOneMenu getsStatus() {
		return sStatus;
	}
	public void setsStatus(HtmlSelectOneMenu sStatus) {
		this.sStatus = sStatus;
	}
	public HtmlSelectOneMenu getsApprovePublish() {
		return sApprovePublish;
	}
	public void setsApprovePublish(HtmlSelectOneMenu sApprovePublish) {
		this.sApprovePublish = sApprovePublish;
	}
	public HtmlInputText getsPartner() {
		return sPartner;
	}
	public void setsPartner(HtmlInputText sPartner) {
		this.sPartner = sPartner;
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
	public List<Deal> getDealsList() {
		return dealsList;
	}
	public void setDealsList(List<Deal> dealsList) {
		this.dealsList = dealsList;
	}
	public Deal getDealSelected() {
		return dealSelected;
	}
	public void setDealSelected(Deal dealSelected) {
		this.dealSelected = dealSelected;
	}
	public DealManager getDealManager() {
		return dealManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
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

	public void setPartnerManager(PartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}	
	
}
