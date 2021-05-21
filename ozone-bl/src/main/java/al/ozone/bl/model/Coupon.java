package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import al.ozone.bl.utils.ZUtils;

public class Coupon implements Serializable{

	private static final long serialVersionUID = 6046329686185441126L;

	public static final String STATUS_NOT_USED = "N";
	public static final String STATUS_USED = "U";
	public static final String STATUS_EXPIRED = "E";
	public static final String STATUS_RETURNED = "R";
	
	private String code;
	private Integer purchaseId;
	private String securityCode;
	private Date from;
	private Date to;
	//private byte[] pdfData;
	private String htmlLink;
	private String status;
	private Date lastStatusChange;
	private Integer dealId;	
	private Integer dealChoiceNr;
	private String titleChoice;
	private String customerEmail;
	private String note;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Integer getDealChoiceNr() {
		return dealChoiceNr;
	}
	public void setDealChoiceNr(Integer dealChoiceNr) {
		this.dealChoiceNr = dealChoiceNr;
	}
	
	public String getStatusDescription(){
		String rv = "Wrong coupon Status";
		
		if(status.equals(STATUS_NOT_USED)){
			rv = "Kupon i pa perdorur akoma";
		}else if(status.equals(STATUS_USED)){
			rv = "Kupon eshte perdorur";
		}else if(status.equals(STATUS_EXPIRED)){
			rv = "Kupon i skaduar";
		}else if(status.equals(STATUS_RETURNED)){
			rv = "Kupon i rimbursuar";
		}
		
		return rv;
	}
	public String getColorStatus(){
		String rv = null;
		if(status.equals(STATUS_USED) ||
			status.equals(STATUS_EXPIRED) ||
			status.equals(STATUS_RETURNED)){
			rv = "TableUsedCouponTr";
		}
		return rv;
	}
	public Date getLastStatusChange() {
		return lastStatusChange;
	}
	public void setLastStatusChange(Date lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}
	public String getHtmlLink() {
		return htmlLink;
	}
	public void setHtmlLink(String htmlLink) {
		this.htmlLink = htmlLink;
	}
	public Integer getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public String getTitleChoice() {
		return titleChoice;
	}
	public void setTitleChoice(String titleChoice) {
		this.titleChoice = titleChoice;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * True if the coupun is not used and expirationDate is a the future date
	 * @return
	 */
	public boolean isValidCoupon(){
		if(ZUtils.isFutureOrTodayDate(to) && status.equals(STATUS_NOT_USED)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean equals(final Object other) {
        if (!(other instanceof Coupon))
            return false;
        Coupon castOther = (Coupon) other;
        return new EqualsBuilder().append(code, castOther.code)
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(code)
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("code", code)
        	.append("securityCode", securityCode)
        	.append("customerEmail", customerEmail)
        	.append("purchaseId", purchaseId)
        	.append("from", from)
			.append("to", to)
			.append("htmlLink", htmlLink)
			.append("status", status)
			.append("lastStatusChange", lastStatusChange)
			.append("dealId", dealId)
			.append("dealChoiceNr", dealChoiceNr)
			.append("titleChoice", titleChoice)
			.append("note", note)
			.toString();
    }	
}
