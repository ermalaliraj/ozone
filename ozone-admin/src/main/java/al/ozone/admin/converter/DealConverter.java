package al.ozone.admin.converter;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import al.ozone.admin.backing.PublicationCalController;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Deal;

@FacesConverter(forClass=PublicationCalController.class,value="dealConverter")
public class DealConverter implements Converter {

   public String getAsString(FacesContext context, UIComponent component, Object value) {
       String val = null;
       try {
    	   Deal deal = (Deal) value;
           val = Integer.toString(deal.getId());
       }catch(Throwable ex) {
           ResourceBundle bundle = ResourceBundle.getBundle("messages");
           FacesMessage msg = new FacesMessage(bundle.getString("deal.convertion_error"));
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ConverterException(msg);
       }
       return val;
   }
   
   public Object getAsObject(FacesContext context, UIComponent arg1, String value) {
	   Deal deal = null;
	   DealManager dealManager = null;
       try {
    	   ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(context);
    	   dealManager = (DealManager) ctx.getBean("dealManager");
    	   deal = dealManager.get(Integer.parseInt(value));
       }catch(Throwable ex) {
           ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
           FacesMessage msg = new FacesMessage(bundle.getString("deal.convertion_error"));
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ConverterException(msg);
       }
       return deal;
   }
}
