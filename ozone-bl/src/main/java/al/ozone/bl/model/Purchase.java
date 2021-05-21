package al.ozone.bl.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Purchase implements Serializable {
	
	private static final long serialVersionUID = -4880116250931983662L;
	
	private Integer id;
	private int quantity;
	private int amount;
	private int totAmount;//quantity * amount
	private int moneySpent;
	private BigDecimal moneySpentEUR;  // used on FE to show euro in purchase time
	private int creditSpent;
	private boolean confirmed;
	private Date purchDate;
	private Integer orderId;
	private DealChoice dealChoice;
	private Customer customer;
	private Payment payment;
	private List<Credit> credits;
	private DiscountCard discount;
	private List<Coupon> coupons;
	private boolean feedbackRequested;

	public Purchase(){
		discount = new DiscountCard();
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
	public Date getPurchDate() {
		return purchDate;
	}
	public void setPurchDate(Date purchDate) {
		this.purchDate = purchDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getMoneySpent() {
		return moneySpent;
	}
	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}
	public int getCreditSpent() {
		return creditSpent;
	}
	public void setCreditSpent(int creditSpent) {
		this.creditSpent = creditSpent;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public List<Credit> getCredits() {
		return credits;
	}
	public void setCredits(List<Credit> credits) {
		this.credits = credits;
	}
	public DiscountCard getDiscount() {
		return discount;
	}
	public void setDiscount(DiscountCard discount) {
		this.discount = discount;
	}
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public List<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	public int getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(int totAmount) {
		this.totAmount = totAmount;
	}
	public BigDecimal getMoneySpentEUR() {
		return moneySpentEUR;
	}
	public void setMoneySpentEUR(BigDecimal moneySpentEUR) {
		this.moneySpentEUR = moneySpentEUR;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public boolean isFeedbackRequested() {
		return feedbackRequested;
	}
	public void setFeedbackRequested(boolean feedbackRequested) {
		this.feedbackRequested = feedbackRequested;
	}

	public int getSaving(){
		int singleValDiscount = dealChoice.getValueDiscount();
		int fullValDiscount = singleValDiscount * quantity;		
		return fullValDiscount;
	}
	
	public int getTotValidCoupons(){
		int count = 0;
		for (Coupon c : coupons) {
			if(c.isValidCoupon()){
				count ++;
			}
		}
		return count;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof Purchase))
            return false;
        Purchase castOther = (Purchase) other;
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
        	.append("quantity", quantity)
        	.append("amount", amount)
        	.append("totAmount", totAmount)
        	.append("moneySpent", moneySpent)
        	.append("moneySpentEUR", moneySpentEUR)        	
        	.append("creditSpent", creditSpent)
        	.append("confirmed", confirmed)
        	.append("purchDate", purchDate)
        	.append("payment", payment)
        	.append("credits", credits)
        	.append("discount", discount)
        	.append("dealChoice", dealChoice)
        	.append("customer", customer)
        	.append("coupons", coupons)
        	.append("feedbackRequested", feedbackRequested)
        	.toString();
    }
}
