package al.ozone.bl.manager;

import java.util.List;

import al.ozone.bl.bean.SearchCustomerLog;
import al.ozone.bl.model.CustomerLog;

public interface CustomerLogManager {
	
	public CustomerLog get(Integer id);	
	
	public void insert (CustomerLog log);
	
	public List<CustomerLog> search(SearchCustomerLog scl);

	public void auditErrorOnOrder(Integer cusId, String string);
	
	public void auditErrorOnPurchase(Integer cusId, String msg);

	public void auditErrorOnLogin(Integer cusId, String msg);
	
	public void auditErrorOnRegistration(Integer cusId, String msg);

	public void auditErrorOnInviteFriend(Integer cusId, String msg);

	public void auditErrorOnResetPwd(Integer cusId, String msg);
	
	public void auditErrorOnChangePwd(Integer cusId, String msg);

	public void auditOnRefundPayPal(Integer cusId, String string);

	public void auditOnErrorPayPal(Integer cusId, String string);

	public void auditOnReversePayPal(Integer cusId, String string);

	public void auditOnInvalidPayPal(Integer cusId, String string);

	public void auditOnErrorEasyPay(Integer cusId, String string);

	public void auditErrorOnChangeCouponStatus(Integer cusId, String string);
	
}