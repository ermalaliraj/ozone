package al.ozone.bl.manager;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.Customer;

public class CustomerManagerTest extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CustomerManagerTest.class);
    
    public void testCustomerCRUD(){
    	CustomerManager customerManager = (CustomerManager) applicationContext.getBean("customerManager");
    	
//    	Customer d = new Customer();
//    	d.setId(6);
//    	d.setName("flori");
//    	d.setSurname("gogaj");
//    	d.setBirthdate(new Date());
//    	d.setPassword("password");
//    	d.setEmail("flori@email.com");
//    	d.setPhone("1010");
//    	customerManager.insert(d);
    	
    	//1. Insert a Partner in DB
    	Customer c = new Customer();
    	c.setId(7);
    	c.setName("gerti");
    	c.setSurname("gjoka");
    	c.setBirthdate(new Date());
    	c.setPassword("password");
    	c.setEmail("gerti@email.com");
    	c.setPhone("1010");
    	customerManager.insert(c);
    	
    	//2. Read the partner inserted in step 1
    	Customer readCustomer = customerManager.get(c.getId());
    	assertEquals(c, readCustomer);
    	 	
    	//3. Update the partner in DB, and read again to check if the update took effect
    	readCustomer.setName("name_updated");
    	readCustomer.setSurname("surname_updated");
    	readCustomer.setBirthdate(new Date());
    	readCustomer.setEmail("name_updated@email.com");
    	readCustomer.setPhone("1010_updated");    	
    	
    	customerManager.update(readCustomer);
    	Customer readCustomerUpdated = customerManager.get(readCustomer.getId());
    	assertEquals(readCustomer, readCustomerUpdated);
    	
//    	//4. Delete the partner, and read from DB to check that does not exist anymore
    	customerManager.delete(readCustomerUpdated);    	
    	Customer readCustomerDeleted = customerManager.get(readCustomer.getId());
    	assertNull(readCustomerDeleted);  	    	
    }
    
 /*   
    public void testCustomerInvitedBy(){
    	CustomerManager customerManager = (CustomerManager) applicationContext.getBean("customerManager");
    	CreditManager creditManager = (CreditManager) applicationContext.getBean("creditManager");
    	
    	Customer d = new Customer();
    	//d.setId(11);
    	d.setName("flori");
    	d.setSurname("gogaj");
    	d.setBirthdate(new Date());
    	d.setPassword("password");
    	d.setEmail("flori@email.com");
    	d.setPhone("1010");
    	customerManager.insert(d);
    	
    	//1. Insert a Partner in DB
    	Customer c = new Customer();
    	c.setId(33);
    	c.setName("gerti");
    	c.setSurname("gjoka");
    	c.setBirthdate(new Date());
    	c.setPassword("password");
    	c.setEmail("gerti@email.com");
    	c.setPhone("1010");
    	c.setInvitedBy(32);
    	customerManager.insert(c);
    	
    	
    	List<Credit> list = creditManager.getByCustomer(d);
    	System.out.println(list);
    	assertEquals(list.get(0).getCustomer().getId(), new Integer(32));
    	assertEquals(list.get(0).getValue(), new Integer(500).intValue());
    	
//    	//2. Read the partner inserted in step 1
//    	Customer readCustomer = customerManager.get(c.getId());
//    	assertEquals(c, readCustomer);
//    	 	
//    	//3. Update the partner in DB, and read again to check if the update took effect
//    	readCustomer.setName("name_updated");
//    	readCustomer.setSurname("surname_updated");
//    	readCustomer.setBirthdate(new Date());
//    	readCustomer.setEmail("name_updated@email.com");
//    	readCustomer.setPhone("1010_updated");    	
//    	
//    	customerManager.update(readCustomer);
//    	Customer readCustomerUpdated = customerManager.get(readCustomer.getId());
//    	assertEquals(readCustomer, readCustomerUpdated);
//    	
////    	//4. Delete the partner, and read from DB to check that does not exist anymore
//    	customerManager.delete(readCustomerUpdated);    	
//    	Customer readCustomerDeleted = customerManager.get(readCustomer.getId());
//    	assertNull(readCustomerDeleted);  	    	
    }*/
}
