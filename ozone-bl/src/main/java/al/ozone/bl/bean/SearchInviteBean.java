package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchInviteBean {

	private String inviterEmail;
    private String invitedEmail;
	private Date from;
	private Date to;
	private Boolean confFirstPurchase;
		
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
	public void setConfFirstPurchase(Boolean confFirstPurchase) {
		this.confFirstPurchase = confFirstPurchase;
	}
	public Boolean getConfFirstPurchase() {
		return confFirstPurchase;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchInviteBean))
            return false;
        SearchInviteBean castOther = (SearchInviteBean) other;
        
        return new EqualsBuilder()
        		.append(inviterEmail, castOther.inviterEmail)
				.append(invitedEmail, castOther.invitedEmail)
				.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(inviterEmail)
        		.append(invitedEmail)
				.append(from)
				.append(to)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("inviterEmail", inviterEmail)
        	.append("invitedEmail", invitedEmail)
        	.append("from", from)
        	.append("to", to)
        	.append("confFirstPurchase", confFirstPurchase)
        	.toString();
    }
	
}
