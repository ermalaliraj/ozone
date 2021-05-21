package al.ozone.engine.email;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Partner;
import al.ozone.engine.email.EmailRobot;

/**
 * SendSingleMailMain.java tests EmailEngine for sending a single email 
 * without starting the thread.
 * 
 * @author Ermal
 *
 */
public class MailRobotMain extends AbstractSpringTestCase {
	
	protected static final transient Log logger = LogFactory.getLog(MailRobotMain.class);
	private ClassPathXmlApplicationContext applicationContext;
	
	protected String[] getApplicationContextPath() {
        return new String[] {
	        "applicationContext-email.xml",
			"applicationContext-BatchEngine.xml",
			"applicationContext-manager.xml",
			"applicationContext-dao.xml",
			"applicationContext-resources.xml",
			"applicationContext-service.xml"
        };
    }
	
	public MailRobotMain(){
		setup();
		sendEmail();
	}
	
	private void setup() {
		applicationContext = new ClassPathXmlApplicationContext(getApplicationContextPath());
	}
	
	private void sendEmail() {
		EmailRobot emailRobot = (EmailRobot) applicationContext.getBean("emailRobot");
		emailRobot.start();
		
		City city = TestUtils.createCity("TT", "Test");
    	Partner p = TestUtils.createPartner("Partner for deal junit", city);
    	Deal deal = TestUtils.createDeal("Scopri l'oferta del....junit", p);    
    	
        Email email = new Email("Newsletter");
        email.addTo("ermal.aliraj@gmail.com");
        email.addParameter("remoteAddress", "127.0.0.1");
        email.addParameter("deal", deal);
        emailRobot.addEmail(email);
        
//		User user = new User();
//		user.setUsername("ermal");
//		user.setEmail("ermal.aliraj@gmail.com");
//		user.setName("Ermal");
//		user.setSurname("Aliraj");
//		
//		email = new Email("BadLogin");
//		email.addTo("ermal.aliraj@gmail.com");
//		email.addParameter("remoteAddress", "127.0.0.1");
//		email.addParameter("user", user);
//		emailRobot.addEmail(email);
		
		emailRobot.stopWhenMessageListIsEmpty();	
	}
	
	
	public static void main(String[] args) {
		new MailRobotMain();
	}
}
