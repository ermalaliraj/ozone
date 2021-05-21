package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ResultStatisticBean {

	private String key;
	private Integer value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Number getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof ResultStatisticBean))
            return false;
        ResultStatisticBean castOther = (ResultStatisticBean) other;
        
        return new EqualsBuilder()
        		.append(key, castOther.key)
        		.isEquals();
    }
	
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(key)
        		.toHashCode();
    }
	
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("key", key)
        	.append("value", value)
        	.toString();
    }

}
