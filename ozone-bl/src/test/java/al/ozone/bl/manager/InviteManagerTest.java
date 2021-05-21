package al.ozone.bl.manager;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Invite;
import al.ozone.bl.model.Partner;

public class InviteManagerTest  extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(InviteManagerTest.class);

	protected InviteManager inviteManager;

	protected void setUp() throws Exception {
		super.setUp();
		inviteManager = (InviteManager) applicationContext.getBean("inviteManager");
	}
	
    public void testDealCRUD() throws Exception{
    	
    	Invite i = new Invite();
    	i.setInviterId(5);
    	i.setInvitedId(611);
    	i.setOperationDate(new Date());
    	inviteManager.insert(i);
    }
    
}
