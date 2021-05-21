package al.ozone.admin.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import al.ozone.admin.util.JSFUtils;
 
public class LoginErrorPhaseListener implements PhaseListener {
	
    private static final long serialVersionUID = -1216620620302322995L;
 
    @Override
    public void beforePhase(PhaseEvent event){
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
        		.get(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
 
        if (e instanceof BadCredentialsException) {
        	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Bad credentials.2", null);
        } else if (e instanceof LockedException){
        	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User is locked.2", null);
        }else if(e instanceof DisabledException){
        	JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "User is disabled.2", null);
        }
       
    }
 
    @Override
    public void afterPhase(PhaseEvent event){
    }
 
    @Override
    public PhaseId getPhaseId(){
        return PhaseId.RENDER_RESPONSE;
    }
 
}