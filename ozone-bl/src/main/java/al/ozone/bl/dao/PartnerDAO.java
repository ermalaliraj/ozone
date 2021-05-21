package al.ozone.bl.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import al.ozone.bl.model.Category;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.model.Partner;

public interface PartnerDAO  extends GenericDAO<Partner, Integer>{
	
	public List<Category> getCategories();
	public Category getCategory(Integer id);
	public PagedResult<Partner> loadLazy(Partner partner, int skipResults, int maxResults);
	public int searchCount(Partner p);
	public UserDetails loadUserByUsername(String emailPartner);
	
}
