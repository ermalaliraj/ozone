package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchOrderBean {

	private Integer orderId;
	private Integer dealId;
	private String dealChoiceTitle;
	private String customer;
	private String type;
	private Date from;
	private Date to;
	private Boolean withPurchase;
	private Boolean contacted;
	private Boolean canceled;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public String getDealChoiceTitle() {
		return dealChoiceTitle;
	}

	public void setDealChoiceTitle(String dealChoiceTitle) {
		this.dealChoiceTitle = dealChoiceTitle;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Boolean getWithPurchase() {
		return withPurchase;
	}

	public void setWithPurchase(Boolean withPurchase) {
		this.withPurchase = withPurchase;
	}

	public Boolean getContacted() {
		return contacted;
	}

	public void setContacted(Boolean contacted) {
		this.contacted = contacted;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof SearchOrderBean))
            return false;
        SearchOrderBean castOther = (SearchOrderBean) other;
        
        return new EqualsBuilder()
        		.append(orderId, castOther.orderId)
        		.isEquals();
    }
	
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(orderId)
        		.toHashCode();
    }
	
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("orderId", orderId)
        	.append("dealId", dealId)
        	.append("dealChoiceTitle", dealChoiceTitle)
        	.append("customer", customer)
        	.append("type", type)
        	.append("from", from)
        	.append("to", to)
        	.append("withPurchase", withPurchase)
        	.append("contacted", contacted)
        	.append("canceled", canceled)
        	.toString();
    }

}
