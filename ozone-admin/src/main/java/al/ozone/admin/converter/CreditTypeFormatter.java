package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Credit;

@FacesConverter("creditTypeFormatter")
public class CreditTypeFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(CreditTypeFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

		String retVal = "-1";
		String type = String.valueOf(value);
		if(type.equals(Credit.TYPE_BENEFIT)){
			retVal = bundle.getString("credit.type.benefit");
		}else if(type.equals(Credit.TYPE_REIMBORSEMENT)){
			retVal = bundle.getString("credit.type.reimborsement");
		}else if(type.equals(Credit.TYPE_DIFFERENCE)){
			retVal = bundle.getString("credit.type.difference");
		}
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter val: "+arg2);
		return null;
	}	
	
}
