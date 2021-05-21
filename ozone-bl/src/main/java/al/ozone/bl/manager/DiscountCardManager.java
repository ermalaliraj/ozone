package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.DiscountCardGroup;

public interface DiscountCardManager {
	
	public DiscountCard get(String id);
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void insert(DiscountCard discountCard) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public void delete(DiscountCard discountCard) throws Exception;
	
	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<DiscountCard> search(DiscountCard discountCard);
	
	public List<DiscountCard> getAll();

	@Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
	public List<DiscountCardGroup> getCardGroups();
}
