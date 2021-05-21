package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;

import al.ozone.bl.model.Category;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.model.Partner;

public interface PartnerManager extends UserDetailsService{
	
	public Partner get(Integer id);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void insert(Partner partner) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void update(Partner partner) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void delete(Partner partner) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Partner> search(Partner partner);
	
	public List<Partner> getAll();
	
	public List<Category> getCategories();
	
	public Category getCategory(Integer id);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public PagedResult<Partner> loadLazy(Partner partner, int skipResults, int maxResults);

	public int searchCount(Partner p);

}
