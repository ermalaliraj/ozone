package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchCustomerLog;
import al.ozone.bl.dao.CustomerLogDAO;
import al.ozone.bl.manager.CustomerLogManager;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.CustomerLog;

public class CustomerLogManagerImpl implements CustomerLogManager, Serializable  {

	private static final long serialVersionUID = -632760940697527305L;
	protected static final transient Log logger = LogFactory.getLog(CustomerLogManagerImpl.class);

	private CustomerLogDAO customerLogDAO;
	
	public CustomerLogDAO getCustomerLogDAO() {
		return customerLogDAO;
	}
	public void setCustomerLogDAO(CustomerLogDAO customerLogDAO) {
		this.customerLogDAO = customerLogDAO;
	}

	@Override
	public CustomerLog get(Integer id) {
		return customerLogDAO.get(id);
	}
	
	@Override
	public void insert(CustomerLog customer) {
		customerLogDAO.insert(customer);
	}
	
	@Override
	public List<CustomerLog> search(SearchCustomerLog scl) {
		return customerLogDAO.search(scl);
	}
	
	@Override
	public void auditErrorOnOrder(Integer id, String msg) {
		auditError(id, msg, CustomerLog.ORDER);
	}   
	
	@Override
	public void auditErrorOnPurchase(Integer id, String msg) {
		auditError(id, msg, CustomerLog.PURCHASE);
	}
	
	@Override
	public void auditErrorOnLogin(Integer id, String msg) {
		auditError(id, msg, CustomerLog.LOGIN);
	}
	
	@Override
	public void auditErrorOnRegistration(Integer id, String msg) {
		auditError(id, msg, CustomerLog.REGISTRATION);
	}
	
	@Override
	public void auditErrorOnInviteFriend(Integer id, String msg) {
		auditError(id, msg, CustomerLog.INVITE);
	}
	
	@Override
	public void auditErrorOnResetPwd(Integer id, String msg) {
		auditError(id, msg, CustomerLog.RESET_PASSWORD);
	}
	
	@Override
	public void auditErrorOnChangePwd(Integer id, String msg) {
		auditError(id, msg, CustomerLog.CHANGE_PASSWORD);
	}
	
	@Override
	public void auditOnRefundPayPal(Integer id, String msg) {
		auditError(id, msg, CustomerLog.PAYPAL_REFUND);
	} 
	
	@Override
	public void auditOnErrorPayPal(Integer id, String msg) {
		auditError(id, msg, CustomerLog.PAYPAL_ERROR);
	}
	
	@Override
	public void auditOnReversePayPal(Integer id, String msg) {
		auditError(id, msg, CustomerLog.PAYPAL_REVERSE);
	}
	
	@Override
	public void auditOnInvalidPayPal(Integer id, String msg) {
		auditError(id, msg, CustomerLog.PAYPAL_INVALID);
	}
	
	@Override
	public void auditOnErrorEasyPay(Integer id, String msg) {
		auditError(id, msg, CustomerLog.EASYPAY_ERROR);
	}
	
	@Override
	public void auditErrorOnChangeCouponStatus(Integer cusId, String msg) {
		auditError(cusId, msg, CustomerLog.COUPON_CHANGE_STATUS_ERROR);
	}
	
	private void auditError(Integer id, String msg, String section) {
		CustomerLog log = new CustomerLog();
		log.setCustomer(new Customer(id));
		log.setOpType(section);
		log.setErrorMsg(msg);
		log.setOpDate(new Date());
		customerLogDAO.insert(log);
	}


}

