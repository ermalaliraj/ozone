package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchPurchaseBean {

	private Integer purId;
	private Integer dealId;
	private String cusEmail;
	private String dealChoiceTitle;
	private Date insertedFrom;
	private Date insertedTo;
	private String paymentType;
	private Integer orderId;
	private boolean noFake;
	private Boolean confirmed;	
	
	public Integer getPurId() {
		return purId;
	}
	public void setPurId(Integer purId) {
		this.purId = purId;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public String getCusEmail() {
		return cusEmail;
	}
	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}
	public String getDealChoiceTitle() {
		return dealChoiceTitle;
	}
	public void setDealChoiceTitle(String dealChoiceTitle) {
		this.dealChoiceTitle = dealChoiceTitle;
	}
	public Date getInsertedFrom() {
		return insertedFrom;
	}
	public void setInsertedFrom(Date insertedFrom) {
		this.insertedFrom = insertedFrom;
	}
	public Date getInsertedTo() {
		return insertedTo;
	}
	public void setInsertedTo(Date insertedTo) {
		this.insertedTo = insertedTo;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public void setNoFake(Boolean isNoFake) {
		this.noFake = isNoFake;
	}
	public boolean isNoFake() {
		return noFake;
	}
	public void setNoFake(boolean noFake) {
		this.noFake = noFake;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public boolean equals(final Object other) {
        if (!(other instanceof SearchPurchaseBean))
            return false;
        SearchPurchaseBean castOther = (SearchPurchaseBean) other;
        
        return new EqualsBuilder()
        		.append(purId, castOther.purId)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(purId)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("purId", purId)
        	.append("dealId", dealId)
        	.append("cusEmail", cusEmail)
        	.append("dealTitle", dealChoiceTitle)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.append("paymentType", paymentType)
        	.append("orderId", orderId)
        	.append("confirmed", confirmed)
        	.append("noFake", noFake)
        	.toString();
    }

}
