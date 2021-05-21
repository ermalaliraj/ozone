package al.ozone.bl.manager;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentEasyPay;
import al.ozone.bl.model.PaymentPayPal;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class PaymentManagerTest extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(PaymentManagerTest.class);

	protected PaymentManager paymentManager;
	protected Payment payment = null;

	protected void setUp() throws Exception {
		super.setUp();
		paymentManager = (PaymentManager) applicationContext.getBean("paymentManager");
	}
	
	//TODO CRUD  Flori 
	public void testPurchaseCRUD() throws Exception{	
		
    	Payment cashPayment = paymentManager.get(1);
    	System.out.println("cashPayment:"+cashPayment);
    	assertNotNull(cashPayment);
    	
    	Payment paypalPayment = paymentManager.get(2);
    	System.out.println("paypalPayment:"+paypalPayment);
    	assertNotNull(paypalPayment);
    }
	
	//TODO CRUD  Flori 
	public void testPayPal() throws Exception{	
		
		PaymentPayPal pp = new PaymentPayPal();
		pp.setAmountEUR("22.45");
		pp.setCurrency("EUR");
		//pp.setExchangeRate("0,0074");
		
		paymentManager.insert(pp);
	
		
		Payment readPP = paymentManager.get(pp.getId());
		assertEquals(readPP, pp);
    }
	
	public void testEasyPay() throws Exception{	
		
		PaymentEasyPay ep = new PaymentEasyPay();
		ep.setTxnId("aaaaa111");
		ep.setOrderId("etet");
		ep.setMerchantUsername("ermal");
		ep.setResponseCode(123);
		ep.setTxnStatus("dsfdsfs");
		ep.setOriginalResponse("all the queryyyy");
		ep.setAmount(1200);
		ep.setDate(new Date());
		ep.setFee(33.76);
		
		paymentManager.insert(ep);
	
		
		Payment readEP = paymentManager.get(ep.getId());
		System.out.println("easyPay:"+readEP);
		assertEquals(readEP, ep);
    }
	
	protected void tearDown(){

	}
	
}
