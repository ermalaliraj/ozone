package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchCustomerLog {
	
	private String cusEmail;
	private String opType;
	private Date from;
	private Date to;
	private Boolean noCustomer;
	
	public String getCusEmail() {
		return cusEmail;
	}
	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
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
	public Boolean getNoCustomer() {
		return noCustomer;
	}
	public void setNoCustomer(Boolean noCustomer) {
		this.noCustomer = noCustomer;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchDealBean))
            return false;
        SearchCustomerLog castOther = (SearchCustomerLog) other;
        
        return new EqualsBuilder()
        		.append(cusEmail, castOther.cusEmail)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(cusEmail)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("cusEmail", cusEmail)
        	.append("opType", opType)
        	.append("from", from)
        	.append("to", to)
        	.append("noCustomer", noCustomer)
        	.toString();
    }
	
}
