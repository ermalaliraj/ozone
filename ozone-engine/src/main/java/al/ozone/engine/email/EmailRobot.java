package al.ozone.engine.email;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import al.ozone.bl.model.Email;
import al.ozone.bl.model.User;

/**
 * Automated class which send emails taken from the list <code>messages</code>.<br>
 * The class is a thread, which stays alive until a "stopRunning" signal comes
 * through the method <code>stopWhenMessageListIsEmpty()</code>. If a "stopRunning"
 * signal comes, the thread will finish the execution only when will finish sending all 
 * emails present in the <code>messages</code> list.
 * 
 * @author Ermal Aliraj
 */
public class EmailRobot extends Thread implements Serializable{

	private static final long serialVersionUID = 1345182149551180270L;
	protected static final transient Log logger = LogFactory.getLog(EmailRobot.class);

	public final static int SECOND = 1000;
    public final static int MINUTE_1 = 60 * SECOND;

	protected JavaMailSender mailSender;
	private String hostMailSender;//only for logging to know which smpt are we using
	protected VelocityEngine velocityEngine;
	protected String emailFrom;
	protected String templateBase;
	protected String defaultSubject;

	protected Vector<Email> messages;
	private boolean stopRunning = false;
	private boolean stopWhenMessageListIsEmpty = false;
	private int maxRetries = 10;

	protected int sleepSeconds;

	public void init() {
		this.setName("Email Robot Thread");
		messages = new Vector<Email>();
	}

	/**
	 * Runs until receives a signal through stopWhenMessageListIsEmpty() and then, stops
	 * when the message list is empty. 
	 */
	public void run() {
		logger.info("***Started Email Robot***");

		while (true && !stopRunning) {
			//logger.debug("Cycling for new emails...");
			if (!stopRunning) {
				sendEmails();
				
				if(stopWhenMessageListIsEmpty){
					if(messages.size() == 0){
						logger.info("No more emails to send. Going to stop the thread...");
						stopRunning = true;
					}else{
						logger.info("Still have "+messages.size()+" emails in list. Going to cycle after "+sleepSeconds * SECOND+" milliseconds");
					}
				}
			}
			
			try {
				if (!stopRunning) {
					Thread.sleep(sleepSeconds * SECOND);
				}
			} catch (InterruptedException e) {
			}
		}
		logger.info("***Email Robot stopped***");
	}
	
	/**
	 * When stopWhenMessageListIsEmpty=true in the next cycling while the thread will be
	 * stopped if the messages list is empty.
	 */
	public void stopWhenMessageListIsEmpty() {
		stopWhenMessageListIsEmpty = true;
	}

	/**
	 * Add the given email to the messages queue.
	 * @param email Email to be added to the queue
	 */
	public void addEmail(Email email) {
		messages.add(email);
	}

	/**
	 * Send the emails present in the messages queue.<br>
	 * In case an email can not be sent, trace it, and retries until 
	 * retryCount has not exceed maxRetries
	 */
	protected void sendEmails() {
		boolean sent;
		Vector<Email> unsent = new Vector<Email>();

		while (messages.size() > 0) {
			Email email = messages.remove(0);
			sent = sendEmail(email);

			// If the mail can not be sent, trace the email
			if (!sent) {
				email.increaseRetryCount();
				// Only if maximum retries has not exceed
				if (email.getRetryCount() <= maxRetries) {
					unsent.add(email);
				}
			}
		}

		// At the end of the loop, store all emails which were not able to be sent, for 
		// later sending.
		messages.addAll(unsent);
	}

	/**
	 * Prepare the email, mapping the template using <code>VelocityEngine</code> , 
	 * and send it in transmission using <code>JavaMailSender</code>  
	 * @param email Email to send
	 * @return true if the email was sent correctly, false otherwise.
	 */
	public boolean sendEmail(final Email email) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				boolean multipart = email.getAttachments().size() > 0;

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, multipart);
				message.setTo((String[]) email.getTo().toArray(new String[] {}));
				message.setFrom(emailFrom, "OZone");//TODO put the name in properties file
				message.setSubject(email.getSubject() == null ? 
									defaultSubject: email.getSubject());

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, templateBase + email.getTemplate()
								+ ".vm", email.getParameters());
				message.setText(text, true);
				
				for (File attachment : email.getAttachments()) {
					if (attachment.exists() && attachment.canRead()) {
						message.addAttachment(attachment.getName(), attachment);
					}
				}
			}
		};
		try {
			logger.info("["+hostMailSender+"] Sending email " + email);
			this.mailSender.send(preparator);
			//logger.debug("Email sent correctly to: " + MessageUtils.buildMessageWithComma(email.getTo()));
			return true;
		} catch (MailException e) {
			logger.warn("Error sending email " + email, e);
			return false;
		}
	}
	
	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public void setHostMailSender(String hostMailSender) {
		this.hostMailSender = hostMailSender;
	}

	public void setDefaultSubject(String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}

	public void setTemplateBase(String templateBase) {
		this.templateBase = templateBase;
		if (!this.templateBase.endsWith("/")) {
			this.templateBase += "/";
		}
	}

	public void setSleepSeconds(int sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
		        "applicationContext-email.xml",
				"applicationContext-BatchEngine.xml",
				"applicationContext-manager.xml",
				"applicationContext-dao.xml",
				"applicationContext-resources.xml",
				"applicationContext-service.xml"
	        });

		EmailRobot robot = (EmailRobot) context.getBean("emailRobot");

		User wrongUser = new User();
		wrongUser.setUsername("Ermal user");
		wrongUser.setEmail("ermal.aliraj@gmail.com");
		wrongUser.setName("Ermal Aliraj");

		Email email = new Email("BadLogin");
		email.addTo("ermal.aliraj@gmail.com");
		email.addParameter("remoteAddress", "0.0.0.0");
		email.addParameter("user", wrongUser);

		robot.sendEmail(email);
	}

}