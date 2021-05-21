package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchCustomerBean;
import al.ozone.bl.bean.TopCustomersBean;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.dao.CustomerDAO;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.PagedResult;

public class CustomerManagerImpl implements CustomerManager, UserDetailsService, Serializable {

	private static final long serialVersionUID = -219216367743250529L;
	protected static final transient Log logger = LogFactory.getLog(CustomerManagerImpl.class);
	
	private ApplicationConfig applicationConfig;
	private CustomerDAO customerDAO;
	private CreditDAO creditDAO;
	private MessageDigestPasswordEncoder passwordEncoder;   

	private void getPasswordEncoderFromSpring() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
	        			new String[] {
							"applicationContext-manager.xml",
							"applicationContext-dao.xml",
							"applicationContext-resources.xml",
							"applicationContext-service.xml"
				        });
        passwordEncoder = (ShaPasswordEncoder) applicationContext.getBean("passwordEncoder");
	}
	
//	public MessageDigestPasswordEncoder getPasswordEncoder() {
//		return passwordEncoder;
//	}
//	public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}
	
	@Override
	public void insert(Customer customer) {
        getPasswordEncoderFromSpring();
        String encodedPassword = null;
        if(customer.getPassword()!=null){
        	encodedPassword = passwordEncoder.encodePassword(customer.getPassword(), null);
        }
		
		customer.setPassword(encodedPassword);
		customer.setReg_Date(new Date());
		customerDAO.insert(customer);
		
		//TODO this reference added in INVITE table
//		Integer idUser = customer.getInvitedBy();
//		if(idUser != null){
//			Credit credit = new Credit();
//			credit.setCustomer(new Customer(idUser));
//			credit.setAssignedDate(new Date());
//			credit.setAbout("Credit assigned after invitation of user "+ customer.getId());
//			credit.setValue(applicationConfig.getBonusValue());
//			creditDAO.insert(credit);
//		}
	}
	
	@Override
	public void update(Customer customer) {
		getPasswordEncoderFromSpring();
		Customer dbCustomer = customerDAO.get(customer.getId());
		
        boolean encode = !customer.getPassword().equals(dbCustomer.getPassword());
        if (encode) {
            String encodedPassword = passwordEncoder.encodePassword(customer.getPassword(), null);
            customer.setPassword(encodedPassword);
        }

		customerDAO.update(customer);
		
//		//TODO update the password. Later change password must be done through a new pop.up
//		changePassword(customer);
	}
	
	@Override
	public void updateFromFE(Customer c) {
		customerDAO.updateFromFE(c);
	}

	@Override
	public void delete(Customer customer) {
		customerDAO.delete(customer);
		deleteNewsletters(customer.getId());
	}

	@Override
	public List<Customer> search(Customer customer) {
		return customerDAO.search(customer);
	}

	@Override
	public List<Customer> getAll() {
		return customerDAO.getAll();
	}	

	@Override
	public Customer get(Integer id) {
		return customerDAO.get(id);
	}

	@Override
	public List<Customer> search(SearchCustomerBean sb) {
		return customerDAO.search(sb);
	}

	@Override
	public List<String> getCitySubscribers(String cityId) {
		return customerDAO.getCityNewsletterSubscribers(cityId);
	}
	
	@Override
	public List<City> getCustomerNewsletters(Integer customerId) {
		return customerDAO.getCustomerNewsletters(customerId);
	}
	
	@Override
	public void insertNewsletter(Integer customerId, List<String> newsletters) {
		 customerDAO.insertNewsletter(customerId, newsletters);
	}
	
	@Override
	public void deleteNewsletters(Integer customerId) {
		customerDAO.deleteNewsletters(customerId);
	}
	
	@Override
	public List<Customer> getInvitations(Integer customerId) {
		return customerDAO.getInvitations(customerId);
	}

	@Override
	public PagedResult<Customer> loadLazy(SearchCustomerBean c, int skipResult, int maxResults) {
		return customerDAO.loadLazy(c, skipResult, maxResults);
	}
	
	
	/**
	 * Creating ClassPathXmlApplicationContext in this point to avoid serialization problems.
	 * 
	 * isEncrypted default true
	 */
	public Customer login(String emailCustomer, String password, boolean isEncrypted) {

		if(isEncrypted){
			getPasswordEncoderFromSpring();
			password = passwordEncoder.encodePassword(password, null);
		}
		
		return customerDAO.login(emailCustomer, password);
	}
	
	/**
	 * 
	 * Creating ClassPathXmlApplicationContext in this point to avoid serialization problems.
	 */
	@Override
	public Customer login(String emailCustomer, String password) {
		return login(emailCustomer, password, true);
	}
	
    public UserDetails loadUserByUsername(String userName) {
        return customerDAO.loadUserByUsername(userName);
    }
	
	@Override
	public void changePassword(Customer customer) {
		getPasswordEncoderFromSpring();
		String pwd = customer.getPassword();
		String encodedPassword = passwordEncoder.encodePassword(pwd, null);
		customer.setPassword(encodedPassword);
		customerDAO.changePassword(customer);
	}
	
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	public CreditDAO getCreditDAO() {
		return creditDAO;
	}
	public void setCreditDAO(CreditDAO creditDAO) {
		this.creditDAO = creditDAO;
	}
	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	@Override
	public Customer getByEmail(String email) {
		return customerDAO.getByEmail(email);
	}

	@Override
	public List<String> getAllEmails() {
		return customerDAO.getAllEmails();
	}
	
	@Override
	public int getCountCustomers() {
		return customerDAO.getCountCustomers();
	}

	@Override
	public List<ResultStatisticBean> getCustomersGroupByRegMonths() {
		return customerDAO.getCustomersGroupByRegMonths();
	}

	@Override
	public boolean isFBUserPresent(String fbId) {
		return customerDAO.isFBUserPresent(fbId);
	}

	@Override
	public Customer getFBCustomer(String fbId) {
		return customerDAO.getFBCustomer(fbId);
	}

	@Override
	public void updateLastLogin(Integer cusId) {
		customerDAO.updateLastLogin(cusId);
	}

	@Override
	public List<TopCustomersBean> getTopCustomers() {
		return customerDAO.getTopCustomers();
	}

//	// true if exist in DB 1 customer with the given email
//	public boolean isCustomerPresentByEmail(String fFPEmail) {
//		return customerDAO.isCustomerPresentByEmail(fFPEmail);
//	}
}

