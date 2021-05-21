package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.bean.SearchAuditTrailBean;
import al.ozone.bl.dao.AuditTrailDAO;
import al.ozone.bl.model.AuditTrail;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.ZUtils;

public class AuditTrailManagerImpl implements AuditTrailManager, Serializable {
	
	private static final long serialVersionUID = -6912356930371907394L;
	public AuditTrailDAO auditTrailDAO;
	
	public void setAuditTrailDAO(AuditTrailDAO auditTrailDAO) {
		this.auditTrailDAO = auditTrailDAO;
	}

	private void insert(AuditTrail record) {
		auditTrailDAO.insert(record);
	}
	
	@Override
	public void auditLoginCorrect(String remoteAddress) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.LOGIN, "User loged from ip "+remoteAddress);
		insert(audit);
	}
	
	@Override
	public void auditLoginBad(String username, String remoteAddress) {
		AuditTrail audit = getPreCompiledAuditTrail(username, "", AuditTrail.LOGIN_WRONG, "Wrong login from ip "+remoteAddress);
		insert(audit);
	}

	@Override
	public void auditUserInserted(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_INSERT, "Inserted new user "+username);
		insert(audit);
	}

	@Override
	public void auditUserUpdated(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_UPDATE, "Updated user "+username);
		insert(audit);
	}

	@Override
	public void auditUserDeleted(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_DELETE, "Deleted user "+username);
		insert(audit);
	}

	@Override
	public void auditUserActive(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_ACTIVE, "Set to Active user "+username);
		insert(audit);
	}

	@Override
	public void auditUserNotActive(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_NOT_ACTIVE, "Set to NOT Active user "+username);
		insert(audit);
	}

	@Override
	public void auditUserNotLocked(String username) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.USER_NOT_LOCKED, "Set to NOT Locked user "+username);
		insert(audit);
	}

	@Override
	public void auditPartnerInserted(Integer partnerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PARTNER_INSERT, "Inserted partner "+partnerId);
		insert(audit);
	}

	@Override
	public void auditPartnerUpdated(Integer partnerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PARTNER_UPDATE, "Updated partner "+partnerId);
		insert(audit);
	}

	@Override
	public void auditPartnerDeleted(Integer partnerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PARTNER_DELETE, "Deleted partner "+partnerId);
		insert(audit);
	}

	@Override
	public void auditDealInserted(Integer dealId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DEAL_DELETE, "Inserted new deal "+dealId);
		insert(audit);
	}

	@Override
	public void auditDealUpdated(Integer dealId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DEAL_UPDATE, "Updated deal "+dealId);
		insert(audit);
	}

	@Override
	public void auditDealDeleted(Integer dealId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DEAL_DELETE, "Deleted deal "+dealId);
		insert(audit);
	}

	@Override
	public void auditDealApproved(Integer dealId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DEAL_APPROVED, "Approved deal "+dealId);
		insert(audit);
	}

	@Override
	public void auditDealNotApproved(Integer dealId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DEAL_DELETE, "Changed to NOT approved deal "+dealId);
		insert(audit);
	}

	@Override
	public void auditPublicationInserted(Integer pubId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PUBLICATION_INSERT, "Inerted publication "+pubId);
		insert(audit);
	}

	@Override
	public void auditPublicationUpdated(Integer pubId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PUBLICATION_UPDATE, "Updated publication "+pubId);
		insert(audit);
	}

	@Override
	public void auditPublicationDeleted(Integer pubId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PUBLICATION_DELETE, "Deleted publication "+pubId);
		insert(audit);
	}

	@Override
	public void auditCustomerInserted(Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.CUSTOMER_INSERT, "Inserted customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditCustomerUpdated(Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.CUSTOMER_UPDATE, "Updated customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditCustomerActive(Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.CUSTOMER_ACTIVE, "Changed to Active status for customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditCustomerNotActive(Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.CUSTOMER_ACTIVE, "Changed to NOT Active status for customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditAddPurchaseForCustomer(Integer purchaseId, Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PURCHASE_INSERT, "Inserted purchase "+purchaseId+" for customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditAddPaymentForCustomer(Integer paymentId, Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.PAYMENT_INSERT, "Inserted payment "+paymentId+" for customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditAddCreditForCustomer(Integer creditId, Integer customerId) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.CREDIT_INSERT, "Inserted credit "+creditId+" for customer " + customerId);
		insert(audit);
	}

	@Override
	public void auditDiscountCard(Integer percentage, Integer totCards) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.DISCOUNT_CARD_INSERT, "Inserted "+totCards+" DiscountCards with discount "+percentage+"%");
		insert(audit);
	}
	
	@Override
	public void auditCouponStatusChange(Coupon coupon) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.COUPON_STATUS_CHANGE, "Coupon "+coupon.getCode()+" changed status to "+coupon.getStatus());
		insert(audit);
	}
	
	@Override
	public void auditErrorCouponStatusChange(Coupon coupon) {
		AuditTrail audit = getPreCompiledAuditTrail(AuditTrail.COUPON_ERROR_STATUS_CHANGE, "Error: Coupon "+coupon.getCode()+" changing status to "+coupon.getStatus());
		insert(audit);
	}
	
    private AuditTrail getPreCompiledAuditTrail(String username, String roles, String operationName, String operationDesc) {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setUsername(username);
        auditTrail.setRoles(roles);
        auditTrail.setOperationName(operationName);
        auditTrail.setOperationDesc(operationDesc);
        auditTrail.setOperationTime(new Timestamp(System.currentTimeMillis()));
        return auditTrail;
    }
    
    private AuditTrail getPreCompiledAuditTrail(String operationName, String operationDesc) {
        String username = null;
        String roles = null;
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            username = user.getUsername();
            roles = ZUtils.getUserRolesAsString();
        }
        return getPreCompiledAuditTrail(username, roles, operationName, operationDesc);
    }

	@Override
	public List<AuditTrail> search(SearchAuditTrailBean sb) { 
		return auditTrailDAO.search(sb);
	}

}
