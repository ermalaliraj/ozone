package al.ozone.bl.manager.impl;

import al.ozone.bl.dao.RoleDAO;
import al.ozone.bl.dao.UserDAO;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.List;


public class UserManagerImpl implements UserManager, UserDetailsService, Serializable {

	private static final long serialVersionUID = -2020806690037197555L;
	protected static final transient Log logger = LogFactory.getLog(UserManagerImpl.class);

    //private AuditTrailManager auditTrailManager;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private MessageDigestPasswordEncoder passwordEncoder;
    
	public UserManagerImpl() {
		super();
	}	

//	public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
//		this.auditTrailManager = auditTrailManager;
//	}

	public MessageDigestPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

//	public AuditTrailManager getAuditTrailManager() {
//		return auditTrailManager;
//	}

	public User getById(String useId) {
		return userDAO.getById(useId);
	}
	
    public UserDetails loadUserByUsername(String userName) {
		DataSourceInitializer dataSourceInitializer = SpringUtil.getBeanFromSpring("dataSourceInitializer");
		return userDAO.loadUserByUsername(userName);
	}
    
	public void insert(User user) throws Exception{
		String encodedPassword = passwordEncoder.encodePassword(user.getPassword(), null);
        user.setPassword(encodedPassword);
		userDAO.insert(user);
		userDAO.insertUserRoles(user);
	}

    public void delete(User user) throws Exception{
        userDAO.deleteUserRoles(user);
        userDAO.delete(user);
    }

	public void update(User user) throws Exception{
		userDAO.update(user);
	}
 
	@Override
    public void save(User user) throws Exception {    
    	//Get the user if present in DB
        User dbUser = userDAO.getById(user.getUsername());
    	
        boolean encode = false;
        if (dbUser == null) {
            encode = true;
        } else {
            //User dbUser = userDAO.getById(user.getUsername());
            encode = !user.getPassword().equals(dbUser.getPassword());
        }

        if (encode) {
            String encodedPassword = passwordEncoder.encodePassword(user.getPassword(), null);
            user.setPassword(encodedPassword);
        }

        //This will never happen in our system.Leave it for completeness
        if (dbUser == null) {
        	logger.debug("User do not exist so inserting new user "+user.getSurname()+" in DB.");
            userDAO.insert(user);
        } else {
        	//logger.debug("User "+user.getUsername()+" is present in DB, so updating the user.");
            userDAO.update(user);
        }

        //delete the actual roles then insert the new one
        userDAO.deleteUserRoles(user);
        userDAO.insertUserRoles(user);
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    public List<Role> getAllRoles() {
        return roleDAO.getAll();
    }

    public boolean changeLoggedUserPassword(String oldPasswd, String newPasswd) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String encodedOldPassword = passwordEncoder.encodePassword(oldPasswd, null);
        if (!user.getPassword().equals(encodedOldPassword)) {
            //result.setMessage("La vecchia password non e' corretta");
            return false;
        }

        user.setPassword(newPasswd);

        this.save(user);

        //result.setMessage("La password e' stata modificata con successo");
        return true;
    }
    
    public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public List<User> search(User user) {
		return userDAO.search(user);
	}

	@Override
	public List<User> getUsersByRoleId(String roleAdmin) {
		return userDAO.getUsersByRoleId(roleAdmin);
	}

	@Override
	public void updateOnBadLogin(User user) {
		userDAO.updateOnBadLogin(user);
	}
	
	@Override
	public void updateOnSuccessLogin(User user) {
		userDAO.updateOnSuccessLogin(user);
	}

}
