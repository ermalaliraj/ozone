package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchPaymentBean {

	private Integer paymentId;
	private Integer purchaseId;
	private String partnerName;
	private String dealTitle;
	private Date insertedFrom;
	private Date insertedTo;
	private String paymentType;
	
	public Integer getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getDealTitle() {
		return dealTitle;
	}
	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
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
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchPaymentBean))
            return false;
        SearchPaymentBean castOther = (SearchPaymentBean) other;
        
        return new EqualsBuilder()
        		.append(paymentId, castOther.paymentId)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(paymentId)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("paymentId", paymentId)
        	.append("purchaseId", purchaseId)
        	.append("paymentType", paymentType)
        	.append("partnerName", partnerName)
        	.append("dealTitle", dealTitle)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.toString();
    }
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	

}
