package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchDiscountCardBean {

	private String customerEmail;
	private Boolean used;
	private Integer percValue ;
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchDiscountCardBean))
            return false;
        SearchDiscountCardBean castOther = (SearchDiscountCardBean) other;
        
        return new EqualsBuilder()
        		.append(customerEmail, castOther.customerEmail)
				.append(used, castOther.used)
				.append(percValue, castOther.percValue)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(customerEmail)
        		.append(used)
				.append(percValue)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("customerEmail", customerEmail)
        	.append("used", used)
        	.append("percValue", percValue)
        	.toString();
    }
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Boolean getUsed() {
		return used;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public Integer getPercValue() {
		return percValue;
	}
	public void setPercValue(Integer percValue) {
		this.percValue = percValue;
	}
}
