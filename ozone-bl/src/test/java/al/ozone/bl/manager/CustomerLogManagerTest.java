package al.ozone.bl.manager;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Customer;
import al.ozone.bl.model.CustomerLog;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class CustomerLogManagerTest extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CustomerLogManagerTest.class);
    
    public void testCustomerCRUD(){
    	CustomerLogManager customerLogManager = (CustomerLogManager) applicationContext.getBean("customerLogManager");
    	
    	CustomerLog log = new CustomerLog();
    	log.setCustomer(new Customer(null));
    	log.setOpType(CustomerLog.LOGIN);
    	log.setOpDate(new Date());
    	log.setErrorMsg("Error loging");
    	
    	customerLogManager.insert(log);
    	
    	CustomerLog readLog = customerLogManager.get(log.getId());
    	assertEquals(readLog.getOpType(), log.getOpType());
    }
    
    public void testCustomerGet(){
    	CustomerLogManager customerLogManager = (CustomerLogManager) applicationContext.getBean("customerLogManager");
    	
    	CustomerLog readLog = customerLogManager.get(3);
    	logger.debug(readLog);
    	
    	assertNotNull(readLog);
    }
    
}
