package al.ozone.bl.dao;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import al.ozone.bl.model.User;

public interface UserDAO {

	public User getById(String username);
	
	public void insert(User user);

    public void delete(User user);    

    public void update(User user);

    public List<User> getAll();

    public UserDetails loadUserByUsername(String userName);

    public void deleteUserRoles(final User user);

    public void insertUserRoles(final User user);

	public List<User> search(User user);

	public void setLastIpForUser(User user);

	public List<User> getUsersByRoleId(String roleId);

	public void updateOnBadLogin(User user);

	public void updateOnSuccessLogin(User user);
}