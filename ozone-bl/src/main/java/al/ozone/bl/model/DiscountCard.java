package al.ozone.bl.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DiscountCard implements Serializable {

	private static final long serialVersionUID = -7487894941192426256L;
	
	private String id;
	private int percDiscount;
	private Date usedDate;
	private String customerEmail;
	private Boolean used;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPercDiscount() {
		return percDiscount;
	}
	public void setPercDiscount(int percDiscount) {
		this.percDiscount = percDiscount;
	}
	public Date getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
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
	
	public boolean equals(final Object other) {
        if (!(other instanceof DiscountCard))
            return false;
        DiscountCard castOther = (DiscountCard) other;

        return new EqualsBuilder()
        		.append(id, castOther.id)
        		.append(percDiscount, percDiscount)
        		.append(customerEmail, customerEmail)
        		.append(used, used)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
        		.append(percDiscount)
        		.append(usedDate)
        		.append(customerEmail)
        		.append(used)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("percDiscount", percDiscount)
        	.append("usedDate", usedDate)
        	.append("customerEmail", customerEmail)
        	.append("used", used)
        	.toString();
    }

}
