package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

public class CustomerRole implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	public static final String ROLE_ADMIN = "ROLE_CUS";
	public static final String ROLE_READ = "ROLE_PARTNER";
	
    private String id;
    private String description;
    
    public CustomerRole(){
    }
    
    public CustomerRole(String id){
    	this.id=id;
    }
    
    public CustomerRole(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getAuthority() {
        return id;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean equals(final Object other) {
        if (!(other instanceof CustomerRole))
            return false;
        CustomerRole castOther = (CustomerRole) other;
        return new EqualsBuilder().append(id, castOther.id).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
        	.append("id", id)
        	.append("description", description)
        	.toString();
    }

}