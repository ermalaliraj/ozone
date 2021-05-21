package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemConfigBean implements Serializable{
	
	private static final long serialVersionUID = -7364227439557198510L;
	
	private String key;
    private String value;
    
	public SystemConfigBean() {
	}
    
	public SystemConfigBean(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
    public boolean equals(final Object other) {
        if (!(other instanceof SystemConfigBean))
            return false;
        SystemConfigBean castOther = (SystemConfigBean) other;
        return new EqualsBuilder().append(key, castOther.key).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(key).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("key", key)
        	.append("value", value)
            .toString();
    }
}
