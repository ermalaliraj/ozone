package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DealPurchasesReport implements Serializable {

	private static final long serialVersionUID = 7409486303345043724L;
	protected static final transient Log logger = LogFactory.getLog(DealPurchasesReport.class);

	private Integer dealId; //
	private Integer choiceNr; //
	private String choiceTitle; //
	private String partner;
	private Date contractDate; //
	private Date startDate; //
	private Date offerExpiration; //took from coupon 
	
	private int percentDiscount; 
	private int priceSellOZone; //	
	private double percentCommission; //
	private int singleEarning; // *
	private int nrPurchaseTotal; //
	private int totAmount;
	private int totEarning;
	private String agentName;
	private int agentEarning;
	
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

	public String getChoiceTitle() {
		return choiceTitle;
	}

	public void setChoiceTitle(String choiceTitle) {
		this.choiceTitle = choiceTitle;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getOfferExpiration() {
		return offerExpiration;
	}

	public void setOfferExpiration(Date offerExpiration) {
		this.offerExpiration = offerExpiration;
	}

	public int getPriceSellOZone() {
		return priceSellOZone;
	}

	public void setPriceSellOZone(int priceSellOZone) {
		this.priceSellOZone = priceSellOZone;
	}

	public int getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(int percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public double getPercentCommission() {
		return percentCommission;
	}

	public void setPercentCommission(double percentCommission) {
		this.percentCommission = percentCommission;
	}

	public int getSingleEarning() {
		return singleEarning;
	}

	public void setSingleEarning(int singleEarning) {
		this.singleEarning = singleEarning;
	}

	public int getNrPurchaseTotal() {
		return nrPurchaseTotal;
	}

	public void setNrPurchaseTotal(int nrPurchaseTotal) {
		this.nrPurchaseTotal = nrPurchaseTotal;
	}
	
	public int getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(int totAmount) {
		this.totAmount = totAmount;
	}

	public int getTotEarning() {
		return totEarning;
	}

	public void setTotEarning(int totEarning) {
		this.totEarning = totEarning;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int getAgentEarning() {
		return agentEarning;
	}

	public void setAgentEarning(int agentEarning) {
		this.agentEarning = agentEarning;
	}
	
}
