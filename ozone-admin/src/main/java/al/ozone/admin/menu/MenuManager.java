package al.ozone.admin.menu;


import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import al.ozone.bl.model.MenuItem;

public class MenuManager implements Serializable{

	private static final long serialVersionUID = -6787542752645892831L;
	
	private MenuItem menuData;

    public void setMenuData(MenuItem menuData) {
        this.menuData = menuData;
    }

    public MenuItem getMenu(HttpServletRequest httpServletRequest) {
        return filterMenu((MenuItem) menuData, new HttpServletRequestRolesAdapter(httpServletRequest));
    }

    public MenuItem getMenu(String[] allowedRoles) {
        return filterMenu((MenuItem) menuData, new StringArrayRolesAdapter(allowedRoles));
    }

    private MenuItem filterMenu(MenuItem menu, RolesAdapter rolesAdapter) {
        MenuItem ret = null;
        if (isAllowed(rolesAdapter, menu.getRoles())) {
            ret = cloneMenuItem(menu);
            for (MenuItem child : menu.getChilds()) {
                MenuItem filteredChild;
                filteredChild = filterMenu(child, rolesAdapter);
                if (filteredChild != null) {
                    ret.getChilds().add(filteredChild);
                }
            }
        }
        return ret;
    }

    private boolean isAllowed(RolesAdapter rolesAdapter, String roles) {
        if (StringUtils.isBlank(roles)) {
            return true;
        } else {
            String[] roleArray = StringUtils.split(roles, ',');
            for (String role : roleArray) {
                if (rolesAdapter.hasRole(role)) {
                    return true;
                }
            }

            return false;
        }
    }
    
    private MenuItem cloneMenuItem(MenuItem menuItem) {
        MenuItem clone = new MenuItem();
        clone.setId(menuItem.getId());
        clone.setLabel(menuItem.getLabel());
        clone.setIcon(menuItem.getIcon());
        clone.setLink(menuItem.getLink());
        clone.setRoles(menuItem.getRoles());
        return clone;
    }
}
