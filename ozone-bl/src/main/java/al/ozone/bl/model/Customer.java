package al.ozone.bl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class Customer implements Serializable, UserDetails {
	
	private static final long serialVersionUID = 734131080599047037L;
	public static final String MALE = "M";
	public static final String FEMALE = "F";
	public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
	
	private Integer id;
	private String fb_Id;
    private String name;
    private String surname;
    private Date birthdate;
    private String email;
    private String password;
    private String phone;
    private String address;
    private List<Credit> credits;
    private List<City> subscriptions;
    private boolean active;
    private String cityId;
    private String sex;
    private Date reg_Date;
    private Date lst_login; //last login
    private Collection<CustomerRole> roles = new HashSet<CustomerRole>();
   
    public Customer(){
    	credits = new ArrayList<Credit>();	
    	roles.add(new CustomerRole(ROLE_CUSTOMER, "Customer role"));
    }
    
    public Customer(Integer idUser) {
    	this.id = idUser;
    	credits = new ArrayList<Credit>();	
    	roles.add(new CustomerRole(ROLE_CUSTOMER, "Customer role"));
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	
	public String getEmail() {
		return email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFb_Id() {
		return fb_Id;
	}

	public void setFb_Id(String fb_Id) {
		this.fb_Id = fb_Id;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof Customer))
            return false;
        Customer castOther = (Customer) other;
        return new EqualsBuilder().append(id, castOther.id)
				.append(name, castOther.name)
				.append(surname, castOther.surname)
//				.append(birthdate, castOther.birthdate)
//				.append(email, castOther.email)
//				.append(password, castOther.password)
//				.append(phone, castOther.phone)	
//				.append(invitedBy, castOther.invitedBy)	
//				.append(sex, castOther.sex)	
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id)
        		.append(name)
				.append(surname)
//				.append(birthdate)
//				.append(email)
//				.append(password)
//				.append(phone)	
//				.append(sex)
//				.append(invitedBy)	
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("fb_Id", fb_Id)
        	.append("name", name)
			.append("surname", surname)
			.append("birthday", birthdate)
			.append("email", email)
			.append("pwd", password)
			.append("phone", phone)		
			.append("active", active)	
			.append("sex", sex)	
			.append("address", address)
			.append("credits", credits)	
			.append("lst_login", lst_login)
			.append("roles", roles)
        	.toString();
    }
	public List<Credit> getCredits() {
		return credits;
	}

	public void setCredits(List<Credit> credits) {
		this.credits = credits;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<City> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<City> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Date getReg_Date() {
		return reg_Date;
	}

	public void setReg_Date(Date registrationDate) {
		this.reg_Date = registrationDate;
	}

	public Date getLst_login() {
		return lst_login;
	}

	public void setLst_login(Date lst_login) {
		this.lst_login = lst_login;
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
	
	
}