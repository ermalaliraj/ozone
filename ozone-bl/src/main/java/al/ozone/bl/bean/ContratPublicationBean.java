package al.ozone.bl.bean;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ContratPublicationBean {

	private String month;
	private Integer totContrats;
	private Integer stillWaiting;
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getTotContrats() {
		return totContrats;
	}

	public void setTotContrats(Integer totContrats) {
		this.totContrats = totContrats;
	}

	public Integer getStillWaiting() {
		return stillWaiting;
	}

	public void setStillWaiting(Integer stillWaiting) {
		this.stillWaiting = stillWaiting;
	}
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("month", month)
        	.append("totContrats", totContrats)
        	.append("stillWaiting", stillWaiting)
        	.toString();
    }

}
