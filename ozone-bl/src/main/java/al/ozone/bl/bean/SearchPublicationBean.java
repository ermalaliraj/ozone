package al.ozone.bl.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchPublicationBean {

	private String pubId;
	private String cityId;
	private String partnerName;
	private String dealTitle;
	private Boolean confirmed;
	private String status; //(W)aiting, (A)ctive, (C)losed
	private Date insertedFrom;
	private Date insertedTo;
	
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getDealTitle() {
		return dealTitle;
	}
	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInsertedFrom() {
		return insertedFrom;
	}
	public void setInsertedFrom(Date insertedFrom) {
		this.insertedFrom = insertedFrom;
	}
	public Date getInsertedTo() {
		return insertedTo;
	}
	public void setInsertedTo(Date insertedTo) {
		this.insertedTo = insertedTo;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof SearchPublicationBean))
            return false;
        SearchPublicationBean castOther = (SearchPublicationBean) other;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        String sD1 = "";
        String sD2 = "";
        if(insertedFrom!=null){
        	sD1 = dateFormat.format(insertedFrom);
        	sD2 = dateFormat.format(castOther.insertedFrom);	
        }
        String eD1 = "";
        String eD2 = "";
        if(insertedTo!=null){
        	eD1 = dateFormat.format(insertedTo);
        	eD2 = dateFormat.format(castOther.insertedTo);	
        }
        return new EqualsBuilder()
        		.append(pubId, castOther.pubId)
        		.append(cityId, castOther.cityId)
        		.append(partnerName, castOther.partnerName)
        		.append(dealTitle, castOther.dealTitle)
        		.append(confirmed, castOther.confirmed)
        		.append(status, castOther.status)
        		.append(sD1, sD2)
        		.append(eD1, eD2)
        		.isEquals();
    }
	
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(pubId)
        		.append(cityId)
        		.append(partnerName)
        		.append(dealTitle)
        		.append(confirmed)
        		.append(status)
        		.append(insertedFrom)
        		.append(insertedTo)
        		.toHashCode();
    }
    
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("pubId", pubId)
        	.append("cityId", cityId)
        	.append("partnerName", partnerName)
        	.append("dealTitle", dealTitle)
        	.append("confirmed", confirmed)
        	.append("status", status)
        	.append("insertedFrom", insertedFrom)
        	.append("insertedTo", insertedTo)
        	.toString();
    }

}
