package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchDealBean {

	private String partnerName;
	private Integer dealId;
	private String dealTitle;
	private Boolean isApprovedForPub;
	private String status;
	private Date insertedFrom;
	private Date insertedTo;
	private Date startedFrom;
	private Date startedTo;
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
	public Boolean getIsApprovedForPub() {
		return isApprovedForPub;
	}
	public void setIsApprovedForPub(Boolean isApprovedForPub) {
		this.isApprovedForPub = isApprovedForPub;
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
	public Date getStartedFrom() {
		return startedFrom;
	}
	public void setStartedFrom(Date startedFrom) {
		this.startedFrom = startedFrom;
	}
	public Date getStartedTo() {
		return startedTo;
	}
	public void setStartedTo(Date startedTo) {
		this.startedTo = startedTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean equals(final Object other) {
        if (!(other instanceof SearchDealBean))
            return false;
        SearchDealBean castOther = (SearchDealBean) other;
        
        return new EqualsBuilder()
        		.append(partnerName, castOther.partnerName)
				.append(dealId, castOther.dealId)
				.append(dealTitle, castOther.dealTitle)
				.append(isApprovedForPub, castOther.isApprovedForPub)
				.append(insertedFrom, castOther.insertedFrom)
				.append(insertedTo, castOther.insertedTo)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(partnerName)
        		.append(dealId)
				.append(dealTitle)
				.append(isApprovedForPub)
				.append(insertedFrom)
				.append(insertedTo)
				.append(startedFrom)
				.append(startedTo)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("partnerName", partnerName)
        	.append("dealId", dealId)
        	.append("dealTitle", dealTitle)
        	.append("isApprovedForPub", isApprovedForPub)
        	.append("status", status)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.append("startedFrom", startedFrom)
			.append("startedTo", startedTo)
			.append("categoryId", categoryId)
        	.toString();
    }
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
}
