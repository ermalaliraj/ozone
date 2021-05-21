package al.ozone.bl.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DealChoice implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(DealChoice.class);
	
	private Integer dealId;
	private Integer choiceNr;
	private String id;
	
	private String dealTitle;
	private String choiceTitle;
	private int price;
	private int fullPrice;
	private double commission;
	private int minCustomers;
	private int maxCustomers;	
	private int percentDiscount;
	private int totPurchases;
	private Deal deal;
	
	public DealChoice(){
		//uploadedFiles = new ArrayList<String>();
	}
	
	public DealChoice(Integer dealId, Integer choiceNr) {
		this.dealId = dealId;
		this.choiceNr = choiceNr;
	}

	public String getId() {
		String rv = ""+dealId+"-"+choiceNr;
		return rv;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDealTitle() {
		return dealTitle;
	}

	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}

	public String getChoiceTitle() {
		return choiceTitle;
	}

	public void setChoiceTitle(String choiceTitle) {
		this.choiceTitle = choiceTitle;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(int fullPrice) {
		this.fullPrice = fullPrice;
	}

	public int getMinCustomers() {
		return minCustomers;
	}

	public void setMinCustomers(int minCustomers) {
		this.minCustomers = minCustomers;
	}

	public int getMaxCustomers() {
		return maxCustomers;
	}

	public void setMaxCustomers(int maxCustomers) {
		this.maxCustomers = maxCustomers;
	}

	public int getTotPurchases() {
		return totPurchases;
	}

	public void setTotPurchases(int totPurchases) {
		this.totPurchases = totPurchases;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}
		
	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	/**
	 * Return percentage calculated as 100-(d.price/d.fullPrice*100)
	 * @return percentage calculated as 100-(d.price/d.fullPrice*100)
	 */
	public int getPercentDiscount() {
		BigDecimal priceB = new BigDecimal(price);
		BigDecimal fullPriceB = new BigDecimal(fullPrice);
//		System.out.println("priceB: "+priceB);
//		System.out.println("fullPriceB: "+fullPriceB);
	
		try {
			priceB = priceB.divide(fullPriceB, 2, BigDecimal.ROUND_HALF_UP);
			priceB = priceB.multiply(new BigDecimal(100));
			percentDiscount = 100 - priceB.intValue();
		} catch (Exception e) {
			logger.error("Error calculating 'percentDiscount' for deal:"+getDealId()+", with fullPriceB="+fullPriceB+" and priceB="+priceB+". Error:"+e.getMessage());
		}
		return percentDiscount;
	}
	
	/**
	 * Returns the value that the customer saved from the original price.
	 * @return fullPrice - price
	 */
	public int getValueDiscount() {
		int totValueSaved = fullPrice - price;
		return totValueSaved;
	}

	public void setPercentDiscount(int percentDiscount) {
		this.percentDiscount = percentDiscount;
	}
	
	public boolean equals(final Object other) {
        if (!(other instanceof DealChoice))
            return false;
        DealChoice castOther = (DealChoice) other;

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
        	.append("dealTitle", dealTitle)
        	.append("choiceTitle", choiceTitle)
        	.append("price", price)
        	.append("fullPrice", fullPrice)
        	.append("commission", commission)
        	.append("minCustomers", minCustomers)
        	.append("maxCustomers", maxCustomers)
        	.append("percentDiscount", percentDiscount)
        	.append("totPurchases", totPurchases)
        	.append("deal", deal)
        	.toString();
    }

	public String getNormalizedTitle() {
		String str = choiceTitle;
//		str = str.replace('?', 'C');
//		str = str.replace('?', 'c');
//		str = str.replace('?', 'e');
		
		return str;
	}
	
	public Object clone() {
		DealChoice dc = new DealChoice();
		dc.dealId = this.dealId;
		dc.choiceNr = this.choiceNr;
		dc.id = this.id;
		dc.dealTitle = this.dealTitle;
		dc.choiceTitle = this.choiceTitle;
		dc.price = this.price;
		dc.fullPrice = this.fullPrice;
		dc.minCustomers = this.minCustomers;
		dc.maxCustomers = this.maxCustomers;
		dc.percentDiscount = this.percentDiscount;
		dc.totPurchases = this.totPurchases;
		dc.deal = this.deal;
		return dc;
	}

}
