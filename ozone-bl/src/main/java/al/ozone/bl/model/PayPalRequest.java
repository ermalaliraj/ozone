package al.ozone.bl.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PayPalRequest implements Serializable{
	
	private static final long serialVersionUID = -746766164284091950L;
	
	private String txnId;
	private String invoice;
	private String receiverEmail;
	private String payerEmail;
	private String paymentStatus;
	private String paymentDate;
	private String payerStatus;
	private String paymentAmountEUR;
	private String paymentCurrency;
	private String parentTxnId; // parent_txn_id
	private String mcFee; //"mc_fee"
	private String subscriberId; //"subscr_id":
	private String firstName; //first_name
	private String lastName; //last_name

	
	public PayPalRequest(HttpServletRequest request) {
		
		txnId = request.getParameter("txn_id");
		invoice = request.getParameter("invoice"); //invoice is our OrderId
		receiverEmail = request.getParameter("receiver_email");
		payerEmail = request.getParameter("payer_email");
		paymentStatus = request.getParameter("payment_status");
		paymentDate = request.getParameter("payment_date");
		payerStatus = request.getParameter("payer_status");			
		paymentAmountEUR = request.getParameter("mc_gross");
		paymentCurrency = request.getParameter("mc_currency");		
		parentTxnId = request.getParameter("parent_txn_id");
		mcFee = request.getParameter("mc_fee");
		subscriberId = request.getParameter("subscr_id");
		firstName = request.getParameter("first_name");
		lastName = request.getParameter("last_name");
//					logger.debug("itemName: "+itemName);
//					logger.debug("itemNumber: "+itemNumber);
//					logger.debug("paymentStatus: "+paymentStatus);
//					logger.debug("paymentAmountEUR: "+paymentAmountEUR);
//					logger.debug("paymentCurrency: "+paymentCurrency);
//					logger.debug("txnId: "+txnId);
//					logger.debug("receiverEmail: "+receiverEmail);
//					logger.debug("payerEmail: "+payerEmail);
//					logger.debug("invoice:"+invoice);
//					logger.debug("mc_gross:"+request.getParameter("mc_gross"));
//					logger.debug("invoice:"+request.getParameter("invoice"));
//					logger.debug("protection_eligibility:"+request.getParameter("protection_eligibility"));
//					logger.debug("address_status:"+request.getParameter("address_status"));
//					logger.debug("payer_id:"+request.getParameter("payer_id"));
//					logger.debug("tax:"+request.getParameter("tax"));
//					logger.debug("address_street:"+request.getParameter("address_street"));
//					logger.debug("payment_date:"+request.getParameter("payment_date"));			
//					logger.debug("payment_status:"+request.getParameter("payment_status"));
//					logger.debug("charset:"+request.getParameter("charset"));
//					logger.debug("address_:"+request.getParameter("address_"));
//					logger.debug("zip:"+request.getParameter("zip"));
//					logger.debug("first_name:"+request.getParameter("first_name"));
//					logger.debug("mc_fee:"+request.getParameter("mc_fee"));
//					logger.debug("address_country_code:"+request.getParameter("address_country_code"));
//					logger.debug("address_name:"+request.getParameter("address_name"));
//					logger.debug("custom:"+request.getParameter("custom"));
//					logger.debug("payer_status:"+request.getParameter("payer_status"));
//					logger.debug("business:"+request.getParameter("business"));
//					logger.debug("address_country:"+request.getParameter("address_country"));
//					logger.debug("address_city:"+request.getParameter("address_city"));
//					logger.debug("quantity:"+request.getParameter("quantity"));
//					logger.debug("payer_email:"+request.getParameter("payer_email"));
//					logger.debug("txn_id:"+request.getParameter("txn_id"));
//					logger.debug("payment_type:"+request.getParameter("payment_type"));
//					logger.debug("last_name:"+request.getParameter("last_name"));
//					logger.debug("address_state:"+request.getParameter("address_state"));
//					logger.debug("receiver_email:"+request.getParameter("receiver_email"));
//					logger.debug("payment_fee:"+request.getParameter("payment_fee"));
//					logger.debug("receiver_id:"+request.getParameter("receiver_id"));
//					logger.debug("txn_type:"+request.getParameter("txn_type"));
//					logger.debug("item_name:"+request.getParameter("item_name"));
//					logger.debug("mc_currency:"+request.getParameter("mc_currency"));
//					logger.debug("item_number:"+request.getParameter("item_number"));
//					logger.debug("residence_country:"+request.getParameter("residence_country"));
//					logger.debug("handling_amount:"+request.getParameter("handling_amount"));
//					logger.debug("transaction_subject:"+request.getParameter("transaction_subject"));
//					logger.debug("payment_gross:"+request.getParameter("payment_gross"));
//					logger.debug("shipping:"+request.getParameter("shipping"));
	}

	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getPayerEmail() {
		return payerEmail;
	}
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPayerStatus() {
		return payerStatus;
	}
	public void setPayerStatus(String payerStatus) {
		this.payerStatus = payerStatus;
	}
	public String getPaymentAmountEUR() {
		return paymentAmountEUR;
	}
	public void setPaymentAmountEUR(String paymentAmountEUR) {
		this.paymentAmountEUR = paymentAmountEUR;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getParentTxnId() {
		return parentTxnId;
	}
	public void setParentTxnId(String parentTxnId) {
		this.parentTxnId = parentTxnId;
	}
	public String getMcFee() {
		return mcFee;
	}
	public void setMcFee(String mcFee) {
		this.mcFee = mcFee;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof PayPalRequest))
            return false;
        PayPalRequest ppr = (PayPalRequest) other;
      
        return new EqualsBuilder()
        		.append(txnId, ppr.txnId)
        		.isEquals();
    }
	
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(txnId)
        		.toHashCode();
    }
    
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("txnId", txnId)
        	.toString();
    }
}
