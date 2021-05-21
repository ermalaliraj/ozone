package al.ozone.engine.batch.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.engine.email.EmailRobot;

/** 
 * For each closed Deal, if confirmed, create the coupon for every single purchase and
 * send it to the customer which purchased the deal.
 * 
 * @author Ermal Aliraj
 */
public class PdfCreatorJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(PdfCreatorJob.class);
	
	private DealManager dealManager;
	private CouponManager couponManager;
	private EmailRobot emailRobot;
	
	
	public void execute() throws Exception {
		List<Email> emailsToSend = new ArrayList<Email>();
	
		List<Deal> closedDeals = dealManager.getDealsEndToday();
		logger.info(closedDeals.size()+" closed deals to prepare coupons");
		//appends to emailsToSend all emails for each deal
		List<Email> dealEmails = new ArrayList<Email>();
		for (Deal d : closedDeals) {
			//if the coupons are Prepared On each purchase do nothing.
			//if(!p.getDeal().isCouponImmediately()){  // actually this control is doing inside the implementation
				dealEmails = dealManager.prepareCoupons(d);
				emailsToSend.addAll(dealEmails);
//			}else{
//				logger.info("Coupons for Deal: "+p.getId()+" ("+p.getDeal().getTitle()+") are created anticipately on each purchase! No email will be send to customers!");
//			}
		}
		
		//add all emails in the EmailRobot queue
		for (Email email : emailsToSend) {
			emailRobot.addEmail(email);
		}
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}
	
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public CouponManager getCouponManager() {
		return couponManager;
	}
	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}
	public EmailRobot getEmailRobot() {
		return emailRobot;
	}
	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}
}
