package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.TopCustomersBean;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.EmailsIntroManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.model.SystemConfigBean;
import al.ozone.bl.utils.ZUtils;

@ManagedBean(name="dashboardController")
@RequestScoped
public class DashboardController implements Serializable {  

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(DashboardController.class);
	
	private DashboardModel model;  
	private CartesianChartModel purchaseModel; 
	private CartesianChartModel purchaseTotAmountModel;
	private CartesianChartModel earningModel;
	private CartesianChartModel customerModel;
	private CartesianChartModel allEmailsModel;	
	private CartesianChartModel activeDealsModel;
	private CartesianChartModel dealsModel;
	private Integer allCustomersNr;
	private Integer allEmailsNr;
	private Integer totAmount;
	private Integer totEarning;
	private Integer totDeals;
	private Integer totActiveDeals;
	private Integer totCouponsSold;
	private Integer totBuyers;
	
	private List<TopCustomersBean> topCustomersList;
	private List<ResultStatisticBean> purchasesByCountList;
	
	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;	
	@ManagedProperty(value="#{customerManager}") 
	private CustomerManager customerManager;
	@ManagedProperty(value="#{purchaseManager}") 
	private PurchaseManager purchaseManager;	
	@ManagedProperty(value="#{emailsIntroManager}") 
	private EmailsIntroManager emailsIntroManager;
      
	@PostConstruct
    public void init() {  
        model = new DefaultDashboardModel();  
        DashboardColumn column1 = new DefaultDashboardColumn(); 
        DashboardColumn column2 = new DefaultDashboardColumn(); 
        
        column1.addWidget("activeDeals");          
        column1.addWidget("purchases");
        column1.addWidget("topCustomers");
        column1.addWidget("allEmails");
        model.addColumn(column1);   
      
        column2.addWidget("customers");        
        column2.addWidget("statistics");      
        column2.addWidget("purchasesByCount");     
        model.addColumn(column2);  
        
//        createPurchasesModel();
//        createPurchaseTotAmountModel();
        createEarningModel();
        createCustomersModel();
        createAllEmailsModel();
        createActiveDealsModel();
        createDealsModel();
        
        topCustomersList = customerManager.getTopCustomers();
        purchasesByCountList = purchaseManager.getPurchasesByCount();
        totBuyers = purchaseManager.getTotBuyers();
    }

	private void createPurchasesModel() {  
    	purchaseModel = new CartesianChartModel();  
    	
    	ChartSeries purchases = new ChartSeries();  
    	purchases.setLabel("Purchases");
    	List<ResultStatisticBean> list = purchaseManager.getPurchasesGroupedByMonths();
    	for (ResultStatisticBean res : list) {
    		purchases.set(res.getKey(), res.getValue()); 
		}
    	
    	purchaseModel.addSeries(purchases);    	
    	
    	totCouponsSold = purchaseManager.getTotCouponsSold();
    }  
	
    private void createEarningModel() {
    	earningModel = new CartesianChartModel();  
    	
    	ChartSeries earnings = new ChartSeries();  
    	earnings.setLabel("Earnings");
    	List<ResultStatisticBean> earnList = purchaseManager.getEarningsGroupedByMonths();
    	for (ResultStatisticBean res : earnList) {
    		earnings.set(res.getKey(), res.getValue()); 
		}
    	earningModel.addSeries(earnings);	
    	
    	totEarning = purchaseManager.getSumTotEarning();
	}
    
    
    private void createPurchaseTotAmountModel() {  
    	purchaseTotAmountModel = new CartesianChartModel();  
    	
    	ChartSeries totAmounts = new ChartSeries();
    	totAmounts.setLabel("Total Amount");
    	List<ResultStatisticBean> list2 = purchaseManager.getTotAmountGroupedByMonths();
    	for (ResultStatisticBean res : list2) {
    		totAmounts.set(res.getKey(), res.getValue()); 
		}
    	purchaseTotAmountModel.addSeries(totAmounts); 
    	
    	totAmount = purchaseManager.getSumTotAmount();
    }  
    
    private void createActiveDealsModel() {  
    	activeDealsModel = new CartesianChartModel();  
    	ChartSeries activeDeals = new ChartSeries();  
    	activeDeals.setLabel("Akordet aktive"); 
    	
    	List<SystemConfigBean> list = dealManager.getActiveDealsGroupedByPurchases();
    	if(!ZUtils.isEmptyList(list)){
    		for (SystemConfigBean mapBean : list) {
    			Integer val = 0;
				try {
					val = Integer.parseInt(mapBean.getValue());
				} catch (Exception e) {;}
				activeDeals.set("d"+mapBean.getKey(), val); 
			}
    	}

    	activeDealsModel.addSeries(activeDeals); 
    	
    	totActiveDeals = dealManager.getTotActiveDeals();
    }  
    
    private void createCustomersModel() {  
    	customerModel = new CartesianChartModel();  
      	
    	ChartSeries customers = new ChartSeries();  
    	customers.setLabel("Klient te regjistruar"); 
    	
    	List<ResultStatisticBean> list = customerManager.getCustomersGroupByRegMonths();
    	for (ResultStatisticBean res : list) {
    		customers.set(res.getKey(), res.getValue()); 
		}
    	customerModel.addSeries(customers); 

        allCustomersNr = customerManager.getCountCustomers();  
    }  
    
	private void createAllEmailsModel() {
		allEmailsModel = new CartesianChartModel();  
		
    	ChartSeries allEmails = new ChartSeries();  
    	allEmails.setLabel("Email-e ne Database"); 
    	
    	List<ResultStatisticBean> list = emailsIntroManager.getEmailsGroupByRegMonths();
    	for (ResultStatisticBean res : list) {
    		allEmails.set(res.getKey(), res.getValue()); 
		}
    	allEmailsModel.addSeries(allEmails); 

        allEmailsNr = emailsIntroManager.getCountEmails();  
		
	}
    
    private void createDealsModel() {  
    	dealsModel = new CartesianChartModel();  
  
        ChartSeries dealsChart = new ChartSeries();  
        dealsChart.setLabel("Oferta te publikuara"); 
    	List<ResultStatisticBean> list = dealManager.getDealsGroupByPubDate();
    	for (ResultStatisticBean res : list) {
    		dealsChart.set(res.getKey(), res.getValue()); 
		}
        dealsModel.addSeries(dealsChart);  
        
        totDeals = dealManager.getAllClosedDealsCount();
    }  

	public DashboardModel getModel() {
		return model;
	}
	public void setModel(DashboardModel model) {
		this.model = model;
	}
	public CartesianChartModel getPurchaseModel() {
		return purchaseModel;
	}
	public void setPurchaseModel(CartesianChartModel purchaseModel) {
		this.purchaseModel = purchaseModel;
	}
	public CartesianChartModel getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(CartesianChartModel customerModel) {
		this.customerModel = customerModel;
	}
	public CartesianChartModel getActiveDealsModel() {
		return activeDealsModel;
	}
	public void setActiveDealsModel(CartesianChartModel activeDealsModel) {
		this.activeDealsModel = activeDealsModel;
	}
	public CartesianChartModel getDealsModel() {
		return dealsModel;
	}
	public void setDealsModel(CartesianChartModel dealsModel) {
		this.dealsModel = dealsModel;
	}
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}
	public CartesianChartModel getPurchaseTotAmountModel() {
		return purchaseTotAmountModel;
	}
	public void setPurchaseTotAmountModel(CartesianChartModel purchaseTotAmountModel) {
		this.purchaseTotAmountModel = purchaseTotAmountModel;
	}
	public Integer getAllCustomersNr() {
		return allCustomersNr;
	}
	public void setAllCustomersNr(Integer allCustomersNr) {
		this.allCustomersNr = allCustomersNr;
	}
	public CartesianChartModel getEarningModel() {
		return earningModel;
	}
	public void setEarningModel(CartesianChartModel earningModel) {
		this.earningModel = earningModel;
	}
	public List<TopCustomersBean> getTopCustomersList() {
		return topCustomersList;
	}
	public void setTopCustomersList(List<TopCustomersBean> topCustomersList) {
		this.topCustomersList = topCustomersList;
	}
	public List<ResultStatisticBean> getPurchasesByCountList() {
		return purchasesByCountList;
	}
	public void setPurchasesByCountList(List<ResultStatisticBean> purchasesByCountList) {
		this.purchasesByCountList = purchasesByCountList;
	}
	public CartesianChartModel getAllEmailsModel() {
		return allEmailsModel;
	}
	public void setAllEmailsModel(CartesianChartModel allEmailsModel) {
		this.allEmailsModel = allEmailsModel;
	}
	public Integer getAllEmailsNr() {
		return allEmailsNr;
	}
	public void setAllEmailsNr(Integer allEmailsNr) {
		this.allEmailsNr = allEmailsNr;
	}
	public void setEmailsIntroManager(EmailsIntroManager emailsIntroManager) {
		this.emailsIntroManager = emailsIntroManager;
	}
	public Integer getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(Integer totAmount) {
		this.totAmount = totAmount;
	}
	public Integer getTotDeals() {
		return totDeals;
	}
	public void setTotDeals(Integer totDeals) {
		this.totDeals = totDeals;
	}
	public Integer getTotBuyers() {
		return totBuyers;
	}
	public void setTotBuyers(Integer totBuyers) {
		this.totBuyers = totBuyers;
	}
	public Integer getTotEarning() {
		return totEarning;
	}
	public void setTotEarning(Integer totEarning) {
		this.totEarning = totEarning;
	}
	public Integer getTotActiveDeals() {
		return totActiveDeals;
	}
	public void setTotActiveDeals(Integer totActiveDeals) {
		this.totActiveDeals = totActiveDeals;
	}
	public Integer getTotCouponsSold() {
		return totCouponsSold;
	}
	public void setTotCouponsSold(Integer totCouponsSold) {
		this.totCouponsSold = totCouponsSold;
	}

}