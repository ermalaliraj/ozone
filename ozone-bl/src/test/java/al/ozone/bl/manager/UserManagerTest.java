package al.ozone.bl.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;


public class UserManagerTest extends AbstractSpringTestCase {
	
	 protected static final transient Log logger = LogFactory.getLog(UserManagerTest.class);
	    
	    public void testUser(){
	    	UserManager userManager = (UserManager) applicationContext.getBean("userManager");
	    	//AuditTrailManager auditTrailManager = (AuditTrailManager) applicationContext.getBean("auditTrailManager");
	    	User u = new User();
	    	u.setName("abdul");
	    	u.setPassword("8148ea3810544592fc8ac75526f66f5401d8e5f0");
	    //	userManager.save(user)
	    	User user = (User) userManager.loadUserByUsername("ermal");
	    	assertEquals(user.getUsername(), "ermal");
	    	
	    	List<Role> roles = (List<Role>) user.getRoles();
	    	assertNotNull(roles);
	    	assertTrue(roles.size()!=0);
	    	logger.debug("roles.size()="+roles.size());
	    	logger.debug("roles.get(0)="+roles.get(0));
	    	assertTrue( roles.get(0).getId().equals("ROLE_ADMIN") );
	    }
	    
	    /**
	     * Fails because no user connect in spring context
	     */
	    public void testSuccesfullLogin(){
//	    	AuditTrailManager auditTrailManager = (AuditTrailManager) applicationContext.getBean("auditTrailManager");
//	    	auditTrailManager.auditLoginCorrect("127.0.0.1");
	    }
	    

}
