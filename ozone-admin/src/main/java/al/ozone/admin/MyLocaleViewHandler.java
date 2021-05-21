package al.ozone.admin;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import al.ozone.admin.backing.UserPreferences;
import al.ozone.admin.util.JSFUtils;

public class MyLocaleViewHandler extends ViewHandler {

    private final ViewHandler base = null;

    @Override
    public Locale calculateLocale(FacesContext context) {
    	//fetch the session scoped bean and return the
    	//UserPreferences userPreferences = (UserPreferences ) context.getExternalContext().getRequest().getSession().getAttribute("userPreferences");//this line is not tested.
    	UserPreferences userPreferences = JSFUtils.findBean("userPreferences");
    	return new Locale(userPreferences.getLanguage());
    }

	@Override
	public String calculateRenderKitId(FacesContext arg0) {
		return this.calculateRenderKitId(arg0);
	}


	@Override
	public UIViewRoot createView(FacesContext arg0, String arg1) {
		return this.createView(arg0, arg1);
	}


	@Override
	public String getActionURL(FacesContext arg0, String arg1) {
		return this.getActionURL(arg0, arg1);
	}


	@Override
	public String getResourceURL(FacesContext arg0, String arg1) {
		return this.getResourceURL(arg0, arg1);
	}


	@Override
	public void renderView(FacesContext arg0, UIViewRoot arg1)
			throws IOException, FacesException {
		this.renderView(arg0, arg1);
	}


	@Override
	public UIViewRoot restoreView(FacesContext arg0, String arg1) {
		return this.restoreView(arg0, arg1);
	}


	@Override
	public void writeState(FacesContext arg0) throws IOException {
		this.writeState(arg0);
	}
}
