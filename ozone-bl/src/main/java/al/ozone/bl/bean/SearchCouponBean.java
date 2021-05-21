package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchCouponBean extends AbstractSearchBean {

	private Integer purchaseId;
	private Integer contractId;
	private String dealTitle;
	private String couponCode;
	private String couponSecCode;
	private String email;
	private String status;
	private boolean noFake;
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchCouponBean))
            return false;
        SearchCouponBean castOther = (SearchCouponBean) other;
        
        return new EqualsBuilder()
				.append(purchaseId, castOther.purchaseId)
        		.append(contractId, castOther.contractId)
        		.append(dealTitle, castOther.dealTitle)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(purchaseId)
        		.append(contractId)
        		.append(dealTitle)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("purchaseId", purchaseId)
        	.append("contractId", contractId)
        	.append("dealTitle",dealTitle)
        	.append("couponCode", couponCode)
        	.append("couponSecCode", couponSecCode)
        	.append("email", email)
        	.append("status", status)
        	.append("noFake", noFake)
        	.toString();
    }
	public Integer getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public String getDealTitle() {
		return dealTitle;
	}
	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}
	public String getCouponSecCode() {
		return couponSecCode;
	}
	public void setCouponSecCode(String couponSecCode) {
		this.couponSecCode = couponSecCode;
	}
	public boolean isNoFake() {
		return noFake;
	}
	public void setNoFake(boolean noFake) {
		this.noFake = noFake;
	}
	
}
