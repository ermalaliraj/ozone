package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import al.ozone.bl.utils.ZUtils;

public class Orders implements Serializable{

	private static final long serialVersionUID = 1797805846669017353L;
	public static final String TYPE_COURIER = "COURIER";
	public static final String TYPE_PAYPAL = "PAYPAL";
	public static final String TYPE_OFFICE = "OFFICE";
	public static final String TYPE_EASYPAY = "EASYPAY";
	public static final String TYPE_BANK = "BANK";
	
	private Integer id;
	private DealChoice dealChoice;
    private Customer customer; 
    private int quantity; 
    private int singlePrice;
    private int moneySpent;
    private int creditUsed;
    private Date date;
    private String address;
    private String tel;
    private String exchangeRate;
    private String orderType;
    private Integer purchaseId;
    private String note;
    private boolean canceled;
    
	public Orders(){
		dealChoice = new DealChoice();
    	customer = new Customer();
    }
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public DealChoice getDealChoice() {
		return dealChoice;
	}
	public void setDealChoice(DealChoice dealChoice) {
		this.dealChoice = dealChoice;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getMoneySpent() {
		return moneySpent;
	}
	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}
	public int getCreditUsed() {
		return creditUsed;
	}
	public void setCreditUsed(int creditUsed) {
		this.creditUsed = creditUsed;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(int singlePrice) {
		this.singlePrice = singlePrice;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Integer getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}
	public boolean isPurchaseDone(){
		return  purchaseId != null;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public boolean isDealActive(){
		if(dealChoice.getDeal().getStatus().equals(Deal.STATUS_ACTIVE)){
			return true;
		} else{
			return false;
		}
	}
	
	public String getColorByPurchase(){
		String rv = "";
		if(canceled){
			rv = "dealError";
		} else
		if(purchaseId==null){
			if(!ZUtils.isEmptyString(note)){	
				rv = "dealOfferExpiration";
			}else{
				rv = "dealNotApproved";
			}
		}
		return rv;
	}
	
	public boolean equals(final Object obj) {
	        if (!(obj instanceof Orders))
	            return false;
	        Orders other = (Orders) obj;
	        return new EqualsBuilder().append(id, other.id)
					.isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder().append(id)
	        		.toHashCode();
	    }

		public String toString() {
	        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	        	.appendSuper(super.toString())
	        	.append("id", id)
	        	.append("quantity", quantity)
	        	.append("singlePrice", singlePrice)
	        	.append("moneySpent", moneySpent)
	        	.append("creditUsed", creditUsed)
	        	.append("date", date)
				.append("address", address)
				.append("tel", tel)
				.append("exchangeRate", exchangeRate)
				.append("orderType", orderType)
				.append("purchaseId", purchaseId)
				.append("dealChoice", dealChoice)
				.append("customer", customer)
				.append("note", note)
				.append("canceled", canceled)
				.toString();
	    }
}
