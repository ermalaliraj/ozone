package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchDealPurchasesBean {

	private String partnerName;
	private Integer dealId;
	private String dealTitle;
	private String agentName;
	private Integer agentCommission;
	private Date insertedFrom;
	private Date insertedTo;
	private Date startFrom;
	private Date startTo;
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
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

	public Date getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(Date startFrom) {
		this.startFrom = startFrom;
	}

	public Date getStartTo() {
		return startTo;
	}

	public void setStartTo(Date startTo) {
		this.startTo = startTo;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setAgentCommission(Integer commission) {
		this.agentCommission = commission;
	}

	public Integer getAgentCommission() {
		return agentCommission;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("partnerName", partnerName)
        	.append("dealId", dealId)
        	.append("dealTitle", dealTitle)
        	.append("agentName", agentName)
        	.append("agentCommission", agentCommission)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.append("startFrom", startFrom)
        	.append("startTo", startTo)
        	.append("purchaseFrom", purchaseFrom)
        	.append("purchaseTo", purchaseTo)
        	.append("categoryId", categoryId)
        	.toString();
    }
}
