package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import al.ozone.bl.dao.DiscountCardDAO;
import al.ozone.bl.manager.DiscountCardManager;
import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.DiscountCardGroup;

public class DiscountCardManagerImpl implements DiscountCardManager, Serializable {

	private static final long serialVersionUID = -2801351173473683667L;
	
	private DiscountCardDAO  discountCardDAO;

	@Override
	public void insert(DiscountCard discountCard) {
		discountCardDAO.insert(discountCard);
	}

	@Override
	public void delete(DiscountCard discountCard) {
		discountCardDAO.delete(discountCard);
	}

	@Override
	public List<DiscountCard> search(DiscountCard discountCard) {
		return discountCardDAO.search(discountCard);
	}

	@Override
	public List<DiscountCard> getAll() {
		return discountCardDAO.getAll();
	}	

	@Override
	public DiscountCard get(String id) {
		return discountCardDAO.get(id);
	}

	public DiscountCardDAO getDiscountCardDAO() {
		return discountCardDAO;
	}

	public void setDiscountCardDAO(DiscountCardDAO discountCardDAO) {
		this.discountCardDAO = discountCardDAO;
	}

	@Override
	public List<DiscountCardGroup> getCardGroups() {
		return discountCardDAO.getGroups();
	}
}
