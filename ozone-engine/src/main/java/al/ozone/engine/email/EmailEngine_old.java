package al.ozone.engine.email;

import java.io.File;
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

import al.ozone.bl.utils.SerializationUtils;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.User;

public class EmailEngine_old extends Thread {

	protected static final transient Log logger = LogFactory.getLog(EmailEngine_old.class);
    public final static int SECOND = 1000;
    public final static int MINUTE_1 = 60 * SECOND;
	private final static String SERIALIZATION_FILE = "OZoneMailStore.ser";

	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private String emailFrom;
	private String templateBase;
	private String defaultSubject;
	private File emailStoreFile;

	private Vector<Email> messages;
	private boolean stopRunning = false;
	private boolean isRunning = false;
	private int maxRetries = 10;

	private int sleepSeconds;
	
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

	public void setEmailStoreFileName(String emailStoreFileName) {
		emailStoreFile = new File(emailStoreFileName, SERIALIZATION_FILE);
	}

	public void init() {
		this.setName("Email Engine Thread");
		messages = new Vector<Email>();

		if (emailStoreFile == null) {
			setupDefaultEmailStore();
		}
		restoreMessages();
	}

	public void run() {
		logger.info("***Started Email Engine Thread***");

		while (true && !stopRunning) {
			logger.debug("Cycling for new emails...");
			if (!stopRunning) {
				isRunning = true;
				sendEmails();
			}
			isRunning = false;
			try {
				if (!stopRunning) {
					Thread.sleep(sleepSeconds * SECOND);
				}
			} catch (InterruptedException e) {
			}
		}
		logger.info("***Email Engine Thread stopped***");
	}

	public boolean stopRunningSucceded() {
		logger.info("Stopping Email Engine ");
		stopRunning = true;
		this.interrupt();
		saveMessages();
		return !isRunning;
	}

	public void addEmail(Email email) {
		messages.add(email);
	}

	private void sendEmails() {
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

		// At the end of the loop, store all emails which couldn't send the mail for 
		// later sending.
		messages.addAll(unsent);
	}

	public boolean sendEmail(final Email email) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				boolean multipart = email.getAttachments().size() > 0;

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, multipart);
				message.setTo((String[]) email.getTo().toArray(new String[] {}));
				message.setFrom(emailFrom);

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
			logger.info("Sending email " + email);
			this.mailSender.send(preparator);
			return true;
		} catch (MailException e) {
			logger.warn("Error sending email " + email, e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void restoreMessages() {
		logger.info("Reloading email messages from Ser file: "+emailStoreFile.getAbsolutePath());

		if (emailStoreFile != null && emailStoreFile.exists() && emailStoreFile.canRead()) {
			Vector<Email> store;
			try {
				store = (Vector<Email>) SerializationUtils.load(emailStoreFile);
				messages = store;
				if (!emailStoreFile.delete()) {
					logger.warn("Error deleting file at " + emailStoreFile.getAbsolutePath());
				}
				logger.debug(messages.size()+" Email messages deserialized from file "+emailStoreFile.getAbsolutePath());
			} catch (Exception e) {
				logger.error("Error deserializing email messages from store", e);
			}
		} else {
			logger.debug("Email messages NOT deserialized from file at "+emailStoreFile.getAbsolutePath());
		}
	}

	private void saveMessages() {
		logger.debug("Saving email messages to Ser file "+emailStoreFile.getAbsolutePath());
		if (emailStoreFile != null && messages.size() > 0) {
			try {
				SerializationUtils.save(emailStoreFile, messages);
				logger.debug("Email messages serialized to file at "+emailStoreFile.getAbsolutePath());
			} catch (Exception e) {
				logger.error("Error deserializing email messages from store", e);
			}
		} else {
			logger.debug("Email messages not serialized to file "+emailStoreFile.getAbsolutePath());
		}
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

		EmailEngine_old robot = (EmailEngine_old) context.getBean("emailEngine");

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

	private void setupDefaultEmailStore() {
		String tempDir = SerializationUtils.getTempDir();
		File temp = new File(tempDir);

		if (temp.exists()) {
			emailStoreFile = new File(temp, SERIALIZATION_FILE);
		}
	}
}