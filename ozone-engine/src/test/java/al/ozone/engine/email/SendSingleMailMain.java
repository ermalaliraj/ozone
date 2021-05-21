package al.ozone.engine.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.User;
import al.ozone.engine.email.EmailRobot;

/**
 * SendSingleMailMain.java tests EmailEngine for sending a single email 
 * without starting the thread.
 * 
 * @author Ermal
 *
 */
public class SendSingleMailMain extends AbstractSpringTestCase {
	
	protected static final transient Log logger = LogFactory.getLog(SendSingleMailMain.class);
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
	
	public SendSingleMailMain(){
		setup();
		sendEmail();
	}
	
	private void setup() {
		applicationContext = new ClassPathXmlApplicationContext(getApplicationContextPath());
	}
	
	private void sendEmail() {
		EmailRobot emailEngine = (EmailRobot) applicationContext.getBean("emailEngine");
		
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
	
	
	public static void main(String[] args) {
		new SendSingleMailMain();
	}
}
