package al.ozone.engine.email;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;

import al.ozone.bl.model.Email;
import al.ozone.bl.utils.SerializationUtils;

/**
 * Automated class extends the thread EmailRobot.
 * The class sends emails using the fathers functionality and uses other approaches to 
 * save not yet sent messages in case of a "stop" signal. The saved messages, in file system,
 * will be send in the next execution of the thread.
 * The loop stays alive until a stop signal comes through the method <code>stopRunningSucceded</code>
 * This class is used from the WebApplication OZoneAdmin, which needs an alive system 
 * always running, to send emails that it puts in the queue during its life cycle.
 * 
 * @author Ermal Aliraj
 * @see #EmailRobot
 */
public class EmailEngine extends EmailRobot implements Serializable {
	
	private static final long serialVersionUID = 6508118655878317962L;
	private final static String SERIALIZATION_FILE = "OZoneMailStore.ser";
	
	private File emailStoreFile;
	private boolean stopRunning = false;
	private boolean isRunning = false;
	
	public void init() {
		super.init();
		this.setName("Email Engine Thread");

		if (this.emailStoreFile == null) {
			setupDefaultEmailStore();
		}
		restoreMessages();
	}
	
	/**
	 * Method override, to make the loop always run, until a "stop" signal comes from
	 * method stopRunningSucceded().
	 */
	public void run() {
		logger.info("***Started Email Engine Thread***");

		while (true && !stopRunning) {
			//logger.debug("Cycling for new emails...");
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
	
	/**
	 * Interrupt the thread, set stopRunning=true for interrupting the while 
	 * in run method.
	 * @return true if the thread was inactive, false otherwise.
	 */
	public boolean stopRunningSucceded() {
		logger.info("Stopping Email Engine ");
		stopRunning = true;
		this.interrupt();
		saveMessages();
		return !isRunning;
	}
	
	/**
	 * Save emails present in the queue in file system.
	 */
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
	
	/**
	 * Restore emails from file system.
	 */
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
	
	/**
	 * Set the directory where to read/write serialization file for the messages queue.
	 */
	private void setupDefaultEmailStore() {
		String tempDir = SerializationUtils.getTempDir();
		File temp = new File(tempDir);

		if (temp.exists()) {
			emailStoreFile = new File(temp, SERIALIZATION_FILE);
		}
	}
}
