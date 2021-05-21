package al.ozone.engine.email;

import java.io.Serializable; 

import al.ozone.bl.model.Email;
import al.ozone.engine.email.EmailRobot;

/**
 * Proxy class for using EmailRobot (Thread) from JSF Controller.
 * JSF controller needs a serializabled object as property, as far as using 
 * javax.faces.STATE_SAVING_METHOD=client
 * 
 * @author Ermal Aliraj
 */
public class ProxyEmailRobotImpl implements ProxyEmailRobot, Serializable {

	private static final long serialVersionUID = -8969149654106851477L;
	
	private EmailRobot emailRobot;
	
//	public void init() {
//		//emailRobot.start();
//	}
//	
//	public void stop() {
//		emailRobot.stopWhenMessageListIsEmpty();
//	}
//	
//	public void addEmail(Email email){
//		emailRobot.addEmail(email);
//	}
	
	public EmailRobot getEmailRobot() {
		return emailRobot;
	}
	
	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}

	public boolean sendEmail(Email email) {
		return emailRobot.sendEmail(email);
	}
	

	
}
