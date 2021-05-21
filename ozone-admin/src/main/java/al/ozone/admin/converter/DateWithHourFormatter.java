package al.ozone.admin.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

@FacesConverter("dateWithHourFormatter")
public class DateWithHourFormatter implements Converter{

protected static final transient Log logger = LogFactory.getLog(DateWithHourFormatter.class);
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			date = dateFormat.parse(value);
		} catch (ParseException e) {
			logger.error("Can not convert "+value+" to Date.", e);
		}
		
		return date;
	}
	 
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value==null){
			return "";
		}
		Date date = (Date) value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String s1 = dateFormat.format(date);
        
		return s1;
	}		
}
