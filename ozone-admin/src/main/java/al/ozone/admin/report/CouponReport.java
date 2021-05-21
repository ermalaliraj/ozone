package al.ozone.admin.report;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Partner;

public class CouponReport extends AbstractReport {

	private CouponReportDatasource datasource;
	private CouponReportParams params;
	private CouponPrintBean printBean = new CouponPrintBean();

	public CouponReport(CouponReportParams params) {
		this("Coupon Report", params);
	}

	public CouponReport(String name, CouponReportParams params) {
		this.name = name;
		this.params = params;
	}

	public byte[] getStreamReport() {

		fillPrintBean();

		CouponReportDatasource ds = new CouponReportDatasource(
				Arrays.asList(printBean), params);
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				params.getJasperFile());
		try {
			return createReport(is, ds, null);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void fillPrintBean() {

		DealChoice dealChoice = params.getDealChoice();
		Deal deal = dealChoice.getDeal();
		Coupon coupon = params.getCoupon();
		Partner partner = deal.getPartner();

		printBean.setOzoneLogo(params.getOzoneLogo());
		printBean
				.setMainImage(params.getUploadFolder() + deal.getMainImgName());
		printBean.setDealTitle(dealChoice.getDealTitle());
		printBean.setFullPrice(dealChoice.getFullPrice());
		printBean.setCouponCode(coupon.getCode());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		printBean.setCouponFrom(dateFormat.format(coupon.getFrom()));
		printBean.setCouponTo(dateFormat.format(coupon.getTo()));
		printBean.setSecurityCode(coupon.getSecurityCode());
		printBean.setPartnerName(deal.getPartner().getName());
		printBean.setPartnerAdress(deal.getPartner().getAddress());
		printBean.setDealSynthesis(htmlToPlainText(deal.getSynthesis()));
		printBean.setDealConditions(htmlToPlainText(deal.getConditions()));
		printBean.setLat(Float.parseFloat(new Double(partner.getLat())
				.toString()));
		printBean.setLng(Float.parseFloat(new Double(partner.getLng())
				.toString()));
		printBean.setZoom(partner.getZoomLevel());

	}

	private byte[] createReport(InputStream is, JRDataSource ds,
			Map<String, Object> params) throws JRException {

		JasperReport jasperReport;
		JasperPrint jasperPrint;

		jasperReport = JasperCompileManager.compileReport(is);

		long start = System.currentTimeMillis();

		jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);

		System.out.println("Report Filling Time : "
				+ (System.currentTimeMillis() - start));

		// JasperExportManager.exportReportToPdfStream(jasperPrint, os);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public CouponReportDatasource getDatasource() {
		return datasource;
	}

	public CouponReportParams getParams() {
		return params;
	}

	public CouponPrintBean getPrintBean() {
		return printBean;
	}

	public static String htmlToPlainText(String html) {
		String plain = html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
		return plain.substring(1);
	}
}
