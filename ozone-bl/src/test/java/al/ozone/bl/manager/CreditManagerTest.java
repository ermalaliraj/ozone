package al.ozone.bl.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;

public class CreditManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CreditManagerTest.class);
    
    protected CustomerManager customerManager;
	protected CreditManager creditManager;
	
	protected Customer customer = null;
	protected Credit credit = null;
    
	protected void setUp() throws Exception {
		super.setUp();
		customerManager = (CustomerManager) applicationContext.getBean("customerManager");
		creditManager = (CreditManager) applicationContext.getBean("creditManager");


		try {
			customer = TestUtils.createCustomer("testcase@yahoo.com");
			customerManager.insert(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    public void testCreditCRUD() throws Exception{
    	
    	credit = TestUtils.createCredit(customer, 500);
    	creditManager.insert(credit);
    	    	
    	//2. Read the Credit inserted in step 1
    	Credit readCredit = creditManager.get(credit.getId());
    	assertEquals(credit, readCredit);
    	
    	//3.use credit
    	assertNull(readCredit.getUsedDate());
    	creditManager.setCreditAsUsed(readCredit.getId(), "usato dal junit");
    	Credit readCredit2 = creditManager.get(credit.getId());
    	assertNotNull(readCredit2.getUsedDate());
    	
    	//4. Delete the credit, and read from DB to check that does not exist anymore
    	creditManager.delete(credit);    	
    	Credit readCreditDeleted = creditManager.get(credit.getId());
    	assertNull(readCreditDeleted);  
    }
    
    protected void tearDown(){
		try{
			creditManager.delete(credit);
			customerManager.delete(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
