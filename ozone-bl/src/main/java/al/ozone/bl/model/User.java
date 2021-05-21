package al.ozone.bl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;

    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Collection<Role> roles = new HashSet<Role>();
    private boolean enabled;
    private boolean locked;
    private Integer failedLoginCount;     
    private String lastIp;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(roles);
	}	

	public boolean isAccountNonExpired() {
		return enabled;
	}

	public boolean isAccountNonLocked() {
		return !locked;
	}

	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof User))
            return false;
        User castOther = (User) other;
        return new EqualsBuilder().append(username, castOther.username).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(username).toHashCode();
    }
  
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("username", username)
        	.append("name", name)
        	.append("surname", surname)
        	.append("email", email)
            .append("password", password)
            .append("enabled", enabled)
            .append("locked", locked)
            .append("failedLoginCount", failedLoginCount)
            .append("lastIp", lastIp)
            .append("roles", roles)
            .toString();
    }
}
