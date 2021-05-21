package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.MenuItem;

/**
 * @author Ermal Aliraj
 * 
 * Session Managed Bean.
 */
@ManagedBean(name="userPreferences")
@SessionScoped
public class UserPreferences implements Serializable{

	private static final long serialVersionUID = -5901293856113021531L;
	protected static final transient Log logger = LogFactory.getLog(UserPreferences.class);
	
	private String loggedUser;
	private String loggedUserRoles;
	private MenuItem loggedUserMenu;
	//private String language; 
	private Locale locale;
	private String theme;
	
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public String getLoggedUserRoles() {
		return loggedUserRoles;
	}
	public void setLoggedUserRoles(String loggedUserRoles) {
		this.loggedUserRoles = loggedUserRoles;
	}
	public MenuItem getLoggedUserMenu() {
		return loggedUserMenu;
	}
	public void setLoggedUserMenu(MenuItem loggedUserMenu) {
		this.loggedUserMenu = loggedUserMenu;
	}
	public String getLanguage() {
		String ln = locale.getLanguage();
		//System.out.println("getLanguage:"+ln);
		return ln;
	}
	public void setLanguage(String language) {
		//this.language = language;
		locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
       // System.out.println("setLanguage locale set to:"+locale);
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

}
