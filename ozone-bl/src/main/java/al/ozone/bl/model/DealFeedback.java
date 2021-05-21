package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DealFeedback implements Serializable {

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(DealFeedback.class);

	private Integer id;
	private Integer dealId;
	private Integer customerId;
	private String cusEmail;
	private Date opDate;	
	private String body;
	private boolean approved;
	private Boolean onlyApproved;
	private Integer rate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Boolean getOnlyApproved() {
		return onlyApproved;
	}

	public void setOnlyApproved(Boolean onlyApproved) {
		this.onlyApproved = onlyApproved;
	}

	public String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof DealFeedback))
            return false;
        DealFeedback castOther = (DealFeedback) other;

        return new EqualsBuilder()
        		.append(id, castOther.id)
        		.isEquals();
    }
	
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
        		.toHashCode();
    }
    
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("dealId", dealId)
        	.append("customerId", customerId)        
        	.append("cusEmail", cusEmail)
        	.append("body", body)
        	.append("opDate", opDate)
        	.append("approved", approved)       
        	.append("rate", rate)
        	.append("onlyApproved", onlyApproved)
        	.toString();
    }
}
