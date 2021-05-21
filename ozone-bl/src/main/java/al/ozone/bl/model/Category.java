package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Category implements Serializable{
	
	private static final long serialVersionUID = 1424058151176452030L;
	public static final Integer CAT_OTHER = 11;
	
	private Integer id;
	private String nameEn;
	private String nameAl;
	private String name;
	private String description;
	
	public Category(){
		
	}
	
	public Category (Integer id){
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameAl() {
		return nameAl;
	}
	public void setNameAl(String nameAl) {
		this.nameAl = nameAl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean equals(final Object obj) {
        if (!(obj instanceof Category))
            return false;
        Category category = (Category) obj;
        return new EqualsBuilder().append(id, category.id)
        		.isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id)
        		.append(id)
        		.toHashCode();
    }

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("nameEn", nameEn)
        	.append("nameAl", nameAl)
        	.append("name", name)
			.append("description", description)
        	.toString();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
