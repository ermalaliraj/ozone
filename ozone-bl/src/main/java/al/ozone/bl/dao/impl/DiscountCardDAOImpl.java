package al.ozone.bl.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.dao.DiscountCardDAO;
import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.DiscountCardGroup;

public class DiscountCardDAOImpl extends GenericDAOImpl<DiscountCard, String> implements DiscountCardDAO {

	private static final long serialVersionUID = -5844677071408780821L;
	protected static final transient Log logger = LogFactory.getLog(DiscountCardDAOImpl.class);

	public DiscountCardDAOImpl(Class<DiscountCard> persistentClass) {
		super(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountCardGroup> getGroups() {
		List<DiscountCardGroup> list = getSqlSession().selectList("DISCOUNTCARD.getGroups");
        return list;
	}
}
