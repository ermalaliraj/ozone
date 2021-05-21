package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import al.ozone.bl.bean.EasyPayBean;

public class PaymentEasyPay extends Payment implements Serializable{

	private static final long serialVersionUID = 208367666966278154L;
	public static final String DETAIL_PAGE_NAME = "paymentEasyPayDetailDlg.xhtml";
	
	private String txnId;
	private String orderId;
	private String merchantUsername; //
	private int responseCode;
	private String txnStatus;
	private String originalResponse; //query string setted on IPN
	private int amount;
	private Date date; //
	private double fee;
	
	public PaymentEasyPay(){
		paymentType = Payment.TYPE_EASY_PAY;
	}

	public PaymentEasyPay(EasyPayBean easyPay) {
		paymentType = Payment.TYPE_EASY_PAY;
		
		txnId = easyPay.getTxnId();
		orderId = easyPay.getOrderId();
		responseCode = easyPay.getResponseCode();
		txnStatus = easyPay.getTxnStatus();
		amount = easyPay.getAmount();
		fee = easyPay.getFee();
		
		switch (responseCode) {
	        case 400:
	        	txnStatus = "Success";
	            break;
	        case 401:
	        	txnStatus = "Invalid user name or reference number";
	            break;
	        case 402:
	        	txnStatus = "Under process";
	            break;
	        case 403:
	        	txnStatus = "Declined";
	            break;
	        case 404:
	        	txnStatus = "Killed";
	            break;
	        case 405:
	        	txnStatus = "Pending";
	            break;
	        case 406:
	        	txnStatus = "Empty value";
	            break;
	        case 407:
	        	txnStatus = "Incorrect mobile no";
	            break;
	        case 408:
	        	txnStatus = "Invalid IP";
	            break;
	        case 471:
	        	txnStatus= "Invalid amount";
	            break;
	        case 411:
	        	txnStatus = "Transaction exist";
	            break;
	        case 500:
	        	txnStatus = "Timeout";
	            break;
	        case 305:
	        	txnStatus = "User not registered";
	            break;
	        case 304:
	        	txnStatus = "Registration pending";
	            break;
	        case 302:
	        	txnStatus = "Registered User";
	            break;
	        default:
	        	txnStatus = "Unknown 'txnStatus'";
	    }
	}
	
//	public PaymentEasyPay(String confirmation) {
//		String[] params = confirmation.split("|");
//		if (params.length > 0){
//			String responseCodeS = params[0];
//			responseCode = Integer.parseInt(responseCodeS);
//			txnId = params[1];
//			orderId = params[2];
//			amount = Integer.parseInt(params[3]);
//			
//			switch (responseCode) {
//                case 400:
//                	txnStatus = "Success";
//                    break;
//                case 401:
//                	txnStatus = "Invalid user name or reference number";
//                    break;
//                case 402:
//                	txnStatus = "Under process";
//                    break;
//                case 403:
//                	txnStatus = "Declined";
//                    break;
//                case 404:
//                	txnStatus = "Killed";
//                    break;
//                case 405:
//                	txnStatus = "Pending";
//                    break;
//                case 406:
//                	txnStatus = "Empty value";
//                    break;
//                case 407:
//                	txnStatus = "Incorrect mobile no";
//                    break;
//                case 408:
//                	txnStatus = "Invalid IP";
//                    break;
//                case 471:
//                	txnStatus= "Invalid amount";
//                    break;
//                case 411:
//                	txnStatus = "Transaction exist";
//                    break;
//                case 500:
//                	txnStatus = "Timeout";
//                    break;
//                case 305:
//                	txnStatus = "User not registered";
//                    break;
//                case 304:
//                	txnStatus = "Registration pending";
//                    break;
//                case 302:
//                	txnStatus = "Registered User";
//                    break;
//            }
//		}
//	}

	@Override
	public String getPaymentType() {	
		return paymentType;
	}
	public String getDetailPageName() {
		return DETAIL_PAGE_NAME;
	}
	
	
	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())        	
        	.append("txnId", txnId)
        	.append("orderId", orderId)
        	.append("merchantUsername", merchantUsername)	
        	.append("responseCode", responseCode)	
        	.append("txnStatus", txnStatus)	
        	.append("txnStatus", txnStatus)	
        	.append("originalResponse", originalResponse)	
        	.append("amount", amount)	
        	.append("date", date)	
        	.append("fee", fee)	
        	.toString();
    }	
}
