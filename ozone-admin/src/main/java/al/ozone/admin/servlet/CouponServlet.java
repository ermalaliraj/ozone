package al.ozone.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import al.ozone.admin.report.AbstractReport;
import al.ozone.admin.report.CouponReport;
import al.ozone.admin.report.CouponReportParams;
import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.DealChoice;

public class CouponServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(CouponServlet.class);
	private AbstractReport report;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String jasperFile = "coupon.jrxml";
		String couponId = request.getParameter("couponId");
		String ozoneLogo = "http://www.ozone.al/image/Logo_Ozone_Kupon.png";
		String uploadsFolder = "http://www.ozone.al/uploads/";
		ServletOutputStream os = response.getOutputStream();
		logger.debug("showing pdf coupon:" + couponId);
		byte[] content = null;
		if (couponId != null) {
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext());

			CouponManager couponManager = (CouponManager) ctx
					.getBean("couponManager");
			Coupon coupon = couponManager.get(couponId);

			DealManager dealManager = (DealManager) ctx.getBean("dealManager");
			DealChoice dealChoice = dealManager.getDealChoice(
					coupon.getDealId(), coupon.getDealChoiceNr());

			report = new CouponReport(new CouponReportParams(jasperFile,
					coupon, ozoneLogo, dealChoice, uploadsFolder));
			content = report.getStreamReport();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ "OZoneCoupon_"+coupon.getCode() + ".pdf\"");
		}else{
			response.setContentType("text/plain");
			content = "Do not know which is the Invoice to print!"
					.getBytes();
		}
		if (content == null) {
			response.setContentType("text/plain");
			content = "Report does not contain values!".getBytes();
		}
		os.write(content);
		os.flush();
		os.close();
	}
}
