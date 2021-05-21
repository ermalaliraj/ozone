package al.ozone.admin.report;

import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.DealChoice;

public class CouponReportParams extends AbstractParams {

	private Coupon coupon;
	private String jasperFile;
	private String ozoneLogo;
	private String uploadFolder;
	private DealChoice dealChoice;

	public CouponReportParams(String jasperFile, Coupon coupon,
			String ozoneLogo, DealChoice dealChoice, String uploadFolder) {
		this.coupon = coupon;
		this.jasperFile = jasperFile;
		this.ozoneLogo = ozoneLogo;
		this.dealChoice = dealChoice;
		this.uploadFolder = uploadFolder;
	}

	public String getJasperFile() {
		return jasperFile;
	}

	public void setJasperFile(String jasperFile) {
		this.jasperFile = jasperFile;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getOzoneLogo() {
		return ozoneLogo;
	}

	public void setOzoneLogo(String ozoneLogo) {
		this.ozoneLogo = ozoneLogo;
	}

	public DealChoice getDealChoice() {
		return dealChoice;
	}

	public void setDealChoice(DealChoice dealChoice) {
		this.dealChoice = dealChoice;
	}

	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

}
