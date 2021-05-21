package al.ozone.bl.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchDealChoiceBean {

	private String partnerName;
	private Integer dealId;
	private String dealTitle;
	private Integer choiceNr;
	private String choiceTitle;
	
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public String getDealTitle() {
		return dealTitle;
	}
	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}
	public Integer getChoiceNr() {
		return choiceNr;
	}
	public void setChoiceNr(Integer choiceNr) {
		this.choiceNr = choiceNr;
	}
	public String getChoiceTitle() {
		return choiceTitle;
	}
	public void setChoiceTitle(String choiceTitle) {
		this.choiceTitle = choiceTitle;
	}
	public boolean equals(final Object other) {
        if (!(other instanceof SearchDealChoiceBean))
            return false;
        SearchDealChoiceBean castOther = (SearchDealChoiceBean) other;
        
        return new EqualsBuilder()
        		.append(partnerName, castOther.partnerName)
				.append(dealId, castOther.dealId)
				.append(dealTitle, castOther.dealTitle)
				.append(choiceNr, castOther.choiceNr)
				.append(choiceTitle, castOther.choiceTitle)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(partnerName)
        		.append(dealId)
				.append(dealTitle)
				.append(choiceNr)
				.append(choiceTitle)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("partnerName", partnerName)
        	.append("dealId", dealId)
        	.append("dealTitle", dealTitle)
        	.append("choiceNr", choiceNr)
        	.append("choiceTitle", choiceTitle)
        	.toString();
    }
}
