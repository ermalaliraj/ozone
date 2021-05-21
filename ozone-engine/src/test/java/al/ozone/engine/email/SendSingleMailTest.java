package al.ozone.engine.email;

import al.ozone.bl.model.*;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendSingleMailTest extends AbstractSpringTestCase {
    
    protected static final transient Log logger = LogFactory.getLog(SendSingleMailTest.class);
    
    protected EmailRobot emailEngine;
    
    protected void setUp() throws Exception {
        super.setUp();
        emailEngine = (EmailRobot) applicationContext.getBean("emailEngine");
    }
    
    public void testEmailLockedUser() {
        User user = new User();
        user.setUsername("Ermal user");
        user.setEmail("ermal.aliraj@gmail.com");
        user.setName("Ermal Aliraj");
        
        Email email = new Email("BadLogin");
        email.addTo("ermal.aliraj@gmail.com");
        email.addParameter("remoteAddress", "127.0.0.1");
        email.addParameter("user", user);
        
        emailEngine.sendEmail(email);
    }
    
    public void testEmailNewsletter() {
        City city = TestUtils.createCity("TT", "Test");
        Partner p = TestUtils.createPartner("Partner for deal junit", city);
        Deal deal = TestUtils.createDeal("Scopri l'oferta del....junit", p);
        
        Email email = new Email("Newsletter");
        email.addTo("ermal.aliraj@gmail.com");
        email.addParameter("remoteAddress", "127.0.0.1");
        email.addParameter("deal", deal);
        
        emailEngine.sendEmail(email);
    }
    
}
