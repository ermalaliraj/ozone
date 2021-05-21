package al.ozone.bl.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.UserDAO;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.ZUtils;


public class UserDAOImpl extends BaseDAO implements UserDAO {

    protected static final transient Log logger = LogFactory.getLog(UserDAOImpl.class);

    public User getById(String username) {
        User user = (User) getSqlSession().selectOne("USER.getById", username);
        //logger.debug("User retrived from db :"+user);
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        List<User> users = getSqlSession().selectList("USER.getAll", null);
        return users;
    }

    public void update(final User user) {
    	getSqlSession().update("USER.update", user);
    }

    public void delete(User user) {
    	getSqlSession().update("USER.delete", user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        //logger.info("Login for user '" + username + "'");
        User user = getById(username);

        if (user == null) {
            //logger.warn("User '"+username+"' not found in database");
            throw new UsernameNotFoundException(username);
        } else {
            //user.setRoles(getUserRoles(user));
            //logger.info("User '" + user.getUsername() +"' found");
        }

        return user;
    }

    public void insert(User user) {
    	getSqlSession().insert("USER.insert", user);
    }

    @SuppressWarnings("unchecked")
    public HashSet<Role> getUserRoles(User user) {
        List<Role> roles = getSqlSession().selectList("USER.getUserRoles", user);

        HashSet<Role> ret = new HashSet<Role>(roles);
        return ret;
    }

    public void deleteUserRoles(final User user) {
    	getSqlSession().delete("USER.deleteUserRoles", user);
    }

    public void insertUserRoles(final User user) {
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                Map<String, Object> newRole = new HashMap<String, Object>();
                newRole.put("userId", user.getUsername());
                newRole.put("roleId", role.getAuthority());

                getSqlSession().insert("USER.insertUserRoles", newRole);
                logger.debug("Role "+role.getId()+" associated successfully to user "+user.getUsername());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<User> search(User user) {
    	//in the user.name has been stored name + surname
    	List<User> users = null;
    	Map<String, Object> newRole = new HashMap<String, Object>();
        newRole.put("userId", user.getUsername());
        newRole.put("userFullName", user.getName());
        
    	Collection<Role> rolesList = (Collection<Role>) user.getRoles();
        if (!ZUtils.isEmptyCollection(rolesList)) {
        	for (Role role : user.getRoles()) {
                newRole.put("roleId", role.getId());
        	}
        }
        
        users = getSqlSession().selectList("USER.search", newRole);
        return users;
	}

	@Override
	public void setLastIpForUser(User user) {
		getSqlSession().update("USER.setLastIpForUser", user);
	}

	@Override
	public List<User> getUsersByRoleId(String roleId) {
		@SuppressWarnings("unchecked")
		List<User> list = getSqlSession().selectList("USER.getUsersByRoleId", roleId);
        return list;
	}

	@Override
	public void updateOnBadLogin(User user) {
		getSqlSession().update("USER.updateOnBadLogin", user);
	}

	@Override
	public void updateOnSuccessLogin(User user) {
		getSqlSession().update("USER.updateOnSuccessLogin", user);
	}

}
