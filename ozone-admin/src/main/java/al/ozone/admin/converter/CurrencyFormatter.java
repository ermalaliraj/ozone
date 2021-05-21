package al.ozone.admin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.utils.ZUtils;
 
@FacesConverter("currencyFormatter")
public class CurrencyFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(CurrencyFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		//logger.debug("to format % : "+value);
		double d = (Double)value;
		double roundVal = ZUtils.roundToDecimals(d, 0);
		int roundInt = (int) roundVal;
		
		String retVal = " %";		
		if(roundInt == Integer.MAX_VALUE){
			retVal = "NaN";
		}else		
		if(roundInt == 0){
			retVal = "--";
		}else{
			retVal = roundInt + retVal;
		}
		
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter format "+arg2);
		return null;
	}	
	
}
