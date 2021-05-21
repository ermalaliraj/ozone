
package al.ozone.bl.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import al.ozone.bl.utils.ZUtils;

public class Publication implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String STATUS_WAITING = "W";
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_CLOSED = "C";

	private Integer id;
	private Date startDate;
	private Date endDate;
	private int totPurchases;
	private boolean confirmed;
	private String status;
	private boolean couponsPrepared;
	private int order;
	private Deal deal;
	// private Integer dealId;
	private City city;
	private boolean valid;
	
	public Publication() {
		deal = new Deal();
	}
	public Publication(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String getEndDateFormatted(){
		return ZUtils.getDateAsStringAsDDMMYYYY(endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotPurchases() {
		return totPurchases;
	}

	public void setTotPurchases(int totPurchases) {
		this.totPurchases = totPurchases;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isCouponsPrepared() {
		return couponsPrepared;
	}
	public void setCouponsPrepared(boolean prepared) {
		this.couponsPrepared = prepared;
	}
	public boolean isValid() {
		return ZUtils.isFutureOrTodayDate(endDate);
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getColorStatus(){
		
		if(status.equals(Publication.STATUS_ACTIVE)){
			return "green";
		}
		if(status.equals(Publication.STATUS_WAITING)){
			return "yellow";
		}
		
		return null;
	}
	
	public Date getExpirationDiscount(){
		Date date = ZUtils.addDaysToDate(endDate, 1);
		//System.out.println(date);
		//for Velur Spa id=32 only 3 weeks
		if(id==14){
			date = ZUtils.addDaysToDate(date, 23);
		} else {
			date = ZUtils.addMonthsToDate(date, deal.getDiscountDuration());
		}
		//System.out.println("deal.getDiscountDuration()"+deal.getDiscountDuration());
		
		//System.out.println(date);
		return date;
	}

	public boolean equals(final Object other) {
		if (!(other instanceof Publication))
			return false;
		Publication castOther = (Publication) other;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");

		String sD1 = "";
		String sD2 = "";
		if (startDate != null) {
			sD1 = dateFormat.format(startDate);
			sD2 = dateFormat.format(castOther.startDate);
		}
		String eD1 = "";
		String eD2 = "";
		if (endDate != null) {
			eD1 = dateFormat.format(endDate);
			eD2 = dateFormat.format(castOther.endDate);
		}

		return new EqualsBuilder().append(id, castOther.id)
				.append(deal, castOther.deal).append(sD1, sD2).append(eD1, eD2)
				.append(totPurchases, castOther.totPurchases)
				.append(confirmed, castOther.confirmed)
				.append(status, castOther.status)
				.append(order, castOther.order).append(city, castOther.city)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(id).append(deal).append(startDate)
				.append(totPurchases).append(confirmed).append(status)
				.append(order).append(city).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.appendSuper(super.toString()).append("id", id)
				.append("startDate", startDate).append("endDate", endDate)
				.append("totPurchases", totPurchases)
				.append("confirmed", confirmed)
				.append("status", status)
				.append("couponsPrepared", couponsPrepared)				
				.append("order", order).append("deal", deal)
				.append("city", city).toString();
	}

	public boolean isEditable() {
		boolean retVal = true;
		if (status == null)
			return true;

		if (status.equals(Publication.STATUS_WAITING)) {
			retVal = true;
		} else if (status.equals(Publication.STATUS_ACTIVE)) {
			retVal = false;
		} else if (status.equals(Publication.STATUS_CLOSED)) {
			retVal = false;
		}
		return retVal;
	}
	
	public boolean isAvailableMoreCoupons(){
		boolean rv = true;
		
		if(totPurchases >= deal.getMaxCustomers()){
			rv = false;
		}
		
		return rv;
	}
}
