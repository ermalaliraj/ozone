package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.model.Role;
import al.ozone.bl.model.User;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Ermal Aliraj
 * 
 * Session Managed Bean.
 */

@ManagedBean(name="userController")
@ViewScoped
public class UserController implements Serializable{

	private static final long serialVersionUID = 2684102776042155683L;
	protected static final transient Log logger = LogFactory.getLog(UserController.class);
	
	//Binding Variables - Search form
	private HtmlInputText sUsername;
	private HtmlInputText sFullName;
	private HtmlSelectOneMenu sRoleId;
	private List<SelectItem> allRoleItems;
	
	//Binding Variables - User's detail form
	private HtmlInputText fUsername;
	private HtmlInputText fName;
	private HtmlInputText fSurname;
	private HtmlInputText fEmail;
	private HtmlInputText fPassword;
	private HtmlSelectBooleanCheckbox  fEnabled;
	private HtmlSelectBooleanCheckbox  fLocked;
	private HtmlInputText fFailedLoginCount;
	
	//Associated Roles FieldSet
	private Map<String,String> allRolesMap;  
	private List<String> rolesOptions;
	private List<String> rolesSelected;
	private List<String> newRolesSelected;// for New User form 
	
	//Binding Variables - New User form 
	private HtmlInputText newUsername;
	private HtmlInputText newName;
	private HtmlInputText newSurname;
	private HtmlInputText newEmail;
	private HtmlInputText newPwd;
	//private Password newPwdRepeat;
	private HtmlSelectBooleanCheckbox newEnabled;
	private HtmlSelectBooleanCheckbox newLocked;
	
	private boolean wasLocked;
	private boolean wasEnable;
	
	//data needed for the session
	private List<User> usersList;
	private User userSelected;	
	private boolean editMode;

	// Injected properties
	@ManagedProperty(value="#{userManager}") 
	private UserManager userManager;
	@ManagedProperty(value="#{auditTrailManager}") 
	private AuditTrailManager auditTrailManager;
	
	//Page initialization
	@PostConstruct
	public void init(){			
		searchUser();					
	    List<Role> allRoles =  userManager.getAllRoles();
		allRolesMap = new HashMap<String, String>();
		allRoleItems = new ArrayList<SelectItem>();
		for (Role role : allRoles) {
			allRolesMap.put(role.getDescription(), role.getId());
			allRoleItems.add(new SelectItem(role.getId(), role.getDescription()));
		}
	}
	
	/**
	 * Reset the form programmatically
	 */
	public void cleanSearchForm(){
		sUsername.setValue(null);
		sFullName.setValue(null);
		sRoleId.setValue("");
	}
	
	public void cleanNewUserForm(){
		newUsername.setValue(null);
		newName.setValue(null);
		newSurname.setValue(null);
		newEmail.setValue(null);
		newPwd.setValue(null);
		// newPwdRepeat.setValue(null);
		newEnabled.setValue(null);
		newLocked.setValue(null);
		setNewRolesSelected(new ArrayList<String>());
	}
	
	public void searchUser(){		
		User u = new User();
		u.setUsername(JSFUtils.getStringFromUIInput(sUsername));	
		u.setName(JSFUtils.getStringFromUIInput(sFullName));	
		String roleId = JSFUtils.getStringFromUIInput(sRoleId);
		
		Collection<Role> newRoles = new HashSet<Role>();
		newRoles.add(new Role(roleId));
		u.setRoles(newRoles);
		
		//logger.debug("Searching with user:"+u);
		List<User> list = userManager.search(u);
		setUsersList(list);
		//logger.debug("Got from DB "+list.size()+" users");
	}

	public void updateUser() throws Exception{		
		boolean toEnable = Boolean.parseBoolean( fEnabled.getValue().toString());
		boolean toLock = Boolean.parseBoolean( fLocked.getValue().toString());
		
		User u = new User();
		u.setUsername(userSelected.getUsername());
		u.setName(fName.getValue().toString());
		u.setSurname(fSurname.getValue().toString());
		u.setEmail(fEmail.getValue().toString());
		u.setPassword(fPassword.getValue().toString());
		u.setEnabled(toEnable);
		u.setLocked(toLock);
		
		if(wasLocked){
			//If was locked, but now we want to unlock, set 0.
			if(!toLock){
				u.setFailedLoginCount(0);
			}else{
				u.setFailedLoginCount(JSFUtils.getIntegerFromUIInput(fFailedLoginCount));	
			}
		}else{
			//was not locked, you can not change the status so copy the fFailedLoginCount
			u.setFailedLoginCount(JSFUtils.getIntegerFromUIInput(fFailedLoginCount));
		}
		
		Collection<Role> rolesChoosed = new HashSet<Role>();
		for (String roleId : rolesSelected) {
			rolesChoosed.add(new Role(roleId));
		}
		u.setRoles(rolesChoosed);
		//logger.debug("Going to update user: "+u);
		try{
			userManager.save(u);
			
			//if unlocked, audit the operation, else audit as general update
			if(wasLocked && !toLock){
				logger.info("Set user "+u.getUsername()+" as NOT locked.");
				auditTrailManager.auditUserNotLocked(u.getUsername());
			}else if(wasEnable && !toEnable){
				logger.info("Set user "+u.getUsername()+" as NOT active.");
				auditTrailManager.auditUserNotActive(u.getUsername());
			}else if(!wasEnable && toEnable){
				logger.info("Set user "+u.getUsername()+" as active.");
				auditTrailManager.auditUserActive(u.getUsername());
			}else{
				logger.info("Updated user "+u.getUsername());
				auditTrailManager.auditUserUpdated(u.getUsername());
			}
			
			this.editMode = false;
	        //logger.debug("User: "+u.getUsername()+" updated from controller.");	
			JSFUtils.addInfoMessage("User updated successfully with no errors.");
		} catch (Exception e) {
			logger.error("User: "+u.getUsername()+" can not be updated. Error:", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}        
		cleanSearchForm();
	    searchUser();  
	}
	
	public void addUser() throws Exception{		
		User u = new User();
		u.setUsername(newUsername.getValue().toString());
		u.setName(newName.getValue().toString());
		u.setSurname(newSurname.getValue().toString());
		u.setEmail(newEmail.getValue().toString());
		u.setPassword(newPwd.getValue().toString());
		u.setEnabled(Boolean.parseBoolean( newEnabled.getValue().toString()) );
		u.setLocked(Boolean.parseBoolean( newLocked.getValue().toString()) );	
		u.setFailedLoginCount(0);
	
		Collection<Role> rolesChoosed = new HashSet<Role>();
		for (String roleId : newRolesSelected) {
			rolesChoosed.add(new Role(roleId));
		}
		u.setRoles(rolesChoosed);
		
		try {
			userManager.insert(u);
			auditTrailManager.auditUserInserted(u.getUsername());
			logger.info("User "+u.getUsername()+" inserted from controller.");
			JSFUtils.addInfoMessage("New User added successfully in database.");
		} catch (Exception e) {
			logger.error("User "+u.getUsername()+" cannot be inserted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}
		cleanSearchForm();
		cleanNewUserForm();
		searchUser(); 
	}
	
	public void deleteUser(){		
		try {
			userManager.delete(userSelected);
			auditTrailManager.auditUserDeleted(userSelected.getUsername());
			logger.info("User "+userSelected.getUsername()+" deleted from controller.");			  
			JSFUtils.addInfoMessage("User Deleted");
		}catch (Exception e) {
			logger.info("User "+userSelected.getUsername()+" can not be deleted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}  		
		searchUser();	 
	}

	/**
	 * Set selected user and defines the roles to be checked for the
	 * userSelected user in the Roles Panel.
	 * @param userSelected
	 */
	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
		
		Collection<Role> userRoles =  userSelected.getRoles();		
		List<String> idRoles = new ArrayList<String>();
		for (Role role : userRoles) {
			idRoles.add(role.getId());
		}
		setRolesSelected(idRoles);
		
		wasLocked = userSelected.isLocked();
		wasEnable = userSelected.isEnabled();
	}
	
	public User getUserSelected() {
		return userSelected;
	}
	
	public HtmlInputText getsUsername() {
		return sUsername;
	}

	public void setsUsername(HtmlInputText sUsername) {
		this.sUsername = sUsername;
	}

	public HtmlInputText getsFullName() {
		return sFullName;
	}

	public void setsFullName(HtmlInputText sFullName) {
		this.sFullName = sFullName;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public HtmlInputText getfUsername() {
		return fUsername;
	}

	public void setfUsername(HtmlInputText fUsername) {
		this.fUsername = fUsername;
	}

	public HtmlInputText getfName() {
		return fName;
	}

	public void setfName(HtmlInputText fName) {
		this.fName = fName;
	}

	public HtmlInputText getfSurname() {
		return fSurname;
	}

	public void setfSurname(HtmlInputText fSurname) {
		this.fSurname = fSurname;
	}

	public HtmlInputText getfEmail() {
		return fEmail;
	}

	public void setfEmail(HtmlInputText fEmail) {
		this.fEmail = fEmail;
	}

	public HtmlInputText getfPassword() {
		return fPassword;
	}

	public void setfPassword(HtmlInputText fPassword) {
		this.fPassword = fPassword;
	}

	public HtmlSelectBooleanCheckbox  getfEnabled() {
		return fEnabled;
	}

	public void setfEnabled(HtmlSelectBooleanCheckbox  fEnabled) {
		this.fEnabled = fEnabled;
	}

	public HtmlSelectBooleanCheckbox  getfLocked() {
		return fLocked;
	}

	public void setfLocked(HtmlSelectBooleanCheckbox  fLocked) {
		this.fLocked = fLocked;
	}

	public HtmlInputText getfFailedLoginCount() {
		return fFailedLoginCount;
	}

	public void setfFailedLoginCount(HtmlInputText fFailedLoginCount) {
		this.fFailedLoginCount = fFailedLoginCount;
	}

	public HtmlInputText getNewUsername() {
		return newUsername;
	}

	public void setNewUsername(HtmlInputText newUsername) {
		this.newUsername = newUsername;
	}

	public HtmlInputText getNewName() {
		return newName;
	}

	public void setNewName(HtmlInputText newName) {
		this.newName = newName;
	}

	public HtmlInputText getNewSurname() {
		return newSurname;
	}

	public void setNewSurname(HtmlInputText newSurname) {
		this.newSurname = newSurname;
	}

	public HtmlInputText getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(HtmlInputText newEmail) {
		this.newEmail = newEmail;
	}

	public HtmlInputText getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(HtmlInputText newPwd) {
		this.newPwd = newPwd;
	}

	public HtmlSelectBooleanCheckbox getNewEnabled() {
		return newEnabled;
	}

	public void setNewEnabled(HtmlSelectBooleanCheckbox newEnabled) {
		this.newEnabled = newEnabled;
	}

	public HtmlSelectBooleanCheckbox getNewLocked() {
		return newLocked;
	}

	public void setNewLocked(HtmlSelectBooleanCheckbox newLocked) {
		this.newLocked = newLocked;
	}

	public Map<String, String> getAllRolesMap() {
		return allRolesMap;
	}

	public void setAllRolesMap(Map<String, String> allRolesMap) {
		this.allRolesMap = allRolesMap;
	}

	public List<String> getRolesOptions() {
		return rolesOptions;
	}

	public void setRolesOptions(List<String> rolesOptions) {
		this.rolesOptions = rolesOptions;
	}

	public List<String> getRolesSelected() {
		return rolesSelected;
	}

	public void setRolesSelected(List<String> rolesSelected) {
		this.rolesSelected = rolesSelected;
	}

	public HtmlSelectOneMenu getsRoleId() {
		return sRoleId;
	}

	public void setsRoleId(HtmlSelectOneMenu sRoleId) {
		this.sRoleId = sRoleId;
	}

	public List<SelectItem> getAllRoleItems() {
		return allRoleItems;
	}

	public void setAllRoleItems(List<SelectItem> allRoleItems) {
		this.allRoleItems = allRoleItems;
	}

	public List<String> getNewRolesSelected() {
		return newRolesSelected;
	}

	public void setNewRolesSelected(List<String> newRolesSelected) {
		this.newRolesSelected = newRolesSelected;
	}

	public boolean isWasLocked() {
		return wasLocked;
	}

	public void setWasLocked(boolean wasLocked) {
		this.wasLocked = wasLocked;
	}

	public boolean isWasEnable() {
		return wasEnable;
	}

	public void setWasEnable(boolean wasEnable) {
		this.wasEnable = wasEnable;
	}

	public AuditTrailManager getAuditTrailManager() {
		return auditTrailManager;
	}

	public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
		this.auditTrailManager = auditTrailManager;
	}
	

}
