package al.ozone.bl.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DiscountCardGroup {
	private int percentage;
	private int total;
	private int totalUsed;
	private int totalUnused;
	

	public boolean equals(final Object other) {
        if (!(other instanceof DiscountCardGroup))
            return false;
        DiscountCardGroup castOther = (DiscountCardGroup) other;
        return new EqualsBuilder()
        		.append(percentage, castOther.percentage)
				.append(total, castOther.total)
				.append(totalUsed, castOther.totalUsed)
				.append(totalUnused, castOther.totalUnused)
        		.isEquals();
    }
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(percentage)
			.append(total)
			.append(totalUsed)
			.append(totalUnused)
			.toHashCode();
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("percentage", percentage)
        	.append("total", total)
			.append("totalUsed", totalUsed)
			.append("totalUnused", totalUnused)
        	.toString();
    }

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalUsed() {
		return totalUsed;
	}
	public void setTotalUsed(int totalUsed) {
		this.totalUsed = totalUsed;
	}
	public int getTotalUnused() {
		return totalUnused;
	}
	public void setTotalUnused(int totalUnused) {
		this.totalUnused = totalUnused;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

}
