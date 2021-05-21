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

import al.ozone.bl.bean.AbstractSearchBean;
import al.ozone.bl.utils.ZUtils;

public class Partner extends AbstractSearchBean implements Serializable, Cloneable, UserDetails{
	
	private static final long serialVersionUID = -5735951902786363382L;
	
	private Integer id;
    private String name;
    private City city;
    private String address;
    private String tel;
    private String cel;
    private String email;
    private String webSite;
    private Category category;
    
    private double lat;  
    private double lng; 
    private int zoomLevel;
    
    private boolean active; // true if can use the changeCouponStatus
    private String password;
    private Collection<CustomerRole> roles = new HashSet<CustomerRole>();
    
    public Partner() {
    	roles.add(new CustomerRole("ROLE_PARTNER", "Partner role"));
	}
    
    public Partner(Integer partnerId) {
    	this();
		this.id=partnerId;
	}
    
    public Partner(String partnerName) {
    	this();
		this.name=partnerName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * The method decides which contact to show for this partner
	 * @return
	 */
	public String getContact(){
		String rv = "";
		 if(!ZUtils.isEmptyString(cel)){
			rv = cel;
		}else if(!ZUtils.isEmptyString(tel)){
			rv = tel;
		}
		return rv;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof Partner))
            return false;
        Partner castOther = (Partner) other;
        return new EqualsBuilder().append(id, castOther.id)
				.append(name, castOther.name)
				.append(address, castOther.address)
				.append(city, castOther.city)				
				.append(tel, castOther.tel)
				.append(cel, castOther.cel)
				.append(email, castOther.email)
				.append(webSite, castOther.webSite)
				//.append(category, castOther.category)
				.append(lat, castOther.lat)
				.append(lng, castOther.lng)
				.append(zoomLevel, castOther.zoomLevel)
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id)
        		.append(name)
        		.append(address)
        		.append(city)
        		.append(tel)
        		.append(cel)
        		.append(email)
        		.append(webSite)
        		//.append(category)
        		.append(lat)
				.append(lng)
				.append(zoomLevel)
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("name", name)
        	.append("address", address)
        	.append("city", city)        	
        	.append("tel", tel)
        	.append("cel", cel)
        	.append("email", email)
        	.append("password", password)
        	.append("webSite", webSite)
        	.append("category", category)
        	.append("lat", lat)
			.append("lng", lng)
			.append("zoomLevel", zoomLevel)
        	.toString();
    }
	
	public Object clone() {
		Partner p = new Partner();
		p.id = this.id;
		p.name = this.name;
		p.city = this.city;
		p.address = this.address;
		p.tel = this.tel;
		p.cel = this.cel;
		p.email = this.email;
		p.webSite = this.webSite;
		p.category = this.category;
		p.lat = this.lat;
		p.lng = this.lng;
		p.zoomLevel = this.zoomLevel;
		return p;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(roles);
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public void setUsername(String username){
		this.email = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public Collection<CustomerRole> getRoles() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}