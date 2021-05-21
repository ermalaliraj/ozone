package al.ozone.admin.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import al.ozone.bl.manager.OrdersManager;
import al.ozone.bl.manager.ReportManager;
import al.ozone.bl.model.Orders;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class StreamServlet.
 */
public class StreamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(StreamServlet.class);

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream servletOutputStream = response.getOutputStream();
		byte[] content = null;
		try {
			String jasperFile = "invoiceReport.jrxml";
			String orderId = request.getParameter("orderId");
			logger.debug("showing pdf invoice for order:" + orderId);
			if (orderId != null) {
				ApplicationContext ctx = WebApplicationContextUtils
						.getWebApplicationContext(this.getServletContext());

				ReportManager reportManager = (ReportManager) ctx
						.getBean("reportManager");
				OrdersManager ordersManager = (OrdersManager) ctx
						.getBean("ordersManager");

				Orders order = ordersManager.get(Integer.parseInt(orderId));

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("ORDER_ID", orderId);
				content = reportManager.createReportPdf(jasperFile, params);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment; filename=invoice_"
								+ order.getDealChoice().getDealId() + "-"
								+ order.getDealChoice().getChoiceNr() + "_"
								+ orderId + ".pdf");
			} else {
				response.setContentType("text/plain");
				content = "Do not know which is the Invoice to print!"
						.getBytes();
			}

			if (content == null) {
				response.setContentType("text/plain");
				content = "Report does not contain values!".getBytes();
			}
			servletOutputStream.write(content);
			servletOutputStream.flush();
		} catch (Exception e) {
			logger.error("Exception during servlet streaming: ", e);
		} finally {
			servletOutputStream.close();
		}
	}
}
