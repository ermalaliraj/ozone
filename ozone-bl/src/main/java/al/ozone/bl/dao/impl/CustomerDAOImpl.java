package al.ozone.bl.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchCustomerBean;
import al.ozone.bl.bean.TopCustomersBean;
import al.ozone.bl.dao.CustomerDAO;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.PagedResult;

public class CustomerDAOImpl extends GenericDAOImpl<Customer, Integer> implements CustomerDAO  {

	private static final long serialVersionUID = 1L;

	public CustomerDAOImpl(Class<Customer> persistentClass) {
		super(persistentClass);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> search(SearchCustomerBean sb) {
		List<Customer> list = getSqlSession().selectList("CUSTOMER.searchSelective", sb);
        return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getCityNewsletterSubscribers(String cityId) {		
		List<String> list = getSqlSession().selectList("CUSTOMER.getCityNewsletterSubscribers", cityId);
        return list;
	}

	@Override
	public void insertNewsletter(Integer customerId, List<String> newsletters) {
		if (newsletters != null) {
			for (String cityId : newsletters) {
	           Map<String, Object> newNewsletter = new HashMap<String, Object>();
	           newNewsletter.put("customerId", customerId);
	           newNewsletter.put("cityId", cityId);
	           
	           getSqlSession().insert("CUSTOMER.insertNewsletter", newNewsletter);
	           //logger.info("Customer "+customerId +" subscribed successfully to "+cityId+ " newsletter");
	       }
	    }
	}

	@Override
	public void deleteNewsletters(Integer customerId) {
		getSqlSession().delete("CUSTOMER.deleteNewsletters", customerId);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<City> getCustomerNewsletters(Integer customerId) {
		List<City> list = getSqlSession().selectList("CUSTOMER.getCustomerNewsletters", customerId);
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getInvitations(Integer customerId) {
		List<Customer> list = getSqlSession().selectList("CUSTOMER.getInvitations", customerId);
        return list;
	}

	@Override
	public PagedResult<Customer> loadLazy(SearchCustomerBean c, int skipResults, int maxResults) {
		RowBounds rowBound = new RowBounds(skipResults, maxResults);
        PagedResult<Customer> result = new PagedResult<Customer>();
        
        @SuppressWarnings("unchecked")
		List<Customer> list = getSqlSession().selectList("CUSTOMER.searchLazy", c, rowBound);
        int totalCount = (Integer) getSqlSession().selectOne("CUSTOMER.searchCount", c);
        result.setRowList(list);
        result.setTotalCount(totalCount);
        return result;
	}

	@Override
	public Customer login(String email, String password) {
		//logger.info("Login for customer '" + email + "'");
		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("email", email);
		loginParams.put("password", password);
		Customer customer = (Customer) getSqlSession().selectOne("CUSTOMER.login", loginParams);
        if (customer == null) {
            //logger.warn("Customer '"+email+"' not found in database");
            //throw new UsernameNotFoundException(email);
        } else {
           // logger.info("Customer '" + email +"' found");
        }
        
        return customer;
	}

	@Override
	public void changePassword(Customer customer) {
		getSqlSession().update("CUSTOMER.changePassword", customer);	
	}

	@Override
	public void updateFromFE(Customer c) {
		getSqlSession().update("CUSTOMER.updateFromFE", c);
	}

	@Override
	public Customer getByEmail(String email) {
		Customer c = (Customer) getSqlSession().selectOne("CUSTOMER.getByEmail", email);
		return c;
	}

	@Override
	public UserDetails loadUserByUsername(String cusEmail) {
        //logger.info("Login for user '" + cusEmail + "'");
        Customer cus = getByEmail(cusEmail);

        if (cus == null) {
            //logger.warn("Customer '"+cusEmail+"' not found in database");
            throw new UsernameNotFoundException(cusEmail);
        } else {
            //user.setRoles(getUserRoles(user));
            // logger.info("User '" + cus.getEmail() +"' found");
        }

        return cus;
	}

	@Override
	public List<String> getAllEmails() {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) getSqlSession().selectList("CUSTOMER.getAllEmails", null);
		return list;
	}
	
	@Override
	public int getCountCustomers(){
		int count = (Integer) getSqlSession().selectOne("CUSTOMER.getCountCustomers", null);
		return count;
	}

	@Override
	public List<ResultStatisticBean> getCustomersGroupByRegMonths() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = (List<ResultStatisticBean>) getSqlSession().selectList("CUSTOMER.getCustomersGroupByRegMonths", null);
		return list;
	}

	@Override
	public boolean isFBUserPresent(String fbId) {
		int count = (Integer) getSqlSession().selectOne("CUSTOMER.countFBUser", fbId);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Customer getFBCustomer(String fbId) {
		Customer record = (Customer) getSqlSession().selectOne("CUSTOMER.getFBCustomer", fbId);
		return record;
	}

	@Override
	public void updateLastLogin(Integer cusId) {
		getSqlSession().update("CUSTOMER.updateLastLogin", cusId);
	}

	@Override
	public List<TopCustomersBean> getTopCustomers() {
		@SuppressWarnings("unchecked")
		List<TopCustomersBean> list = (List<TopCustomersBean>) getSqlSession().selectList("CUSTOMER.getTopCustomers", null);
		return list;
	}

	
//	public boolean isCustomerPresentByEmail(String fFPEmail) {
//		Integer count = (Integer) getSqlSession().selectOne("CUSTOMER.countCustomerByEmail", fFPEmail);
//		if(count > 0){
//			return true;
//		}else{
//			return false;
//		}
//	}
}
