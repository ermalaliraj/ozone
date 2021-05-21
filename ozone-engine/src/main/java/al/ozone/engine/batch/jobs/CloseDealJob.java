package al.ozone.engine.batch.jobs;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.DealManager;

/**
 * This Job checks all deals and change their status to (A)active
 * or (C)losed depending on the Start and End date.
 * 
 * @author Ermal Aliraj
 */
public class CloseDealJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(CloseDealJob.class);
	
	private DealManager dealManager;

	public DealManager getDealManager() {
		return dealManager;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}

	public void execute() throws Exception {
		dealManager.changeDealsStatus();
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}
}
