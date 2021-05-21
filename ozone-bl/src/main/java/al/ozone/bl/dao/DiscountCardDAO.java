package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.DiscountCardGroup;

public interface DiscountCardDAO extends GenericDAO<DiscountCard, String> {
	
	public List<DiscountCardGroup> getGroups();
}
