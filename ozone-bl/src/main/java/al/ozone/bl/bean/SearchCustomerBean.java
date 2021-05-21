package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchCustomerBean extends AbstractSearchBean{

	private String id;
	private String fb_Id;
	private String fullname;
	private String name;
	private String surname;
	private String email;
	private String sex;
	private Boolean active;
	private String regDate;
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchCustomerBean))
            return false;
        SearchCustomerBean castOther = (SearchCustomerBean) other;
        
        return new EqualsBuilder()
        		.append(fullname, castOther.fullname)
				.append(email, castOther.email)
				.append(sex, castOther.sex)
				.append(active, castOther.active)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(fullname)
        		.append(email)
				.append(sex)
				.append(active)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("fb_Id", fb_Id)
        	.append("fullname", fullname)
        	.append("name", name)
        	.append("surname", surname)
        	.append("email", email)
        	.append("sex", sex)
        	.append("active", active)
        	.append("regDate", regDate)
        	.toString();
    }
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getFb_Id() {
		return fb_Id;
	}
	public void setFb_Id(String fb_Id) {
		this.fb_Id = fb_Id;
	}
	
}
