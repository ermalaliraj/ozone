package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class EmailNewsletter implements Serializable, Cloneable{
	
	private static final long serialVersionUID = -7210352796631813416L;
	
	private String email;
	private boolean acceptNewsletter;
	private String cityId;
	private Date opDate;
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAcceptNewsletter() {
		return acceptNewsletter;
	}

	public void setAcceptNewsletter(boolean acceptNewsletter) {
		this.acceptNewsletter = acceptNewsletter;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public EmailNewsletter() {
	}
    
    public EmailNewsletter(String email) {
    	this.email = email;
    	//this.acceptNewsletter = true;
	} 

	public boolean equals(final Object other) {
        if (!(other instanceof EmailNewsletter))
            return false;
        EmailNewsletter castOther = (EmailNewsletter) other;
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
        	.append("cityId", cityId)
        	.append("opDate", opDate)
        	.toString();
    }

}