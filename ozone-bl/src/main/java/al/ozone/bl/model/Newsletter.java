package al.ozone.bl.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Newsletter implements Serializable{
	
	private static final long serialVersionUID = -336414112822459418L;
	
	private String cityId;
	private String cityName;
    private List<Deal> activeDeals;
    
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public List<Deal> getActiveDeals() {
		return activeDeals;
	}
	public void setActiveDeals(List<Deal> activeDeals) {
		this.activeDeals = activeDeals;
	}
	public boolean equals(final Object obj) {
	        if (!(obj instanceof Newsletter))
	            return false;
	        Newsletter newsletter = (Newsletter) obj;
	        return new EqualsBuilder().append(cityId, newsletter.cityId)
					.isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder().append(cityId)
	        		.toHashCode();
	    }

		public String toString() {
	        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	        	.appendSuper(super.toString())
	        	.append("cityId", cityId)
	        	.append("cityName", cityName)
	        	.append("activeDeals", activeDeals)
	        	.toString();
	    }
}
