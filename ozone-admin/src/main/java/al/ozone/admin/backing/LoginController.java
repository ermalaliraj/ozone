package al.ozone.admin.backing;

import al.ozone.admin.menu.MenuManager;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.utils.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ermal Aliraj
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = -2390028675965883617L;
    protected static final transient Log logger = LogFactory.getLog(LoginController.class);

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;
    @ManagedProperty(value = "#{menuManager}")
    private MenuManager menuManager;

    @ManagedProperty(value = "#{dataSourceInitializer}")
    private DataSourceInitializer dataSourceInitializer;

    // This is the action method called when the user clicks the "login" button
    public String doLogin() throws IOException, ServletException {
//		DataSourceInitializer dataSourceInitializer = SpringUtil.getBeanFromSpring("dataSourceInitializer");

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

//        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		  JSFUtils.getFacesMessage(FacesMessage.SEVERITY_ERROR, "Error before Dispatching to Spring Login", null);
        //JSFUtils.addErrorMessage("Error before Dispatching to Spring Login");
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        // It's OK to return null here because Faces is just going to exit.
        return null;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public void setDataSourceInitializer(DataSourceInitializer dataSourceInitializer) {
        this.dataSourceInitializer = dataSourceInitializer;
    }

    public DataSourceInitializer getDataSourceInitializer() {
        return dataSourceInitializer;
    }
}
