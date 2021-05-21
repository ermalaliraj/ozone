package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CustomerInvited implements Serializable{

	private static final long serialVersionUID = 8760894625982924904L;
	
	private Integer inviterId;
    private Integer invitedId;//equal to customer ID
    private boolean confFirsPurchase;
    
    private String name;
    private String surname;
    private String email;
    private boolean active;
    private String cityId;
    
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public boolean equals(final Object obj) {
	        if (!(obj instanceof CustomerInvited))
	            return false;
	        CustomerInvited city = (CustomerInvited) obj;
	        return new EqualsBuilder()
	        		.append(inviterId, city.inviterId)
					.append(invitedId, city.invitedId)
					.append(confFirsPurchase, city.confFirsPurchase)
					.isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder().append(inviterId)
	        		.append(invitedId)
	        		.append(confFirsPurchase)
	        		.toHashCode();
	    }

		public String toString() {
	        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	        	.appendSuper(super.toString())
	        	.append("inviterId", inviterId)
	        	.append("invitedId", invitedId)
	        	.append("confFirsPurchase", confFirsPurchase)
	        	.append("name", name)
	        	.append("surname", surname)
	        	.append("email", email)
	        	.append("active", active)
	        	.append("cityId", cityId)
				.toString();
	    }
}
