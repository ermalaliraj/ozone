package al.ozone.bl.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchCustomerBean;
import al.ozone.bl.bean.TopCustomersBean;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.PagedResult;

public interface CustomerDAO extends GenericDAO<Customer, Integer>{
	
	public List<Customer> search(SearchCustomerBean sb);
	
	public void insertNewsletter(Integer customerId, List<String> newsletters);
	
	public void deleteNewsletters(Integer customerId);
	
	public List<String> getCityNewsletterSubscribers(String cityId);
	
	public List<City> getCustomerNewsletters(Integer customerId);

	public List<Customer> getInvitations(Integer customerId);

	public PagedResult<Customer> loadLazy(SearchCustomerBean c, int skipResults, int maxResults);

	public Customer login(String emailCustomer, String encodedPassword);

	public void changePassword(Customer customer);
	
	public void updateFromFE(Customer c);

	//public boolean isCustomerPresentByEmail(String fFPEmail);

	public Customer getByEmail(String email);
	
	public UserDetails loadUserByUsername(String userName);

	public List<String> getAllEmails();

	public int getCountCustomers();

	public List<ResultStatisticBean> getCustomersGroupByRegMonths();

	public boolean isFBUserPresent(String fbId);

	public Customer getFBCustomer(String fbId);

	public void updateLastLogin(Integer cusId);

	public List<TopCustomersBean> getTopCustomers();

}
