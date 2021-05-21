package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchDealReportBean {

	private String partnerName;
	private Integer dealId;
	private String dealTitle;
	private Boolean isExpired;
	private Boolean isAccounted;
	private Date insertedFrom;
	private Date insertedTo;
	private Date closedFrom;
	private Date closedTo;
	private Date purchaseFrom;
	private Date purchaseTo;
	private Integer categoryId;
	
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public String getDealTitle() {
		return dealTitle;
	}
	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}
	
	public Boolean getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	public Boolean getIsAccounted() {
		return isAccounted;
	}
	public void setIsAccounted(Boolean isAccounted) {
		this.isAccounted = isAccounted;
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
	public Date getClosedFrom() {
		return closedFrom;
	}
	public void setClosedFrom(Date closedFrom) {
		this.closedFrom = closedFrom;
	}
	public Date getClosedTo() {
		return closedTo;
	}
	public void setClosedTo(Date closedTo) {
		this.closedTo = closedTo;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Date getPurchaseFrom() {
		return purchaseFrom;
	}
	public void setPurchaseFrom(Date purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}
	public Date getPurchaseTo() {
		return purchaseTo;
	}
	public void setPurchaseTo(Date purchaseTo) {
		this.purchaseTo = purchaseTo;
	}
	public boolean equals(final Object other) {
        if (!(other instanceof SearchDealReportBean))
            return false;
        SearchDealReportBean castOther = (SearchDealReportBean) other;
        
        return new EqualsBuilder()
        		.append(partnerName, castOther.partnerName)
				.append(dealId, castOther.dealId)
				.append(dealTitle, castOther.dealTitle)
				.append(isExpired, castOther.isExpired)
				.append(isAccounted, castOther.isAccounted)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(partnerName)
        		.append(dealId)
				.append(dealTitle)
				.append(isExpired)
				.append(isAccounted)
				.append(insertedFrom)
				.append(insertedTo)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("partnerName", partnerName)
        	.append("dealId", dealId)
        	.append("dealTitle", dealTitle)
        	.append("isExpired", isExpired)
        	.append("isAccounted", isAccounted)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.append("closedFrom", closedFrom)
        	.append("closedTo", closedTo)
        	.append("purchaseFrom", purchaseFrom)
        	.append("purchaseTo", purchaseTo)
        	.append("categoryId", categoryId)
        	.toString();
    }
}
