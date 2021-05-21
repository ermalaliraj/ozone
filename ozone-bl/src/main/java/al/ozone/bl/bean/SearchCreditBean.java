package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchCreditBean {

	private String customerEmail;
	private Boolean used;
	private String type;
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchCreditBean))
            return false;
        SearchCreditBean castOther = (SearchCreditBean) other;
        
        return new EqualsBuilder()
        		.append(customerEmail, castOther.customerEmail)
				.append(used, castOther.used)
				.append(type, castOther.type)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(customerEmail)
        		.append(used)
				.append(type)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("customerEmail", customerEmail)
        	.append("used", used)
        	.append("type", type)
        	.toString();
    }
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Boolean isUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
