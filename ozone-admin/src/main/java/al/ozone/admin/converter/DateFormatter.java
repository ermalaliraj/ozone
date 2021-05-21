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
 

@FacesConverter("dateFormatter")
public class DateFormatter implements Converter{

protected static final transient Log logger = LogFactory.getLog(DateFormatter.class);
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = dateFormat.parse(value);
		} catch (ParseException e) {
			logger.error("Can not convert "+value+" to Date.", e);
		}
		
		return date;
	}
	 
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Date date = (Date) value;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
        String s1 = "";
		try {
			s1 = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Date to show as string is null");
		}
        
		return s1;
	}		
}
