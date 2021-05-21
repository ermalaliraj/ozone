package al.ozone.bl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.exception.PublicationNotSelectedException;
import al.ozone.bl.utils.ZUtils;

public class Deal implements Serializable {

	private static final long serialVersionUID = 7409486303345043724L;
	protected static final transient Log logger = LogFactory.getLog(Deal.class);
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_WAITING = "W";
	public static final String STATUS_CLOSED = "C";
	public static final String STATUS_DELETED = "D";	
	
	public final double WEEK_1 = 0.23;
	public final double WEEK_2 = 0.46;
	public final double WEEK_3 = 0.7;	
	
	private Integer id;
	private String title;
	private String titleNewsletter;
	private int percentDiscount;
	private String clientFullName;
	private String clientCel;
	private String brokerFullName;
	private String brokerCel;
	
	private Date contractDate;
	private Date startDate;
	private Date endDate;
	private int discountDuration;//3, 6 or 12 months //1(0.23), 2(0.46), or 3(0.7) weeks
	private String status;	
	private int totPurchases;
	private boolean confirmed;	
	private boolean couponsPrepared;
	private int order;
	
	private String mainImgName;
	private String synthesis;
	private String conditions;
	private String description;
	private boolean approvedForPublish;
	private String approvedUser;	
	private Date approvedDate;
	private Date lastUpdate;
	private String lastUpdateUser;
	private boolean couponImmediately;
	private boolean accounted;
	private String contractComment;
	private List<String> uploadedFiles;
	private Partner partner;
	private List<DealChoice> choices;
	private List<String> dealCities;
	private Integer rank;

	public Deal(){
		//uploadedFiles = new ArrayList<String>();
		choices = new ArrayList<DealChoice>(); 
		//dealCities = new ArrayList<City>(); 
	}
	
	public Deal(Integer id) {
		this.id = id;
		choices = new ArrayList<DealChoice>(); 
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public String getClientFullName() {
		return clientFullName;
	}
	public void setClientFullName(String clientFullName) {
		this.clientFullName = clientFullName;
	}
	public String getClientCel() {
		return clientCel;
	}
	public void setClientCel(String clientCel) {
		this.clientCel = clientCel;
	}
	public String getBrokerFullName() {
		return brokerFullName;
	}
	public void setBrokerFullName(String brokerFullName) {
		this.brokerFullName = brokerFullName;
	}
	public String getBrokerCel() {
		return brokerCel;
	}
	public void setBrokerCel(String brokerCel) {
		this.brokerCel = brokerCel;
	}
	public String getMainImgName() {
		//System.out.println("dealImg:"+mainImgName);
		return mainImgName;
	}
	public void setMainImgName(String mainImgName) {
		this.mainImgName = mainImgName;
	}
	public String getSynthesis() {
		return synthesis;
	}
	public void setSynthesis(String synthesis) {
		this.synthesis = synthesis;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isApprovedForPublish() {
		return approvedForPublish;
	}
	public void setApprovedForPublish(boolean approvedForPublish) {
		this.approvedForPublish = approvedForPublish;
	}
	public String getApprovedUser() {
		return approvedUser;
	}
	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public List<String> getUploadedFiles() {
		return uploadedFiles;
	}
	public void setUploadedFiles(List<String> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}	
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public int getDiscountDuration() {
		return discountDuration;
	}
	public void setDiscountDuration(int discountDuration) {
		this.discountDuration = discountDuration;
	}
	public boolean isCouponImmediately() {
		return couponImmediately;
	}
	public void setCouponImmediately(boolean couponImmediately) {
		this.couponImmediately = couponImmediately;
	}
	public String getTitleNewsletter() {
		return titleNewsletter;
	}
	public void setTitleNewsletter(String titleNewsletter) {
		this.titleNewsletter = titleNewsletter;
	}

	public void setPercentDiscount(int percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
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

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isCouponsPrepared() {
		return couponsPrepared;
	}

	public void setCouponsPrepared(boolean couponsPrepared) {
		this.couponsPrepared = couponsPrepared;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<DealChoice> getChoices() {
		return choices;
	}

	public void setChoices(List<DealChoice> choices) {
		this.choices = choices;
	}

	public String getContractComment() {
		return contractComment;
	}

	public void setContractComment(String contractComment) {
		this.contractComment = contractComment;
	}

	public String getColorForCalendar() {
		String rv = "";
		
		if(ZUtils.isEmptyList(choices)) {
			rv = "calErrorColor";
		} else
		if(status.equals(Deal.STATUS_CLOSED)){
			if(approvedForPublish){
				rv = "calClosedDealColor";
			}else{				
				rv = "calErrorColor";
			}
		} else 
		if(status.equals(Deal.STATUS_ACTIVE)){		
			if(approvedForPublish){
				if( startDate==null && endDate==null){
					rv = "calErrorColor";
				}else
				if(startDate!=null && endDate==null){
					rv = "dealActiveNoEndDate";	
				}else{
					rv = "dealActive";
				}				
			}else{
				rv = "calErrorColor";
			}	
		}else
		if(status.equals(Deal.STATUS_WAITING)){
			if(approvedForPublish){
				rv = "calApprovedColor";
			}else{
				rv = "calNotApprovedColor";
			}			
		}
		else{
			rv = "calErrorColor";
			logger.error("Error deal Status. Is neither Waiting nor Active nor Closed");
		}
		
		return rv;
	}

	public String getColorStatus(){
		String rv = null;
		
		if(ZUtils.isEmptyList(choices)) {
			rv = "dealError";
		}else		
		if(status.equals(STATUS_CLOSED)){
			if(!approvedForPublish){
				rv = "dealError";
			}		
		} else 
		if(status.equals(STATUS_ACTIVE)){		
			if(approvedForPublish){
				if( startDate==null && endDate==null){
					rv = "dealError";
				}else
				if(startDate!=null && endDate==null){
					rv = "dealActiveNoEndDate";	
				}else{
					rv = "dealActive";
				}				
			}else{
				rv = "dealError";
			}		
		}else
		if(status.equals(STATUS_WAITING)){
			if(approvedForPublish){
				rv = "dealApproved";
			}else{
				rv = "dealNotApproved";
			}			
		}else
		if(status.equals(STATUS_DELETED)){
			rv = "dealDeleted";	
		}
		else{
			rv = "dealError";
			logger.error("Error deal Status. Is neither Waiting nor Active nor Closed");
		}
		return rv;
	}
	
	public Date getExpirationDiscount(){
		Date date = ZUtils.addDaysToDate(endDate, 1);
		
		//for Velur Spa id=32 only 3 weeks
		if(id==32){
			date = ZUtils.addDaysToDate(date, 21);
		} else {
			date = ZUtils.addMonthsToDate(date, discountDuration);
		}
		
//		if(discountDuration < 1){
//			if(discountDuration <= WEEK_1){
//				date = ZUtils.addDaysToDate(date, 7);
//			}else if(discountDuration <= WEEK_2){
//				date = ZUtils.addDaysToDate(date, 14);
//			} if(discountDuration <= WEEK_3){
//				date = ZUtils.addDaysToDate(date, 21);
//			}
//		} else{
//			// if > 1 we are talking about months
//			date = ZUtils.addMonthsToDate(date, (int) discountDuration);
//		}
		
		return date;
	}
	
	public String getExpirationDiscountFormatted(){
		return ZUtils.getDateAsStringAsDDMMYYYY(getExpirationDiscount());
	}
	
	public String getEndDateFormatted(){
		return ZUtils.getDateAsStringAsDDMMYYYY(endDate);
	}
	
	public String getNormalizedTitle() {
		String str = title;
//		str = str.replace('e', 'C');
//		str = str.replace('e', 'c');
//		str = str.replace('e', 'e');
		
		return str;
	}
	
	public boolean isValid() {
		if(endDate==null){
			return true; // no end date is set
		}else{
			return ZUtils.isFutureOrTodayDate(endDate);
		}		
	}
	public boolean isOpenDeal() {
		if(endDate==null){
			return true; // no end date is set
		}else{
			return false;
		}		
	}
	
	//TODO ermal kur nuk mund te blihet me thuaje
	public boolean isAvailableMoreCoupons(){
		boolean rv = true;
		
//		if(totPurchases >= ){
//			rv = false;
//		}
		
		return rv;
	}
	
	public String getPriceLabel() throws PublicationNotSelectedException{
		String rv = "";
		try {
			DealChoice dc = choices.get(0);
			if (choices.size() == 1) {
				rv = "" + dc.getPrice();
			} else {
				//rv = "nga " + dc.getPrice();
				rv = "" + dc.getPrice();
			}
		} 
//		catch(IndexOutOfBoundsException e1){
//			throw new PublicationNotSelectedException("Deal " + id + " seems not to have right number of dealChoices");
//		}
		catch (Exception e) {
			logger.warn("Error calculation price to show on Front-End.", e);
			rv = "";
		} 
		return rv;
	}

	public void setPrice(int price){

	}
	public int getPrice(){
		int rv = 0;
		try{
			DealChoice dc = choices.get(0);
			rv = dc.getPrice();
		}
		catch(Exception e){
			logger.warn("Error calculation price to show on Front-End.", e);
			rv = 0;
			//throw new PublicationNotSelectedException(e.getMessage());
		}	
		return rv;
	}
	
	public int getFullPrice(){
		int rv = 0;
		try{
			DealChoice dc = choices.get(0);
			rv = dc.getFullPrice();
		}
		catch(Exception e){
			logger.warn("Error calculation fullprice to show on Front-End.", e);
			rv = 0;
			//throw new PublicationNotSelectedException(e.getMessage());
		}	
		return rv;
	}
	
	public int getPercentDiscount() {
		int rv = 0;
		try{
			DealChoice dc = choices.get(0);
			rv = dc.getPercentDiscount();
		}
		catch(Exception e){
			logger.warn("Error calculation percentDiscount to show on Front-End.", e);
			rv = 0;
			//throw new PublicationNotSelectedException(e.getMessage());
		}	
		return rv;
	}
	
	public int getSaving(){	
		int rv = 0;
		try{
			DealChoice dc = choices.get(0);
			rv = dc.getFullPrice() - dc.getPrice();
		}
		catch(Exception e){
			logger.warn("Error calculation getSaving to show on Front-End.", e);
			rv = 0;
			//throw new PublicationNotSelectedException(e.getMessage());
		}	
		return rv;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof Deal))
            return false;
        Deal castOther = (Deal) other;

        return new EqualsBuilder()
        		.append(id, castOther.id)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("title", title)
        	.append("titleNewsletter", titleNewsletter)
        	.append("percentDiscount", percentDiscount)
        	.append("contractDate", contractDate)
        	.append("startDate", startDate)
        	.append("endDate", endDate)
        	.append("discountDuration", discountDuration)
        	.append("status", status)
        	.append("totPurchases", totPurchases)
        	.append("confirmed", confirmed)
        	.append("couponsPrepared", couponsPrepared)
        	.append("order", order)
        	.append("partner", partner)
			.append("clientFullName", clientFullName)
			.append("clientCel", clientCel)
			.append("brokerFullName", brokerFullName)
			.append("brokerCel", brokerCel)
        	.append("couponImmediately", couponImmediately)
        	.append("mainImgName", mainImgName)
        	.append("synthesis", synthesis)
        	.append("conditions", conditions)
        	.append("description", description)
			.append("approvedForPublish", approvedForPublish)
			.append("approvedUser", approvedUser)
			.append("approvedDate", approvedDate)
			.append("lastUpdate", lastUpdate)
        	.append("lastUpdateUser", lastUpdateUser)   
        	.append("contractComment", contractComment)
        	.append("accounted", accounted)
        	.append("uploadedFiles", uploadedFiles)
        	.append("choices", choices)
        	.toString();
    }
	
	//Used on dealWizard to permit or avoid data modification
	public boolean isEditable() {
		boolean retVal = true;
		if (status == null){
			return true;
		}
		
		if(approvedForPublish){
			retVal = false;	
		}else{
			retVal = true;	
		}		
		
		return retVal;
	}
	
	//Used on PublicationCalendar to permit or avoid event moviment in the calendar
	// The deal is not movable, start/end date can not be changed, when
	// 1. status active, 2. status closed, 3. Unlimited deal, so endDate==null 
	public boolean isMovable() {
		boolean retVal = true;		
		if (status.equals(Deal.STATUS_ACTIVE)) {
			retVal = false;
		} else if (status.equals(Deal.STATUS_CLOSED)) {
			retVal = false;
		} else if(endDate == null){
			retVal = false;
		} else if (status.equals(Deal.STATUS_WAITING)) {
			retVal = true;	
		} 
		return retVal;
	}

	public int getMinCustomers() {
		int minCustomers = 0;
		for (DealChoice dc : choices) {
			minCustomers = minCustomers + dc.getMinCustomers();
		}
		return minCustomers;
	}

	public int getMaxCustomers() {
		int maxCustomers = 0;
		for (DealChoice dc : choices) {
			maxCustomers = maxCustomers + dc.getMaxCustomers();
		}
		return maxCustomers;
	}

	public List<String> getDealCities() {
		return dealCities;
	}

	public void setDealCities(List<String> dealCities) {
		this.dealCities = dealCities;
	}
	
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
     * @return missing second until the deal expires. Used for the countdown.
     */
    public long getMissingSeconds(){
    	long sec;
		try {
			//skip to 00:00 of the next day, when the deal suppose to ends.
			Date endPub = ZUtils.addDaysToDate(ZUtils.getMidnightForDate(endDate), 1);
			Date now = new Date();  	
			sec = (endPub.getTime() - now.getTime()) / 1000;
		} catch (Exception e) {
			//logger.error("Can not calculate missing seconds for deal:"+id, e);
			sec = 0;
		}
    	return sec;
    }
//	public void addCityForDeal(City city) {
//		dealCities.add(city);
//	}
	
}
