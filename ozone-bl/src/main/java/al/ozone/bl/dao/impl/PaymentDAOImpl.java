package al.ozone.bl.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchPaymentBean;
import al.ozone.bl.dao.PaymentDAO;
import al.ozone.bl.model.Payment;

public class PaymentDAOImpl extends GenericDAOImpl<Payment, Integer> implements PaymentDAO {

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(PaymentDAOImpl.class);

	public PaymentDAOImpl(){

	}

	public PaymentDAOImpl(Class<Payment> persistentClass) {
//		super(persistentClass);
	}
	
	public void insert(Payment record) {
//		record.setOperationDate(new Date());
//
//		//insert in parent table
//		getSqlSession().insert("PAYMENT.insert", record);
//		// after the insert record has the ID valorized.
//
//		//insert in child table
//		if(record.getPaymentType().equals(Payment.TYPE_CASH)){
//			//logger.debug("Insertig CASH payment in DB");
//			getSqlSession().insert("PAYMENT.insertCashPay", record);
//		}else if(record.getPaymentType().equals(Payment.TYPE_PAYPAL)){
//			//logger.debug("Insertig PAYPAL payment in DB");
//			getSqlSession().insert("PAYMENT.insertPayPalPay", record);
//		}else if(record.getPaymentType().equals(Payment.TYPE_EASY_PAY)){
//			logger.debug("Insertig EASY_PAY payment in DB");
//			getSqlSession().insert("PAYMENT.insertPayEasyPay", record);
//		}else if(record.getPaymentType().equals(Payment.TYPE_BANK)){
//			logger.debug("Insertig BANK payment in DB");
//			getSqlSession().insert("PAYMENT.insertPayBank", record);
//		}else if(record.getPaymentType().equals(Payment.TYPE_AMERICAN_EXPRESS)){
//			logger.debug("Insertig AMERICAN_EXPRESS payment in DB");
//		}
	
	}
	
	public Payment get(Integer id) {
//		Payment p = (Payment)getSqlSession().selectOne("PAYMENT.get", id);
//		return p;
		return null;
	}

	@Override
	public List<Payment> search(SearchPaymentBean sb) {
//		@SuppressWarnings("unchecked")
//		List<Payment> list = getSqlSession().selectList("PAYMENT.search", sb);
//        return list;
		return null;
	}

	@Override
	public void changePaymentStatus(Payment payment) {
//		getSqlSession().update("PAYMENT.changePaymentStatus", payment);
	}

	@Override
	public Payment getPaymentForPurchase(Integer purchaseId) {
//		Payment p = (Payment)getSqlSession().selectOne("PAYMENT.getPaymentForPurchase", purchaseId);
//		return p;
		return null;
	}
	
	public void updatePaymentNote(Integer paymentId, String note) {
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("pId", paymentId);
//        param.put("note", note);
//		getSqlSession().update("PAYMENT.updatePaymentNote", param);
	}

}
