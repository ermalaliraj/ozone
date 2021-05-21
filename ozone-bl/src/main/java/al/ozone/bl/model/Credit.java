package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import al.ozone.bl.utils.ZUtils;

public class Credit implements Serializable{
	
	private static final long serialVersionUID = -746766164284091950L;
	public final static  String TYPE_BENEFIT = "B";
	public final static String TYPE_REIMBORSEMENT = "R";
	public final static String TYPE_DIFFERENCE = "D";
	
	private Integer id;
	private Date assignedDate;
	private Date validDate;
	private Date usedDate;
	private int value;
	private String type;
	private Customer customer;
	private String about;
	private String aboutUse;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public Date getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getAboutUse() {
		return aboutUse;
	}
	public void setAboutUse(String aboutUse) {
		this.aboutUse = aboutUse;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof Credit))
            return false;
        Credit credit = (Credit) other;
      
        return new EqualsBuilder()
        		.append(id, credit.id)
//        		.append(assignedDate.toString(),credit.assignedDate.toString())
//        		.append(validDate.toString(), credit.validDate.toString())
//        		.append(usedDate.toString(), credit.usedDate.toString())
//        		.append(value, credit.value)
//        		.append(type, credit.type)
//        		.append(customer, credit.customer)
//        		.append(about, credit.about)
//        		.append(aboutUse, credit.aboutUse)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
//        		.append(assignedDate)
//        		.append(validDate)
//        		.append(usedDate)
//        		.append(value)
//        		.append(type)
//        		.append(customer)
//        		.append(about)
//        		.append(aboutUse)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("assignedDate", assignedDate)
        	.append("validDate", validDate)
        	.append("usedDate", usedDate)
        	.append("value", value)
        	.append("type", type)
        	.append("customer", customer)
        	.append("about", about)
        	.append("aboutUse", aboutUse)
        	.toString();
    }

	public boolean isEditable(){
		return usedDate==null;
	}
	
	public boolean isAvailable(){
		 return ZUtils.isValidCredit(this);
	}
	
	public String getStatusString(){
		String rv = "";
		Date today = ZUtils.getDateAsDDMMYYYY(new Date());
		Date creditDate = ZUtils.getDateAsDDMMYYYY(getValidDate());
		
		if(usedDate==null){
			if(today.compareTo(creditDate) <= 0){
				rv = "I disponueshem"; //TODO use messageBundle
			}else{
				rv = "I skaduar";
			}
		}else{
			rv = "I perdorur me date "+ZUtils.getDateAsStringAsDDMMYYYY(usedDate);
		}
		return rv;
	}
}
