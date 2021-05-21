package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchPaymentBean;
import al.ozone.bl.model.Payment;

public interface PaymentDAO {

	public Payment get(Integer id);
	
	public void insert (Payment credit);

	public List<Payment> search(SearchPaymentBean sb);

	public Payment getPaymentForPurchase(Integer purchaseId);

	public void changePaymentStatus(Payment payment);
	
	public void updatePaymentNote(Integer paymentId, String note);

}