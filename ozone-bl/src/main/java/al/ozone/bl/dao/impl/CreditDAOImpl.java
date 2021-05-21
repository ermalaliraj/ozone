package al.ozone.bl.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchCreditBean;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;

public class CreditDAOImpl extends GenericDAOImpl<Credit, Integer> implements CreditDAO {

	private static final long serialVersionUID = -1234747687981145845L;
	
	protected static final transient Log logger = LogFactory.getLog(CreditDAOImpl.class);

	public CreditDAOImpl(Class<Credit> persistentClass) {
		super(persistentClass);
	}

	@Override
	public void setCreditAsUsed(Integer creditId, String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", creditId);
		map.put("aboutUse", reason);
		map.put("usedDate", new Date());
		getSqlSession().update("CREDIT.setCreditAsUsed", map);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Credit> search(SearchCreditBean sb) {
		List<Credit> list = getSqlSession().selectList("CREDIT.searchSelective", sb);
        return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Credit> getByCustomer(Integer cusId) {
		List<Credit> list = getSqlSession().selectList("CREDIT.getByCustomer",cusId);
        return list;
	}
}
