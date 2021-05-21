package al.ozone.bl.dao.impl;

import java.io.Serializable;
import java.util.List;

import al.ozone.bl.bean.SearchCustomerLog;
import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.CustomerLogDAO;
import al.ozone.bl.model.CustomerLog;

public class CustomerLogDAOImpl extends BaseDAO implements CustomerLogDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public CustomerLog get(Integer id) {
		CustomerLog ad = (CustomerLog) getSqlSession().selectOne("CUSTOMER_LOG.get", id);
		return ad;
	}

	@Override
	public void insert(CustomerLog customerLog) {
		 getSqlSession().selectOne("CUSTOMER_LOG.insert", customerLog);
	}

	@Override
	public List<CustomerLog> search(SearchCustomerLog sb) {
		@SuppressWarnings("unchecked")
		List<CustomerLog> list = getSqlSession().selectList("CUSTOMER_LOG.search", sb);
        return list;
	}
	
	
}
