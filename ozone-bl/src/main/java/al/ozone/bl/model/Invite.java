package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Invite implements Serializable{

	private static final long serialVersionUID = 8760894625982924904L;
	
	private Integer inviterId;
    private Integer invitedId;
    private String inviterEmail;
    private String invitedEmail;
    private boolean confFirsPurchase;
    private Date operationDate;
    
	public Integer getInviterId() {
		return inviterId;
	}
	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}
	public Integer getInvitedId() {
		return invitedId;
	}
	public void setInvitedId(Integer invitedId) {
		this.invitedId = invitedId;
	}
	public boolean isConfFirsPurchase() {
		return confFirsPurchase;
	}
	public void setConfFirsPurchase(boolean confFirsPurchase) {
		this.confFirsPurchase = confFirsPurchase;
	}

	public String getInviterEmail() {
		return inviterEmail;
	}
	public void setInviterEmail(String inviterEmail) {
		this.inviterEmail = inviterEmail;
	}
	public String getInvitedEmail() {
		return invitedEmail;
	}
	public void setInvitedEmail(String invitedEmail) {
		this.invitedEmail = invitedEmail;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	public boolean equals(final Object obj) {
	        if (!(obj instanceof Invite))
	            return false;
	        Invite inv = (Invite) obj;
	        return new EqualsBuilder()
	        		.append(inviterId, inv.inviterId)
	        		.append(inviterEmail, inv.inviterEmail)
					.append(invitedId, inv.invitedId)
					.append(invitedEmail, inv.invitedEmail)
					.append(confFirsPurchase, inv.confFirsPurchase)
					.isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder().append(inviterId)
	        		.append(invitedId)
	        		.append(inviterEmail)
	        		.append(invitedEmail)
	        		.append(confFirsPurchase)
	        		.toHashCode();
	    }

		public String toString() {
	        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	        	.appendSuper(super.toString())
	        	.append("inviterId", inviterId)
	        	.append("inviterEmail", inviterEmail)
	        	.append("invitedId", invitedId)
				.append("invitedEmail", invitedEmail)
	        	.append("confFirsPurchase", confFirsPurchase)
	        	.append("operationDate", operationDate)
				.toString();
	    }
}
