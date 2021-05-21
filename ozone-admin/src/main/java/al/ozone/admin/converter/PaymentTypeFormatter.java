package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Payment;
 
@FacesConverter("paymentTypeFormatter")
public class PaymentTypeFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(PaymentTypeFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {

		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		String type = value.toString();
		String retVal = "";
		
		if (type.equals(Payment.TYPE_CASH)){
			retVal = bundle.getString("payment.type.cash");
		}else if (type.equals(Payment.TYPE_EASY_PAY)){ 
			retVal = bundle.getString("payment.type.easypay");
		}else if (type.equals(Payment.TYPE_PAYPAL)){ 
			retVal = bundle.getString("payment.type.paypal");
		}else if (type.equals(Payment.TYPE_BANK)){ 
			retVal = bundle.getString("payment.type.bank");
		}else if (type.equals(Payment.TYPE_AMERICAN_EXPRESS)){ 
			retVal = bundle.getString("payment.type.ae");
		}else{
			retVal = "??? Unknown Payment Type ???";
		}
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter format "+arg2);
		return null;
	}	
	
}
