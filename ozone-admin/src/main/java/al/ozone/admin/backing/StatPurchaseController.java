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

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchDealPurchasesBean;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.DealPurchasesReport;

/**
 * @author Ermal Aliraj
 * 
 */
@ManagedBean(name="statPurchaseController")
@ViewScoped
public class StatPurchaseController implements Serializable{

	private static final long serialVersionUID = 5866673902858855703L;
	protected static final transient Log logger = LogFactory.getLog(StatPurchaseController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sDealNr;
	private HtmlInputText sDealTitle;	
	private HtmlInputText sAgentName;
	private HtmlInputText sAgentCommission;
	private int agentCommission;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;  
	private Date sFromStartDate;  
    private Date sToStartDate;
    private String sToMinStartDate;
    private String sFromMaxStartDate;
    private Date sFromPurchaseDate;
    private Date sToPurchaseDate;
    private String sToPurchaseDateMin;
    private String sFromPurchaseDateMax;
    private List<SelectItem> categoryItems;
    private HtmlSelectOneMenu sCategoryId;
    
	private List<DealPurchasesReport> dealPurchasesList;
	private DealPurchasesReport dealPurchases;	
	
	//footer row
	private int sumNrPurchaseTotal;
	private int sumTotAmount;
	private int sumTotEarning;
	private int sumAgentEarning;

	// Injected properties
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	@ManagedProperty(value="#{partnerManager}") 
	private PartnerManager partnerManager;
	
	@PostConstruct
	public void init(){	
		categoryItems = new ArrayList<SelectItem>();
		List<Category> cat = partnerManager.getCategories();
		for(Category c: cat){
			categoryItems.add(new SelectItem(c.getId(),c.getNameAl()));
		}
		searchDeal();
	}
	
	public void searchDeal(){		
		SearchDealPurchasesBean sb = new SearchDealPurchasesBean();
		sb.setDealId(JSFUtils.getIntegerFromUIInput(sDealNr));
		sb.setDealTitle(JSFUtils.getStringFromUIInput(sDealTitle));
		//partner
		sb.setAgentName(JSFUtils.getStringFromUIInput(sAgentName));
		sb.setAgentCommission(JSFUtils.getIntegerFromUIInput(sAgentCommission));
		
		sb.setInsertedFrom(sFrom);
		sb.setInsertedTo(sTo);
		sb.setStartFrom(sFromStartDate);
		sb.setStartTo(sToStartDate);
		sb.setPurchaseFrom(sFromPurchaseDate);
		sb.setPurchaseTo(sToPurchaseDate);
		Integer categoryId = JSFUtils.getIntegerFromUIInput(sCategoryId);
		sb.setCategoryId(categoryId);
		dealPurchasesList = dealManager.searchDealPurchases(sb);
		//logger.debug("Got from DB "+dealsRList.size()+" dealReports");
		//ZUtils.printListOnLogger(list, logger, "debug");
		calculateSums();
		
		
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sDealNr.setValue(null);
		sDealTitle.setValue(null);
		sAgentName.setValue(null);
		sFrom = null;
		sTo = null;
		sToMinDate=null;
		sFromMaxDate=null;
		sFromStartDate=null;  
	    sToStartDate=null;
	    sToMinStartDate=null;
	    sFromMaxStartDate=null;  
	    sFromPurchaseDate=null;
	    sToPurchaseDate=null;
	    sFromPurchaseDateMax=null;
	    sToPurchaseDateMin=null;
	    sCategoryId.setValue("");
	}
	
	public void calculateSums(){
		sumNrPurchaseTotal = 0;
		sumTotAmount = 0;
		sumTotEarning = 0;
		sumAgentEarning = 0;
		
		for (DealPurchasesReport dr : dealPurchasesList) {
			sumNrPurchaseTotal += dr.getNrPurchaseTotal();
			sumTotAmount += dr.getTotAmount();
			sumTotEarning += dr.getTotEarning();
			sumAgentEarning += dr.getAgentEarning();
		}
	}
	
	public void postProcessXLS(Object document) {  
	    HSSFWorkbook wb = (HSSFWorkbook) document;  
	    HSSFSheet sheet = wb.getSheetAt(0);  
	    
	    HSSFRow header = sheet.getRow(0);  
	    header.setHeightInPoints(40);
	    
	    sheet.setColumnWidth(0, 1700);
	    sheet.setColumnWidth(1, 7800);
	    sheet.setColumnWidth(2, 2600);
	    sheet.setColumnWidth(3, 2600);
	    sheet.setColumnWidth(4, 2000);
	    sheet.setColumnWidth(5, 1800);
	    sheet.setColumnWidth(6, 1800);
	    sheet.setColumnWidth(7, 1800);
	    sheet.setColumnWidth(8, 1700);
	    sheet.setColumnWidth(9, 1700);
	    
	    sheet.setColumnWidth(10, 1700);//easyP
	    sheet.setColumnWidth(11, 1700);//bank
	    
	    sheet.setColumnWidth(12, 1500);//tot.shitura
	    sheet.setColumnWidth(13, 2100);//kupona likuiduar
	    sheet.setColumnWidth(14, 1900);
	    
	    sheet.setColumnWidth(15, 2000);	    
	    sheet.setColumnWidth(16, 2000);
	    sheet.setColumnWidth(17, 2000);
	    sheet.setColumnWidth(18, 2200);
	    sheet.setColumnWidth(19, 2000);
	    sheet.setColumnWidth(20, 1800);
	    sheet.setColumnWidth(21, 2100);
	    sheet.setColumnWidth(22, 2100);
	    sheet.setColumnWidth(23, 2100);
	    sheet.setColumnWidth(24, 2100);
	    sheet.setColumnWidth(25, 2200);
	    sheet.setColumnWidth(26, 1800);
	  
	    HSSFCellStyle cellStyle;  
	    for(int i=0; i < header.getPhysicalNumberOfCells();i++) {  
	        cellStyle = wb.createCellStyle(); 
	        cellStyle.setWrapText(true);
    		short bgColor = getCellColor(i);
 		    cellStyle.setFillForegroundColor(bgColor);  
 		    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 		     
 		    HSSFCell cell = header.getCell(i); 
	        cell.setCellStyle(cellStyle);
	        
	        // normalize column name
	        cell = changeColumnHeaderName(i, cell);
	    }  
	    
//	    //color all rows
//	    HSSFRow row; 
//	    for(int i=1; i<dealsRList.size()+1; i++){
//	    	row = sheet.getRow(i);
//	    	
//	    	//each cell of the row
//	    	for(int j=0; j < row.getPhysicalNumberOfCells();j++) {  
//	    		if(j==10 || j==14 || j==16 || j==24){
//		    		cellStyle = wb.createCellStyle(); 
//		    		short bgColor = getCellColor(j);
//		 		    cellStyle.setFillForegroundColor(bgColor);  
//		 		    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		 		    
//			        HSSFCell cell = row.getCell(j);  
//			        cell.setCellStyle(cellStyle);
//	    		}
//	    	}  
//	    }
	}

	private short getCellColor(int key) {
		short rv = HSSFColor.GREY_25_PERCENT.index;
		
		switch (key) {
			case 12:
				rv = HSSFColor.BLUE.index;				
				break;	
			case 16:
				rv = HSSFColor.RED.index;	
				break;
			case 18:
				rv = HSSFColor.BLUE.index;	
				break;				
			case 22:
				rv = HSSFColor.RED.index;	
				break;
			case 26:
				rv = HSSFColor.BLUE.index;	
				break;			
			default:
				break;
		}
		
		return rv;
	}

	private HSSFCell changeColumnHeaderName(int i, HSSFCell cell) {
		switch (i) {
		
		case 1:
			cell.setCellValue("Partneri");
			break;
		case 2:
			cell.setCellValue("Mbyllur");
			break;
		case 3:
			cell.setCellValue("Skadimi Ofertes");
			break;
		case 4:
			cell.setCellValue("Shet OZone");
			break;		
		case 5:
			cell.setCellValue("% Fitim");
			break;		
		case 6:
			cell.setCellValue("Blen OZone");
			break;
		case 7:
			cell.setCellValue("Fitim per kupon");
			break;
		case 8:
			cell.setCellValue("Shitura Cash");
			break;
		case 9:
			cell.setCellValue("Shitura PPal");
			break;
		case 10:
			cell.setCellValue("Shitura EasyP");
			break;
		case 11:
			cell.setCellValue("Shitura Bank");
			break;
		case 12:
			cell.setCellValue("Tot. Shitur");
			break;
		case 13:
			cell.setCellValue("Kupona Likuiduar");
			break;
		case 14:
			cell.setCellValue("Anulluar");
			break;
		case 15:
			cell.setCellValue("Skaduar");
			break;
		case 16:
			cell.setCellValue("Per tu likuiduar");
			break;
		case 17:
			cell.setCellValue("Bonus Perdorur");
			break;
		case 18:
			cell.setCellValue("Tot. Mbledhur");
			break;
		case 19:
			cell.setCellValue("Per partnerin");
			break;
		case 20:
			cell.setCellValue("Fitim");
			break;
		case 21:
			cell.setCellValue("Likuiduar");
			break;
		case 22:
			cell.setCellValue("Detyrim per partnerin");
			break;
		case 23:
			cell.setCellValue("Gjendje nga partneret");
			break;
		case 24:
			cell.setCellValue("Fitim per kuponat likuiduar");
			break;
		case 25:
			cell.setCellValue("Fitim nga skaduarit");
			break;
		case 26:
			cell.setCellValue("Fitim ABS");
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
	public DealManager getDealManager() {
		return dealManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	
	public int getSumTotAmount() {
		return sumTotAmount;
	}

	public void setSumTotAmount(int sumTotAmount) {
		this.sumTotAmount = sumTotAmount;
	}

	public int getSumTotEarning() {
		return sumTotEarning;
	}

	public void setSumTotEarning(int sumTotEarning) {
		this.sumTotEarning = sumTotEarning;
	}

	public int getSumNrPurchaseTotal() {
		return sumNrPurchaseTotal;
	}

	public void setSumNrPurchaseTotal(int sumNrPurchaseTotal) {
		this.sumNrPurchaseTotal = sumNrPurchaseTotal;
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

	public HtmlSelectOneMenu getsCategoryId() {
		return sCategoryId;
	}

	public void setsCategoryId(HtmlSelectOneMenu sCategoryId) {
		this.sCategoryId = sCategoryId;
	}
	
	public String getsToPurchaseDateMin() {
		if(sFromPurchaseDate != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sToPurchaseDateMin = dateFormat.format(sFromPurchaseDate);
		}
		return sToPurchaseDateMin;
	}
	
	public String getsFromPurchaseDateMax() {
		if(sToPurchaseDate != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sFromPurchaseDateMax = dateFormat.format(sToPurchaseDate);
		}
		return sFromPurchaseDateMax;
	}

	public Date getsFromPurchaseDate() {
		return sFromPurchaseDate;
	}

	public void setsFromPurchaseDate(Date sFromPurchaseDate) {
		this.sFromPurchaseDate = sFromPurchaseDate;
	}

	public Date getsToPurchaseDate() {
		return sToPurchaseDate;
	}

	public void setsToPurchaseDate(Date sToPurchaseDate) {
		this.sToPurchaseDate = sToPurchaseDate;
	}

	public void setsToPurchaseDateMin(String sToPurchaseDateMin) {
		this.sToPurchaseDateMin = sToPurchaseDateMin;
	}

	public void setsFromPurchaseDateMax(String sFromPurchaseDateMax) {
		this.sFromPurchaseDateMax = sFromPurchaseDateMax;
	}

	public HtmlInputText getsAgentName() {
		return sAgentName;
	}

	public void setsAgentName(HtmlInputText sAgentName) {
		this.sAgentName = sAgentName;
	}

	public List<DealPurchasesReport> getDealPurchasesList() {
		return dealPurchasesList;
	}

	public void setDealPurchasesList(List<DealPurchasesReport> dealPurchasesList) {
		this.dealPurchasesList = dealPurchasesList;
	}

	public DealPurchasesReport getDealPurchases() {
		return dealPurchases;
	}

	public void setDealPurchases(DealPurchasesReport dealPurchases) {
		this.dealPurchases = dealPurchases;
	}

	public HtmlInputText getsAgentCommission() {
		return sAgentCommission;
	}

	public void setsAgentCommission(HtmlInputText sAgentCommission) {
		this.sAgentCommission = sAgentCommission;
	}

	public int getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(int agentCommission) {
		this.agentCommission = agentCommission;
	}

	public int getSumAgentEarning() {
		return sumAgentEarning;
	}

	public void setSumAgentEarning(int sumAgentEarning) {
		this.sumAgentEarning = sumAgentEarning;
	}
	
	
}
