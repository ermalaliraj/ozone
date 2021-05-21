package al.ozone.admin.menu;

public class StringArrayRolesAdapter implements RolesAdapter {

    private String[] allowedRoles;

    public StringArrayRolesAdapter(String[] allowedRoles) {
        super();
        this.allowedRoles = allowedRoles;
    }

    public boolean hasRole(String role) {
        for (int i = 0; i < allowedRoles.length; i++) {
            if (role.equals(allowedRoles[i])) {
                return true;
            }
        }
        return false;
    }

}
