package al.ozone.admin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



import al.ozone.admin.backing.UserController;
import al.ozone.bl.model.Role;

@FacesConverter(forClass=UserController.class,value="roleConverter")
public class RoleConverter implements Converter {

   public String getAsString(FacesContext context, UIComponent component, Object value) {
      return String.valueOf(((Role) value).getId());
   }
   
   public Object getAsObject(FacesContext context, UIComponent arg1, String value) {
      return null;
   }
}
