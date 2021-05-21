package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchCreditBean;
import al.ozone.bl.model.Credit;

public interface CreditDAO extends GenericDAO<Credit, Integer> {
	
	public void setCreditAsUsed(Integer creditId, String reason) ;
	public List<Credit> search(SearchCreditBean sb);
	public List<Credit> getByCustomer(Integer cusId);
}
