package al.ozone.admin.login;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import al.ozone.admin.backing.UserPreferences;
import al.ozone.admin.menu.MenuManager;
import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.MenuItem;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.ZUtils;

public class GroupAlbAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	protected static final transient Log logger = LogFactory.getLog(GroupAlbAuthenticationSuccessHandler.class);
    private String defaultTargetUrl = "/secured/homePage.faces";
    private final String logout = "/j_spring_security_logout";
    private final String loginFailed = "/loginFailed.jsp";

    private UserManager userManager;
    private MenuManager menuManager;
    private AuditTrailManager auditTrailManager;
    private ApplicationConfig applicationConfig;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
		this.auditTrailManager = auditTrailManager;
	}
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		String redirect = request.getContextPath().replace("/secured", "");
        // get the IP from which the user logged in
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) auth.getDetails();
        String remoteAddress = authDetails.getRemoteAddress();
        User user = userManager.getById(auth.getName());
        user.setLastIp(remoteAddress);
        user.setFailedLoginCount(0);
        userManager.updateOnSuccessLogin(user);
        
    	Collection<Role> roleList = user.getRoles();
        if (roleList.size() == 0) {
            redirect += logout;
            logger.info("User can not connect to the system because has no role associated");
        } else {
            redirect += defaultTargetUrl;
        	String loggedUser = ZUtils.getLoggedUserFullname();
        	Collection<Role> roles = ZUtils.getLoggedUserRoles();
        	String userRoles = ZUtils.getUserRolesAsString(roles);
        	MenuItem loggedMenu = menuManager.getMenu(ZUtils.getUserRolesAsArray(roles, "ID"));
        	
        	UserPreferences preferences = new UserPreferences();
        	preferences.setLoggedUser(loggedUser);
        	preferences.setLoggedUserRoles(userRoles);
        	preferences.setLoggedUserMenu(loggedMenu);
        	preferences.setTheme(applicationConfig.getTheme());
        	preferences.setLanguage(applicationConfig.getLanguage());
        	
        	JSFUtils.putObjectInSession("userPreferences", preferences);
//        	JSFUtils.putObjectInSession("loggedUser", loggedUser);
//        	JSFUtils.putObjectInSession("userRoles", userRoles);
//        	JSFUtils.putObjectInSession("loggedMenu", loggedMenu);
//        	JSFUtils.putObjectInSession("appTheme", applicationConfig.getTheme());
//        	JSFUtils.putObjectInSession("language", applicationConfig.getLanguage());
        	
        	logger.info("User "+user.getUsername()+" correctly authenticated from ip "+remoteAddress+" using Spring.");
        	auditTrailManager.auditLoginCorrect(remoteAddress);
        }
        
        //logger.debug("Login successfully. Redirecting to: "+redirect);
        response.sendRedirect(redirect);
    }
}
