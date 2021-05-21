package al.ozone.bl.bean;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchAuditTrailBean {

	private String username;
	private Date from;
	private Date to;
	
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchAuditTrailBean))
            return false;
        SearchAuditTrailBean castOther = (SearchAuditTrailBean) other;
        
        return new EqualsBuilder()
				.append(username, castOther.username)
        		.append(from, castOther.from)
				.append(to, castOther.to)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(username)
        		.append(from)
        		.append(to)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("username", username)
        	.append("from", from)
        	.append("to", to)
        	.toString();
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
}
