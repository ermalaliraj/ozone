package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchCustomerLog;
import al.ozone.bl.model.CustomerLog;

public interface CustomerLogDAO {

	public CustomerLog get(Integer id);

	public void insert(CustomerLog coupon);

	public List<CustomerLog> search(SearchCustomerLog sb);
	
}
