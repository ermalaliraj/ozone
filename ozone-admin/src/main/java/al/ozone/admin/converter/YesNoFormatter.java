package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@FacesConverter("yesNoFormatter")
public class YesNoFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(CurrencyFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		String yes = bundle.getString("yes");
		String no = bundle.getString("no");
		
		boolean boolVal = (Boolean) value;
		
		String retVal = "";
		if(boolVal==true){
			retVal = yes;
		}else{
			retVal = no;
		}
		
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter format "+arg2);
		return null;
	}	
	
	public static String convertToString(boolean value){
		String retVal = "";
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		String yes = bundle.getString("yes");
		String no = bundle.getString("no");

		if(value==true){
			retVal = yes;
		}else{
			retVal = no;
		}
		
		return retVal;
	}
	
}
