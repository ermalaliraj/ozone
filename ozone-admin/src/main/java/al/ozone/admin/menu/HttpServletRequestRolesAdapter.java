package al.ozone.admin.menu;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestRolesAdapter implements RolesAdapter {

    private HttpServletRequest httpServletRequest;

    public HttpServletRequestRolesAdapter(HttpServletRequest httpServletRequest) {
        super();
        this.httpServletRequest = httpServletRequest;
    }

    public boolean hasRole(String role) {
        return httpServletRequest.isUserInRole(role);
    }

}
