package al.ozone.bl.dao;


import java.util.List;

import al.ozone.bl.model.Role;

public interface RoleDAO {

	public Role getById(String roleId);
	
	public void insert(Role role);

    public void delete(Role role);    

    public void update(Role role);

    public List<Role> getAll();

}