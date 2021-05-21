package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchCreditBean;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.manager.CreditManager;
import al.ozone.bl.model.Credit;

public class CreditManagerImpl implements CreditManager, Serializable  {

	protected static final transient Log logger = LogFactory.getLog(CreditManagerImpl.class);
	private static final long serialVersionUID = -6099090761866868162L;
	
	private CreditDAO creditDAO;

	@Override
	public void insert(Credit credit) {
		creditDAO.insert(credit);
	}

//	@Override
//	public void update(Credit credit) {
//		creditDAO.update(credit);
//	}

	@Override
	public void delete(Credit credit) {
		creditDAO.delete(credit);
		
	}

	@Override
	public List<Credit> search(Credit credit) {
		return creditDAO.search(credit);
	}

	@Override
	public List<Credit> getAll() {
		return creditDAO.getAll();
	}	

	@Override
	public Credit get(Integer id) {
		return creditDAO.get(id);
	}

	public CreditDAO getCreditDAO() {
		return creditDAO;
	}

	public void setCreditDAO(CreditDAO creditDAO) {
		this.creditDAO = creditDAO;
	}

	public static Log getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void setCreditAsUsed(Integer creditId, String reason) {
		creditDAO.setCreditAsUsed(creditId, reason);
		
	}
	
	public List<Credit> search(SearchCreditBean sb) {
		return creditDAO.search(sb);
	}

	@Override
	public List<Credit> getByCustomer(Integer cusId) {
		return creditDAO.getByCustomer(cusId);
	}
}
