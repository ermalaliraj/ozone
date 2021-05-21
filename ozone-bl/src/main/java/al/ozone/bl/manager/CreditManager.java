package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.SearchCreditBean;
import al.ozone.bl.model.Credit;

public interface CreditManager {

	public Credit get(Integer id);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void insert (Credit credit);
	
	//public void update (Credit credit);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void delete (Credit credit);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Credit> search(Credit credit);
	
	public List<Credit> getAll();

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void setCreditAsUsed(Integer creditId, String reason);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<Credit> search(SearchCreditBean sb);
	
	public List<Credit> getByCustomer(Integer cusId);

}