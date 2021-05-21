package al.ozone.bl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class CustomerLog implements Serializable {

	private static final long serialVersionUID = -2682592638090266068L;
	public static final String LOGIN = "LOGIN";
	public static final String REGISTRATION = "REGISTRATION";
	public static final String ORDER = "ORDER";
	public static final String PURCHASE = "PURCHASE";
	public static final String INVITE = "INVITE";
	public static final String RESET_PASSWORD = "RESET_PASSWORD";
	public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
	public static final String PAYPAL_REFUND= "PAYPAL_REFUND";
	public static final String PAYPAL_REVERSE= "PAYPAL_REVERSE";
	public static final String PAYPAL_ERROR = "PAYPAL_ERROR";
	public static final String PAYPAL_INVALID = "PAYPAL_INVALID";	
	public static final String EASYPAY_ERROR = "EASYPAY_ERROR";
	public static final String COUPON_CHANGE_STATUS_ERROR = "COUPON_CHANGE_STATUS_ERROR";

	private Integer id;
	private Customer customer;
    private String opType;
    private Date opDate;
    private String errorMsg;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof CustomerLog))
            return false;
        CustomerLog castOther = (CustomerLog) other;
        return new EqualsBuilder().append(id, castOther.id)
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id)
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("opType", opType)
			.append("opDate", opDate)
			.append("errorMsg", errorMsg)
			.append("customer", customer)
        	.toString();
    }

	public static List<String> getAllOperationTypes() {
		List<String> allTypes = new ArrayList<String>();
		allTypes.add(LOGIN);
		allTypes.add(REGISTRATION);
		allTypes.add(ORDER);
		allTypes.add(PURCHASE);
		allTypes.add(PAYPAL_REFUND);
		allTypes.add(PAYPAL_REVERSE);
		allTypes.add(PAYPAL_INVALID);
		allTypes.add(PAYPAL_ERROR);		
		allTypes.add(INVITE);
		allTypes.add(RESET_PASSWORD);
		allTypes.add(CHANGE_PASSWORD);
		allTypes.add(EASYPAY_ERROR);
		allTypes.add(COUPON_CHANGE_STATUS_ERROR);
		return allTypes;
	}
}