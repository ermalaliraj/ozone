package al.ozone.bl.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PaymentPayPal extends Payment implements Serializable{

	private static final long serialVersionUID = 208367666966278154L;
	public static final String DETAIL_PAGE_NAME = "paymentPayPalDetailDlg.xhtml";

	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_REVERSED = "Reversed";	
	public static final String STATUS_REFUNDED = "Refunded";
	
	private String txnId;
	private String invoice;
	private String payerEmail;
	private String receiverEmail;
	private String amountEUR;
	private String currency;
	private String exchangeRate;
	private String paymentStatus;
	private String paymentDate;
	private String payerStatus;
	private String parentTxnId; // parent_txn_id
	private String mcFee; //"mc_fee"
	private String subscriberId; //"subscr_id":
	private String firstName; //first_name
	private String lastName; //last_name
	private String queryString;
	private String responseString;
	
	public PaymentPayPal(){
		paymentType = Payment.TYPE_PAYPAL;
	}

	public PaymentPayPal(HttpServletRequest request) {
		paymentType = Payment.TYPE_PAYPAL;
		
		txnId = request.getParameter("txn_id");
		invoice = request.getParameter("invoice"); //invoice is our OrderId		
		payerEmail = request.getParameter("payer_email");
		receiverEmail = request.getParameter("receiver_email");
		amountEUR = request.getParameter("mc_gross");
		currency = request.getParameter("mc_currency");		
		//exchangeRate
		paymentStatus = request.getParameter("payment_status");
		paymentDate = request.getParameter("payment_date");
		payerStatus = request.getParameter("payer_status");			
		parentTxnId = request.getParameter("parent_txn_id");
		mcFee = request.getParameter("mc_fee");
		subscriberId = request.getParameter("subscr_id");
		firstName = request.getParameter("first_name");
		lastName = request.getParameter("last_name");		
		//queryString
		//responseString
	}

	@Override
	public String getPaymentType() {	
		return paymentType;
	}
	public String getDetailPageName() {
		return DETAIL_PAGE_NAME;
	}
	
	
	public String getPayerEmail() {
		return payerEmail;
	}
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getAmountEUR() {
		return amountEUR;
	}
	public void setAmountEUR(String amountEUR) {
		this.amountEUR = amountEUR;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
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
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("payerEmail", payerEmail)
        	.append("receiverEmail", receiverEmail)
        	.append("amountEUR", amountEUR)
        	.append("currency", currency)
        	.append("exchangeRate", exchangeRate)
        	.append("txnId", txnId)
        	.append("invoice", invoice)
        	.append("paymentStatus", paymentStatus)
        	.append("paymentDate", paymentDate)
        	.append("payerStatus", payerStatus)
        	.append("parentTxnId", parentTxnId)
        	.append("mcFee", mcFee)
        	.append("subscriberId", subscriberId)
        	.append("firstName", firstName)
        	.append("lastName", lastName)
        	.append("responseString", responseString)
        	.append("queryString", queryString)        	
        	.toString();
    }	
}
