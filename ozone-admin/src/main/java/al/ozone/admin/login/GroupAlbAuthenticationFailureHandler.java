package al.ozone.admin.login;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.model.Email;
import al.ozone.engine.email.EmailEngine;
import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.ZUtils;

public class GroupAlbAuthenticationFailureHandler implements AuthenticationFailureHandler {

	protected static final transient Log logger = LogFactory.getLog(GroupAlbAuthenticationFailureHandler.class);
	private String loginFailedUrl = "/loginFailed.jsp";
	//private String userLockedUrl = "/userLocked.jsp";

    private UserManager userManager;
    private ApplicationConfig applicationConfig;
    private EmailEngine emailEngine;
	private AuditTrailManager auditTrailManager;

    
    public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
		this.auditTrailManager = auditTrailManager;
	}
	public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	public void setEmailEngine(EmailEngine emailEngine) {
		this.emailEngine = emailEngine;
	}
	public void setLoginFailedUrl(String loginFailedUrl) {
		this.loginFailedUrl = loginFailedUrl;
	}

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
		
        //logger.debug("Failed login. Spring params: j_username="+request.getParameter("j_username")+", j_password="+request.getParameter("j_password"));
        String redirect = request.getContextPath();
        // Get Ip from which the user is trying to login
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) authenticationException.getAuthentication().getDetails();
        String remoteAddress = authDetails.getRemoteAddress();
      
    	User wrongUser = new User();
        wrongUser.setUsername((String) authenticationException.getAuthentication().getPrincipal());
        logger.debug("Failed authentication using username: "+wrongUser.getUsername());
            
        if (authenticationException instanceof LockedException){
        	logger.debug("User "+wrongUser.getUsername() +" is locked.");
        	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User is locked", null);
        }else if(authenticationException instanceof DisabledException){
        	logger.debug("User "+wrongUser.getUsername() +" is disabled.");
        	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User is disabled", null);
        } else{
        	// Get the user from database
            User dbUser = userManager.getById(wrongUser.getUsername());
            if(dbUser!=null){
            	dbUser.setLastIp(remoteAddress);
            	boolean lockUser = increaseBadLogin(dbUser);
            	if (lockUser){
                	List<String> adminEmails = ZUtils.getEmailsFromUsers(userManager.getUsersByRoleId(Role.ROLE_ADMIN));
                	if(!ZUtils.isEmptyList(adminEmails)){
	                	Email email = new Email("BadLogin");
	                	email.setSubject("OZoneAdmin - User Locked");
	                    email.addTo(adminEmails);
	                    email.addParameter("remoteAddress", remoteAddress);
	                    email.addParameter("user", dbUser);
	                    logger.info("User "+dbUser.getUsername()+" locked. Sending emails to all administrators: "+adminEmails);
	                    JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User has been locked. An email has been sent to the administrators.", null);
	                    emailEngine.addEmail(email);
                	}else{
                		logger.info("User "+dbUser.getUsername()+" locked. No admin email present in DB to send notification.");
                		JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User has been locked.", null);
                		
                		logger.error("No admin email present in DB to send notification!!!!!");
                		Email email = new Email("StrangeSituation");
                    	email.setSubject("OZone - Strange Situation");
                        email.addTo("ermal.aliraj@gmail.com");
                        email.addParameter("where", "GroupAlbAuthenticationFailureHandler.onAuthenticationFailure");
                        email.addParameter("msg", "No admin email present in DB to send notification!");
                        emailEngine.addEmail(email);
                	}
                }else{
                	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong password.", null);
                }
            	auditTrailManager.auditLoginBad(dbUser.getUsername(), remoteAddress);
            }else{
            	logger.debug("User "+wrongUser.getUsername() +" not present in database.");
            	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User not present in database.", null);
            }
        }

        redirect += loginFailedUrl;
        logger.debug("Wrong Authentication! Redirecting to: "+redirect);
        response.sendRedirect(redirect);
//      RequestDispatcher rd = request.getRequestDispatcher(redirect) ;
//      rd.forward( request, response ) ; 
    }

	/**
	 * Increase the number of badLogins for the user. In case has exceed maxNrBadLogin set
	 * the user as locked.
	 * @param dbUser User to which increase badLogins
	 * @return status of the user locked/unlocked
	 */
    private boolean increaseBadLogin(User dbUser) {
    	boolean locked = false;
        int maxBadLogin = 0;
        if (dbUser.getFailedLoginCount() == null) {
            dbUser.setFailedLoginCount(0);
        }
        dbUser.setFailedLoginCount(dbUser.getFailedLoginCount() + 1);
        logger.info("Set FailedLoginCount to "+ dbUser.getFailedLoginCount() +" for the User "+dbUser.getUsername());
        
        maxBadLogin = applicationConfig.getMaxBadLogins();
        if (dbUser.getFailedLoginCount() >= maxBadLogin) {
            dbUser.setLocked(true);
            locked = true;
            logger.debug("User "+dbUser.getUsername()+" will be locked because exceeded MaxNumberBadLogins.");
        }

        try {
			userManager.updateOnBadLogin(dbUser);
		} catch (Exception e) {
			logger.error("Can not update the user "+dbUser.getSurname()+" after an authentication failure");
			e.printStackTrace();
		}

        return locked;
    }
}
