package al.ozone.bl.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class SearchBeanEmailNewsletter extends AbstractSearchBean implements Serializable, Cloneable{
	
	private static final long serialVersionUID = -7166673544831632239L;
	
	private String email;
	private Boolean acceptNewsletter;
	
    public SearchBeanEmailNewsletter() {
	}
    
    public SearchBeanEmailNewsletter(String email) {
    	this.email = email;
	} 

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAcceptNewsletter() {
		return acceptNewsletter;
	}

	public void setAcceptNewsletter(Boolean acceptNewsletter) {
		this.acceptNewsletter = acceptNewsletter;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof SearchBeanEmailNewsletter))
            return false;
        SearchBeanEmailNewsletter castOther = (SearchBeanEmailNewsletter) other;
        return new EqualsBuilder().append(email, castOther.email)
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(email)
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("email", email)
        	.append("acceptNewsletter", acceptNewsletter)
        	.toString();
    }

}