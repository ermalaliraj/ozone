package al.ozone.engine.email;

import java.io.Serializable;

import al.ozone.bl.model.Email;
import al.ozone.engine.email.EmailEngine;

/**
 * Proxy class for using EmailEngine (Thread) from JSF Controller.
 * JSF controller needs a serializabled object as property, as far as using 
 * javax.faces.STATE_SAVING_METHOD=client
 * 
 * @author Ermal Aliraj
 */
public class ProxyEmailEngineImpl implements ProxyEmailEngine, Serializable {

	private static final long serialVersionUID = -8969149654106851477L;
	
	private EmailEngine emailEngine;

	public EmailEngine getEmailEngine() {
		return emailEngine;
	}
	public void setEmailEngine(EmailEngine emailEngine) {
		this.emailEngine = emailEngine;
	}
	
    public void init() {
        emailEngine.start();
    }
    
	public void addEmail(Email email){
		emailEngine.addEmail(email);
	}

	@Override
	public void stopRunningSucceded() {
		this.emailEngine.stopRunningSucceded();
	}
	
}
