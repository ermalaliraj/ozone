package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.SearchPaymentBean;
import al.ozone.bl.model.Payment;

public interface PaymentManager {

	public Payment get(Integer id);
	
	@Secured( { "ROLE_ADMIN" })
	public void insert (Payment credit);

	@Secured( { "ROLE_ADMIN" })
	public List<Payment> search(SearchPaymentBean sb);

	public Payment getPaymentForPurchase(Integer purchaseId);

}