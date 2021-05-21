package al.ozone.engine.batch.jobs;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Role;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailRobot;

/** 
 * Send a notification to admins for the expired Deals in order to check 
 * all coupons status. 
 * @author Ermal Aliraj
 */
public class NotifyExpiredCouponJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(NotifyExpiredCouponJob.class);
	
	private DealManager dealManager;
	private UserManager userManager;
	private EmailRobot emailRobot;
	private CouponManager couponManager;

	public void execute() throws Exception {
		
		List<String> adminEmails = ZUtils.getEmailsFromUsers(userManager.getUsersByRoleId(Role.ROLE_ADMIN));
		List<Deal> expiredDeal = dealManager.getTodayExpiringDeals();
		logger.info("Sending "+expiredDeal.size()+" notifications to admins for expired Deals.");
		
		for (Deal deal : expiredDeal) {
			SearchCouponBean sb = new SearchCouponBean();
			sb.setContractId(deal.getId());
			sb.setStatus(Coupon.STATUS_NOT_USED);
			sb.setSortColumn("CODE");
			sb.setSortDirection("ASC");
			List<Coupon> notUsedCoupons = couponManager.search(sb);
			
			//TODO set status to all not used coupons as expired
			for (Coupon coupon : notUsedCoupons) {
				coupon.setStatus(Coupon.STATUS_EXPIRED);
				couponManager.changeStatus(coupon);
			}
			logger.info("Setted as expired all NOT USED coupons of deal "+deal.getId());
			
			Email email = new Email("ExpiredCoupon");
			email.setSubject("OZone - Oferta skadoi per "+deal.getPartner().getName());
			email.addParameter("deal", deal);
			email.addParameter("notUsedCoupons", notUsedCoupons);
	        email.addTo(adminEmails);
	        emailRobot.addEmail(email);
		}
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}
	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
}
