package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Publication;

@FacesConverter("statusPubFormatter")
public class StatusPubFormatter implements Converter{

	protected static final transient Log logger = LogFactory.getLog(StatusPubFormatter.class);

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

		String retVal = "";
		String statusCode = String.valueOf(value);
		if(statusCode.equals(Publication.STATUS_WAITING)){
			retVal = bundle.getString("publication.status.waiting");
		}else if(statusCode.equals(Publication.STATUS_ACTIVE)){
			retVal = bundle.getString("publication.status.active");
		}else if(statusCode.equals(Publication.STATUS_CLOSED)){
			retVal = bundle.getString("publication.status.closed");
		}
		return retVal;
	}

	public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) {
		logger.debug("Need the object from Formatter val: "+arg2);
		return null;
	}	
	
	public static String convertToString(String statusCode){
		String retVal = "";
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

		if(statusCode.equals(Publication.STATUS_WAITING)){
			retVal = bundle.getString("publication.status.waiting");
		}else if(statusCode.equals(Publication.STATUS_ACTIVE)){
			retVal = bundle.getString("publication.status.active");
		}else if(statusCode.equals(Publication.STATUS_CLOSED)){
			retVal = bundle.getString("publication.status.closed");
		}
		
		return retVal;
	}
	
}
