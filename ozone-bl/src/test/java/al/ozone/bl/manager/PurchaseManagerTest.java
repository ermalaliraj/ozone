package al.ozone.bl.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import al.ozone.bl.dao.DealChoiceDAO;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.Partner;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.Publication;
import al.ozone.bl.model.Purchase;

public class PurchaseManagerTest extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(PurchaseManagerTest.class);
    
	protected PurchaseManager purchaseManager;
	protected DealChoiceDAO dealChoiceDAO;
	protected DealManager dealManager;
	protected PartnerManager partnerManager;
	protected CityManager cityManager;
	protected CustomerManager customerManager;
	protected CreditManager creditManager;
	protected DiscountCardManager discountCardManager;
	protected PaymentManager paymentManager;
	
	protected City city = null;
	protected Partner partner = null;
	protected Deal deal = null;
	protected DealChoice dealChoice = null;
	protected Customer customer = null;
	protected Credit credit = null;
	protected DiscountCard discountCard = null;
	protected Payment payment = null;
	protected Purchase purchase = null;

	protected void setUp() throws Exception {
		super.setUp();
		purchaseManager = (PurchaseManager) applicationContext.getBean("purchaseManager");
		dealChoiceDAO = (DealChoiceDAO) applicationContext.getBean("dealChoiceDAO");
		dealManager = (DealManager) applicationContext.getBean("dealManager");
		partnerManager = (PartnerManager) applicationContext.getBean("partnerManager");
		cityManager = (CityManager) applicationContext.getBean("cityManager");
		customerManager = (CustomerManager) applicationContext.getBean("customerManager");
		creditManager = (CreditManager) applicationContext.getBean("creditManager");
		discountCardManager = (DiscountCardManager) applicationContext.getBean("discountCardManager");
		paymentManager = (PaymentManager) applicationContext.getBean("paymentManager");
		
		//City/Partner/Deal /Customer/Credit/DiscountCard
		try {
	        city = TestUtils.createCity("TT", "Test");
	        cityManager.insert(city);
	        
	        partner = TestUtils.createPartner("Partner for deal junit", city);
	        partnerManager.insert(partner);
	        
	    	deal = TestUtils.createDeal("Scopri l'oferta del....junit", partner);  
	    	dealManager.insert(deal);
	    	
	    	dealChoice = TestUtils.createDealChoice(deal.getId(), 1);
	    	dealChoiceDAO.insert(dealChoice);
	    	
	    	customer = TestUtils.createCustomer("purchaseExmapleCust");
	    	customerManager.insert(customer);
	    	
	    	credit = TestUtils.createCredit(customer, 30);
	    	creditManager.insert(credit);
	    	
	    	discountCard = TestUtils.createDiscountCard(20);
	    	discountCardManager.insert(discountCard);
	    	
	    	payment = TestUtils.createPaymentCash();
	    	paymentManager.insert(payment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void testPurchaseCRUD() throws Exception{	
		
    	//1. Insert the publication for the deal inserted in precedence
        purchase = TestUtils.createPurchase(dealChoice, customer, payment, discountCard);
        purchaseManager.insert(purchase, true);
    	
    	//2. Read the Purchase inserted in step 1
    	Purchase readPurchase = purchaseManager.get(purchase.getId());
    	assertEquals(purchase, readPurchase);
    	
    	//3. Update the Purchase in DB, and read again to check if the update took effect
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
    	Date startDate = null;
		try {
			startDate = dateFormat.parse("22/12/2011");
		} catch (ParseException e) {
			logger.debug("Not a date string", e);
		}
		readPurchase.setQuantity(1);
		readPurchase.setPurchDate(startDate);
		readPurchase.setAmount(400);
		readPurchase.setMoneySpent(5000);
		readPurchase.setCreditSpent(35);
		readPurchase.setDealChoice(dealChoice);
		readPurchase.setCustomer(customer);
		readPurchase.setPayment(payment);
		//pur.setCredits(customer.getCredits());
		readPurchase.setDiscount(discountCard);
		
//		purchaseManager.update(readPurchase);
//    	Purchase readPurchaseUpdated = purchaseManager.get(purchase.getId());
//    	assertEquals(readPurchase, readPurchaseUpdated);
    	
    	//4. Delete the Purchase/Deal/Partner/City, and read from DB to check that does not exists anymore
//    	purchaseManager.delete(purchase);
//    	Purchase readPurchaseDeleted = purchaseManager.get(purchase.getId());
//    	assertNull(readPurchaseDeleted); 
    }
	
	public void testPurchaseGet() throws Exception{	
	    	
	    	//2. Read the Purchase inserted in step 1
	    	Purchase readPurchase = purchaseManager.get(1);
	    	
//	    	System.out.println(readPurchase.getDealChoice());
	}

//	protected void tearDown(){
//		cleanDB();
//	}

//	private void cleanDB() {
//		try{
//			purchaseManager.delete(purchase);
//			dealChoiceDAO.delete(dealChoice);
//			dealManager.delete(deal);
//	    	partnerManager.delete(partner);
//	    	cityManager.delete(city);
//
//	    	creditManager.delete(credit);
//	    	customerManager.delete(customer);
//	    	discountCardManager.delete(discountCard);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
