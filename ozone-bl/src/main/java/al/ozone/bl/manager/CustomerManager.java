package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchCustomerBean;
import al.ozone.bl.bean.TopCustomersBean;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.PagedResult;

public interface CustomerManager extends UserDetailsService{
	
	public Customer get(Integer id);
	
	public void insert (Customer customer);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE","ROLE_CUSTOMER" })
	public void update (Customer customer);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void delete (Customer customer);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Customer> search(Customer customer);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Customer> getAll();
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Customer> search(SearchCustomerBean sb);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<String> getCitySubscribers(String cityId);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<City> getCustomerNewsletters(Integer customerId);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void insertNewsletter(Integer customerId, List<String> newsletters);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void deleteNewsletters(Integer customerId);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Customer> getInvitations(Integer customerId);

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public PagedResult<Customer> loadLazy(SearchCustomerBean c, int skipResult, int maxResults);
	
	public Customer login(String emailCustomer, String password);

	public Customer login(String emailCustomer, String password, boolean isEncrypted);

	public void changePassword(Customer customer);

	public void updateFromFE(Customer c);

	//public boolean isCustomerPresentByEmail(String fFPEmail);

	public Customer getByEmail(String fFPEmail);

	public List<String> getAllEmails();

	public int getCountCustomers();

	public List<ResultStatisticBean> getCustomersGroupByRegMonths();

	public boolean isFBUserPresent(String fbId);

	public Customer getFBCustomer(String fbId);

	@Secured( { "ROLE_CUSTOMER" })
	public void updateLastLogin(Integer cusId);

	public List<TopCustomersBean> getTopCustomers();
}