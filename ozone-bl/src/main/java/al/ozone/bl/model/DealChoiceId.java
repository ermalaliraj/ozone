package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DealChoiceId implements Serializable {

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(DealChoiceId.class);
	
	private Integer dealId;
	private Integer choiceNr;
	
	public DealChoiceId(Integer dealId, Integer choiceNr) {
		this.dealId = dealId;
		this.choiceNr = choiceNr;
	}
	
	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public Integer getChoiceNr() {
		return choiceNr;
	}
	public void setChoiceNr(Integer choiceNr) {
		this.choiceNr = choiceNr;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof DealChoiceId))
            return false;
        DealChoiceId castOther = (DealChoiceId) other;

        return new EqualsBuilder()
        		.append(dealId, castOther.dealId)
        		.append(choiceNr, castOther.choiceNr)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(dealId)
        		.append(choiceNr)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("dealId", dealId)
        	.append("choiceNr", choiceNr)
        	.toString();
    }
}
