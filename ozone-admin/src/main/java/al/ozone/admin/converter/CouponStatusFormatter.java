package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Coupon;
 
@FacesConverter("couponStatusFormatter")
public class CouponStatusFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(CouponStatusFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

		String retVal = "";
		String statusCode = String.valueOf(value);
		if(statusCode.equals(Coupon.STATUS_NOT_USED)){
			retVal = bundle.getString("coupon.status.notUsed");
		}else if(statusCode.equals(Coupon.STATUS_USED)){
			retVal = bundle.getString("coupon.status.used");
		}else if(statusCode.equals(Coupon.STATUS_EXPIRED)){
			retVal = bundle.getString("coupon.status.expired");
		}else if(statusCode.equals(Coupon.STATUS_RETURNED)){
			retVal = bundle.getString("coupon.status.returned");
		}
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter format "+arg2);
		return null;
	}	
	
}
