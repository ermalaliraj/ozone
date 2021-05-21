package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;


public interface UserManager extends UserDetailsService {

    public User getById(String id);

    public UserDetails loadUserByUsername(String userName);
    
    @Secured( { "ROLE_ADMIN" })
    public void insert(User user) throws Exception;
    
    @Secured( { "ROLE_ADMIN" })
    public void update(User user) throws Exception;

    @Secured( { "ROLE_ADMIN" })
    public void delete(User user) throws Exception;

    /**
     * Save the user in DB. Method used from Controller taking the data from html fields.
     * @param user user to save
     * @throws Exception
     */
    @Secured( { "ROLE_ADMIN" })
    public void save(User user) throws Exception;

    public boolean changeLoggedUserPassword(String oldPasswd, String newPasswd) throws Exception;
    
    public List<User> getAllUsers();
    
    public List<Role> getAllRoles();  
    
    @Secured( { "ROLE_ADMIN" })
    public List<User> search(User user);

	public List<User> getUsersByRoleId(String roleAdmin);

	/**
	 * On BadLogin update fields LOCKED, FAILED_LOGIN_COUNT and LAST_IP.
	 * @param user user to update
	 */
	public void updateOnBadLogin(User user);  
	
	/**
	 * On SuccessLogin update fields FAILED_LOGIN_COUNT and LAST_IP.
	 * @param user
	 */
	public void updateOnSuccessLogin(User user);

}
