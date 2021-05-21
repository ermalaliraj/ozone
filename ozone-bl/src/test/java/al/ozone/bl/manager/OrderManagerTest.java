package al.ozone.bl.manager;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Orders;
import al.ozone.bl.model.PaymentPayPal;
import al.ozone.bl.model.Publication;
import al.ozone.bl.model.Purchase;



public class OrderManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(OrderManagerTest.class);
    
    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }
    
    public void testOrderCRUD(){
    	OrdersManager ordersManager = (OrdersManager) applicationContext.getBean("ordersManager");
    	PurchaseManager purchaseManager = (PurchaseManager) applicationContext.getBean("purchaseManager");
    	
    	//1. Insert
    	Orders o = new Orders();
    	o.setCustomer(new Customer(1));
    	//o.setPublication(new Publication(1));
    	o.setQuantity(2);
    	o.setMoneySpent(300);
    	o.setCreditUsed(50);
    	o.setDate(new Date());
    	o.setAddress("rr mihal grameno");
    	o.setTel("323245");
    	o.setExchangeRate("0.0074");
    	
    	ordersManager.insert(o);
    }
    
    public void testPurchaseNotConfirmed(){
    	PurchaseManager purchaseManager = (PurchaseManager) applicationContext.getBean("purchaseManager");
    	
    	Purchase pur = purchaseManager.getPurchaseByOrderId(1);
    	System.out.println(pur);
		purchaseManager.setPurchaseAsNotConfirmed(pur, PaymentPayPal.STATUS_REFUNDED);
    }
}
