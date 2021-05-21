package al.ozone.bl.dao.impl;

import java.util.List;

import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.RoleDAO;
import al.ozone.bl.model.Role;


public class RoleDAOImpl extends BaseDAO implements RoleDAO {

	public Role getById(String roleId) {
		return (Role) getSqlSession().selectOne("USER.getById", roleId);
	}

	public void insert(Role role) {
		getSqlSession().insert("USER.insertUserRoles", role);
	}

	public void delete(Role role) {
		getSqlSession().delete("USER.delete", role);
	}

	public void update(Role role) {
		getSqlSession().update("USER.update", role);
	}

	@SuppressWarnings("unchecked")
	public List<Role> getAll() {
		List<Role> roles = getSqlSession().selectList("ROLE.getAll", null);
        return roles;
	}

}
