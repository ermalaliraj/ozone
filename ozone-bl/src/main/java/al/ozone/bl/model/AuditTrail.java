package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AuditTrail implements Serializable {

	private static final long serialVersionUID = 686324286556428119L;
	
	public static final String LOGIN = "LOGIN";
	public static final String LOGIN_WRONG = "LOGIN_WRONG";
	public static final String USER_INSERT = "USER_INSERT";
	public static final String USER_UPDATE = "USER_UPDATE";
	public static final String USER_DELETE = "USER_DELETE";
	public static final String USER_ACTIVE = "USER_ACTIVE";
	public static final String USER_NOT_ACTIVE = "USER_NOT_ACTIVE";
	public static final String USER_NOT_LOCKED = "USER_NOT_LOCKED";
	
	public static final String PARTNER_INSERT = "PARTNER_INSERT";
	public static final String PARTNER_UPDATE = "PARTNER_UPDATE";
	public static final String PARTNER_DELETE = "PARTNER_DELETE";
	
	public static final String DEAL_INSERT = "DEAL_INSERT";
	public static final String DEAL_UPDATE = "DEAL_UPDATE";
	public static final String DEAL_DELETE = "DEAL_DELETE";
	public static final String DEAL_APPROVED = "DEAL_APPROVED";
	public static final String DEAL_NOT_APPROVED = "DEAL_NOT_APPROVED";
	
	public static final String PUBLICATION_INSERT = "PUBLICATION_INSERT";
	public static final String PUBLICATION_UPDATE = "PUBLICATION_UPDATE";
	public static final String PUBLICATION_DELETE = "PUBLICATION_DELETE";
	
	public static final String CUSTOMER_INSERT = "CUSTOMER_INSERT";
	public static final String CUSTOMER_UPDATE = "CUSTOMER_UPDATE";
	public static final String CUSTOMER_DELETE = "CUSTOMER_DELETE";
	public static final String CUSTOMER_ACTIVE = "CUSTOMER_ACTIVE";
	public static final String CUSTOMER_NOT_ACTIVE = "CUSTOMER_NOT_ACTIVE";
	
	public static final String PURCHASE_INSERT = "PURCHASE_INSERT";
	public static final String PAYMENT_INSERT = "PAYMENT_INSERT";
	public static final String CREDIT_INSERT = "CREDIT_INSERT";
	
	public static final String DISCOUNT_CARD_INSERT = "DISCOUNT_CARD_INSERT";
	
	public static final String COUPON_STATUS_CHANGE = "COUPON_STATUS_CHANGE";
	public static final String COUPON_ERROR_STATUS_CHANGE = "COUPON_ERROR_STATUS_CHANGE";
	
    private Long id;
    private String username;
    private String operationName;
    private String operationDesc;
    private Date operationTime;
    private String roles;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
    public boolean equals(final Object other) {
        if (!(other instanceof AuditTrail))
            return false;
        AuditTrail castOther = (AuditTrail) other;
        return new EqualsBuilder().append(id, castOther.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.append("id", id)
        	.append("username", username)
            .append("operationName", operationName)
            .append("operationDesc", operationDesc)
            .append("operation", operationTime)
            .append("roles", roles)
            .toString();
    }
}
