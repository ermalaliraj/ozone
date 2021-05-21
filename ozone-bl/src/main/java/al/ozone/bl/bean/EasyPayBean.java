package al.ozone.bl.bean;

import java.util.Date;

public class EasyPayBean {

	private String txnId;
	private String orderId;
	private String merchantUsername;
	private int responseCode;
	private String txnStatus;
	private String originalResponse;
	private int amount;
	private Date date;
	private double fee;
	
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMerchantUsername() {
		return merchantUsername;
	}
	public void setMerchantUsername(String merchantUsername) {
		this.merchantUsername = merchantUsername;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getOriginalResponse() {
		return originalResponse;
	}
	public void setOriginalResponse(String originalResponse) {
		this.originalResponse = originalResponse;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	
}
