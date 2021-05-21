package al.ozone.engine.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.engine.batch.jobs.BatchJobInterface;
import al.ozone.engine.email.EmailRobot;

/**
 * Thread class which runs all jobs present in the list.
 * The class uses <code>EmailRobot</code> to send emails to the customers.
 * 
 * @author Ermal
 *
 */
public class BatchEngine extends Thread{
	
	protected static final transient Log logger = LogFactory.getLog(BatchEngine.class);
    public final static int SECOND = 1000;
    public final static int MINUTE_1 = 60 * SECOND;
    
    private EmailRobot emailRobot;
    private List<BatchJobInterface> jobs;
    
    /**
     * Starts EmailRobot which will send all the emails
     * that the different jobs will put in queue.
     */
    public void init() {
        setName("Init BatchEngine");
        emailRobot.start();
    }

    /**
     * Executes all jobs, then gives the signal to EmailRobot thread that 
     * can be shut down after finished sending all the emails.
     */
    public void run() {
    	executeJobs();
    	emailRobot.stopWhenMessageListIsEmpty();
    }
	
    /**
     * Executed one by one the list of Batch Jobs
     */
    public void executeJobs() {
        logger.info("-- Starting Batch Engine ("+jobs.size()+" jobs ready to start) --");

        for (int i = 0; i < jobs.size(); i++) {
        	BatchJobInterface job = jobs.get(i);
            String jobName = job.getJobName();
            logger.info("*Starting Job: " + jobName);

            try {
                job.execute();
                logger.info("*Job Executed: " + jobName);
            } catch (Exception e) {
                logger.error("Job Error: " + jobName, e);
            }
        }

        logger.info("-- Finished Batch Engine --");
    }
    
	public List<BatchJobInterface> getJobs() {
		return jobs;
	}
	public void setJobs(List<BatchJobInterface> jobs) {
		this.jobs = jobs;
	}
	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}

}
