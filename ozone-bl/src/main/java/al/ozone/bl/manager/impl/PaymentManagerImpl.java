package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import al.ozone.bl.bean.SearchPaymentBean;
import al.ozone.bl.dao.PaymentDAO;
import al.ozone.bl.manager.PaymentManager;
import al.ozone.bl.model.Payment;

public class PaymentManagerImpl implements PaymentManager, Serializable {

	private static final long serialVersionUID = 4080908274655244818L;
	private PaymentDAO paymentDAO;

	public PaymentDAO getPaymentDAO() {
		return paymentDAO;
	}
	
	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	@Override
	public Payment get(Integer id) {
		return paymentDAO.get(id);
	}

	@Override
	public void insert(Payment record) {
		paymentDAO.insert(record);
	}
	
	@Override
	public List<Payment> search(SearchPaymentBean sb) {
		return paymentDAO.search(sb);
	}

	@Override
	public Payment getPaymentForPurchase(Integer purchaseId) {
		return paymentDAO.getPaymentForPurchase(purchaseId);
	}
}
