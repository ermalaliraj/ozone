package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.SearchAuditTrailBean;
import al.ozone.bl.model.AuditTrail;
import al.ozone.bl.model.Coupon;

public interface AuditTrailManager {
 
	public void auditLoginCorrect(String remoteAddress);//
	public void auditLoginBad(String username, String remoteAddress);//
	
	public void auditUserInserted(String username);//
	public void auditUserUpdated(String username);//
	public void auditUserDeleted(String username);//
	public void auditUserActive(String username);//
	public void auditUserNotActive(String username);//
	public void auditUserNotLocked(String username);//
	
	public void auditPartnerInserted(Integer partnerId);
	public void auditPartnerUpdated(Integer partnerId);
	public void auditPartnerDeleted(Integer partnerId);
	
	public void auditDealInserted(Integer dealId);
	public void auditDealUpdated(Integer dealId);
	public void auditDealDeleted(Integer dealId);
	public void auditDealApproved(Integer dealId);
	public void auditDealNotApproved(Integer dealId);
	
	public void auditPublicationInserted(Integer pubId);
	public void auditPublicationUpdated(Integer pubId);
	public void auditPublicationDeleted(Integer pubId);
	
	public void auditCustomerInserted(Integer customerId);
	public void auditCustomerUpdated(Integer customerId);
	public void auditCustomerActive(Integer customerId);
	public void auditCustomerNotActive(Integer customerId);
	
	public void auditAddPurchaseForCustomer(Integer purchaseId, Integer customerId);
	public void auditAddPaymentForCustomer(Integer paymentId, Integer customerId);
	public void auditAddCreditForCustomer(Integer creditId, Integer customerId);
	
	public void auditDiscountCard(Integer percentage, Integer totCards);
	

    @Secured({ "ROLE_ADMIN" })
	public List<AuditTrail> search(SearchAuditTrailBean sb);
    
	public void auditCouponStatusChange(Coupon coupon);
	public void auditErrorCouponStatusChange(Coupon coupon);
}
