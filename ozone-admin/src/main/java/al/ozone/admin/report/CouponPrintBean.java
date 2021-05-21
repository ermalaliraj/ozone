package al.ozone.admin.report;

public class CouponPrintBean implements IPrintBean {

	private String ozoneLogo;
	private String mainImage;
	private String dealTitle;
	private int fullPrice;
	private String couponCode;
	private String couponFrom;
	private String couponTo;
	private String securityCode;
	private String partnerName;
	private String partnerAdress;
	private String dealSynthesis;
	private String dealConditions;
	private Float lat;
	private Float lng;
	private Integer zoom;

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getDealTitle() {
		return dealTitle;
	}

	public void setDealTitle(String dealTitle) {
		this.dealTitle = dealTitle;
	}

	public int getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(int fullPrice) {
		this.fullPrice = fullPrice;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponFrom() {
		return couponFrom;
	}

	public void setCouponFrom(String couponFrom) {
		this.couponFrom = couponFrom;
	}

	public String getCouponTo() {
		return couponTo;
	}

	public void setCouponTo(String couponTo) {
		this.couponTo = couponTo;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerAdress() {
		return partnerAdress;
	}

	public void setPartnerAdress(String partnerAdress) {
		this.partnerAdress = partnerAdress;
	}

	public String getDealSynthesis() {
		return dealSynthesis;
	}

	public void setDealSynthesis(String dealSynthesis) {
		this.dealSynthesis = dealSynthesis;
	}

	public String getDealConditions() {
		return dealConditions;
	}

	public void setDealConditions(String dealConditions) {
		this.dealConditions = dealConditions;
	}

	public String getOzoneLogo() {
		return ozoneLogo;
	}

	public void setOzoneLogo(String ozoneLogo) {
		this.ozoneLogo = ozoneLogo;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
}
