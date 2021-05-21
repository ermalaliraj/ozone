package al.ozone.bl.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Payment {

	public static final String TYPE_CASH = "1";
	public static final String TYPE_EASY_PAY = "2";
	public static final String TYPE_PAYPAL = "3";
	public static final String TYPE_BANK = "4";
	public static final String TYPE_AMERICAN_EXPRESS = "5";
	
	protected Integer id;
	protected String paymentType;
	protected int amount;
	protected Date operationDate;
	private Purchase purchase;

	public abstract String getPaymentType();

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("amount", amount)
        	.append("operationDate", operationDate)
        	.append("paymentType", paymentType)
        	.append("purchase", purchase)
        	.toString();
    }

}
