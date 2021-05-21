package al.ozone.bl.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.utils.ZUtils;

public class CopyOfDealReport implements Serializable {

	private static final long serialVersionUID = 7409486303345043724L;
	protected static final transient Log logger = LogFactory.getLog(CopyOfDealReport.class);

	private Integer dealId; //
	private Integer choiceNr; //
	private String choiceTitle; //
	private Date contractDate; //
	private Date endDate; //
	private int discountDuration;//3, 6 or 12 months
	private Date offerExpiration; //took from coupon 
	
	private int fullPrice; //
	private int priceBuyOzone; //*
	private int priceSellOZone; //
	private int percentDiscount; 
	private double percentCommission; //
	private int singleEarning; // *
	
	private int nrPurchaseCash; //
	private int nrPurchasePayPal; //
	private int nrPurchaseTotal; //
	private int nrCouponsPayed; //
	private int nrCouponsCanceled; //
	private int nrCouponsExpired;
	private int nrCouponsStillToPay;
	
	private int totBonusUsed; //
	private int totCash;
	private int totPayPal;
	private int totCashPayPal;
	private int totAmount;
	private int totForPartner;
	private int totEarning;
	private int totPayed;//Likuiduar
	private int totToPay;//Lek Per te likuiduar
	private int totRemainFromPartners; //Gjendje nga partneret
	private int totEarningForPayedCoupons; //Fitimi per kupont e likuiduar
	private int totEarningForExpiredCoupons;// Fitim/Mbetje nga te skaduarit
	private int absEarnings;
	
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
	
	public void setPercentCommission(double percentCommission) {
		this.percentCommission = percentCommission;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDiscountDuration() {
		return discountDuration;
	}

	public void setDiscountDuration(int discountDuration) {
		this.discountDuration = discountDuration;
	}

	public Date getOfferExpiration() {
		return offerExpiration;
	}

	public void setOfferExpiration(Date offerExpiration) {
		this.offerExpiration = offerExpiration;
	}

	public int getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(int fullPrice) {
		this.fullPrice = fullPrice;
	}

	//sellOZone - singleEarning
	public int getPriceBuyOzone() {
		priceBuyOzone = priceSellOZone - getSingleEarning();
		return priceBuyOzone;
	}

	public void setPriceBuyOzone(int priceBuyOzone) {
		this.priceBuyOzone = priceBuyOzone;
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

	public void setPercentCommission(int percentCommission) {
		this.percentCommission = percentCommission;
	}

	//sellOZone * commission / 100
	public int getSingleEarning() {
		BigDecimal se = new BigDecimal(priceSellOZone);
		se = se.multiply(new BigDecimal(percentCommission));
		se = se.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
		singleEarning = se.intValue();
		return singleEarning;
	}

	public void setSingleEarning(int singleEarning) {
		this.singleEarning = singleEarning;
	}

	public int getNrPurchaseCash() {
		return nrPurchaseCash;
	}

	public void setNrPurchaseCash(int nrPurchaseCash) {
		this.nrPurchaseCash = nrPurchaseCash;
	}

	public int getNrPurchasePayPal() {
		return nrPurchasePayPal;
	}

	public void setNrPurchasePayPal(int nrPurchasePayPal) {
		this.nrPurchasePayPal = nrPurchasePayPal;
	}

	public int getNrPurchaseTotal() {
		return nrPurchaseTotal;
	}

	public void setNrPurchaseTotal(int nrPurchaseTotal) {
		this.nrPurchaseTotal = nrPurchaseTotal;
	}

	public int getTotBonusUsed() {
		return totBonusUsed;
	}

	public void setTotBonusUsed(int totBonusUsed) {
		this.totBonusUsed = totBonusUsed;
	}

	public int getTotCash() {
		totCash = priceSellOZone * nrPurchaseCash;
		return totCash;
	}

	public void setTotCash(int totCash) {
		this.totCash = totCash;
	}

	public int getTotPayPal() {
		totPayPal = priceSellOZone * nrPurchasePayPal;
		return totPayPal;
	}

	public void setTotPayPal(int totPayPal) {
		this.totPayPal = totPayPal;
	}

	public int getTotCashPayPal() {
		totCashPayPal = totCash + totPayPal - totBonusUsed;
		return totCashPayPal;
	}

	public void setTotCashPayPal(int totCashPayPal) {
		this.totCashPayPal = totCashPayPal;
	}

	public int getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(int totAmount) {
		this.totAmount = totAmount;
	}

	public int getTotForPartner() {
		totForPartner = priceSellOZone * nrPurchaseTotal;
		return totForPartner;
	}

	public void setTotForPartner(int totForPartner) {
		this.totForPartner = totForPartner;
	}

	public int getTotEarning() {
		totEarning = totCashPayPal - totForPartner;
		return totEarning;
	}

	public void setTotEarning(int totEarning) {
		this.totEarning = totEarning;
	}

	public int getNrCouponsPayed() {
		return nrCouponsPayed;
	}

	public void setNrCouponsPayed(int nrCouponsPayed) {
		this.nrCouponsPayed = nrCouponsPayed;
	}

	public int getNrCouponsCanceled() {
		return nrCouponsCanceled;
	}

	public void setNrCouponsCanceled(int nrCouponsCanceled) {
		this.nrCouponsCanceled = nrCouponsCanceled;
	}

	public int getNrCouponsExpired() {
		return nrCouponsExpired;
	}

	public void setNrCouponsExpired(int nrCouponsExpired) {
		this.nrCouponsExpired = nrCouponsExpired;
	}

	public int getNrCouponsStillToPay() {
		nrCouponsStillToPay = nrPurchaseTotal - nrCouponsPayed - nrCouponsCanceled - nrCouponsExpired;
		return nrCouponsStillToPay;
	}

	public void setNrCouponsStillToPay(int nrCouponsStillToPay) {
		this.nrCouponsStillToPay = nrCouponsStillToPay;
	}

	public int getTotPayed() {
		totPayed = priceBuyOzone * nrCouponsPayed;
		return totPayed;
	}

	public void setTotPayed(int totPayed) {
		this.totPayed = totPayed;
	}

	public int getTotToPay() {
		totToPay = priceBuyOzone * nrCouponsStillToPay;
		return totToPay;
	}

	public void setTotToPay(int totToPay) {
		this.totToPay = totToPay;
	}

	public int getTotRemainFromPartners() {
		totRemainFromPartners = totForPartner - totPayed;
		return totRemainFromPartners;
	}

	public void setTotRemainFromPartners(int totRemainFromPartners) {
		this.totRemainFromPartners = totRemainFromPartners;
	}

	public int getTotEarningForPayedCoupons() {
		totEarningForPayedCoupons = singleEarning * nrCouponsPayed;
		return totEarningForPayedCoupons;
	}

	public void setTotEarningForPayedCoupons(int totEarningForPayedCoupons) {
		this.totEarningForPayedCoupons = totEarningForPayedCoupons;
	}

	public int getTotEarningForExpiredCoupons() {
		totEarningForExpiredCoupons = priceSellOZone * nrCouponsExpired;
		return totEarningForExpiredCoupons;
	}

	public void setTotEarningForExpiredCoupons(int totEarningForExpiredCoupons) {
		this.totEarningForExpiredCoupons = totEarningForExpiredCoupons;
	}
	
	public int getAbsEarnings() {
		absEarnings = totEarningForPayedCoupons + totEarningForExpiredCoupons;
		return absEarnings;
	}

	public void setAbsEarnings(int absEarnings) {
		this.absEarnings = absEarnings;
	}
	
	public String getColorExpiration() {
		String rv = null;
		
		boolean isExpired = ZUtils.isEarlierDate(offerExpiration);
		if(isExpired) {
			rv = "dealOfferExpiration";
		}
		return rv;
	}

}
