package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PaymentCash extends Payment implements Serializable{

	private static final long serialVersionUID = -3053147355025474986L;
	public static final String DETAIL_PAGE_NAME = "paymentCashDetailDlg.xhtml";

	private String sellerFullName;
	private String buyerFullName;
	private String buyerTel;
	private String customerName;
	private String customerSurname;
	private String customerEmail;
	private String note;
	
	public PaymentCash(){
		paymentType = Payment.TYPE_CASH;
	}
	
	@Override
	public String getPaymentType() {	
		return paymentType;
	}

	public String getSellerFullName() {
		return sellerFullName;
	}
	public void setSellerFullName(String sellerFullName) {
		this.sellerFullName = sellerFullName;
	}
	public String getBuyerFullName() {
		return buyerFullName;
	}
	public void setBuyerFullName(String buyerFullName) {
		this.buyerFullName = buyerFullName;
	}
	public String getBuyerTel() {
		return buyerTel;
	}
	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerSurname() {
		return customerSurname;
	}
	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("sellerFullName", sellerFullName)
        	.append("buyerFullName", buyerFullName)
        	.append("buyerTel", buyerTel)
        	.append("customerName", customerName)
        	.append("customerSurname", customerSurname)
        	.append("customerEmail", customerEmail)
        	.append("note", note) 	
        	.toString();
    }
	
	public String getDetailPageName() {
		return DETAIL_PAGE_NAME;
	}
}
