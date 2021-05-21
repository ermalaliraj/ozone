package al.ozone.bl.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import al.ozone.bl.dao.PartnerDAO;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.model.Partner;


public class PartnerDAOImpl extends GenericDAOImpl<Partner, Integer> implements PartnerDAO  {

	private static final long serialVersionUID = 5826029176203027598L;
	
	public PartnerDAOImpl(Class<Partner> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<Category> getCategories() {
		@SuppressWarnings("unchecked")
		List<Category> list = getSqlSession().selectList("PARTNER.getAllCategories",null);
		return list;
	}

	@Override
	public Category getCategory(Integer id) {
		Category c = (Category) getSqlSession().selectOne("PARTNER.getCategory",id);
		return c;
	}

	@Override
	public PagedResult<Partner> loadLazy(Partner partner, int skipResults, int maxResults) {
		RowBounds rowBound = new RowBounds(skipResults, maxResults);
        PagedResult<Partner> result = new PagedResult<Partner>();
        
        @SuppressWarnings("unchecked")
		List<Partner> list = getSqlSession().selectList("PARTNER.searchLazy", partner, rowBound);
        int totalCount = (Integer) getSqlSession().selectOne("PARTNER.searchCount", partner);
        result.setRowList(list);
        result.setTotalCount(totalCount);
        return result;
	}

	@Override
	public int searchCount(Partner p) {
		int totalCount = (Integer) getSqlSession().selectOne("PARTNER.searchCount", p);
		return totalCount;
	}

	@Override
	public UserDetails loadUserByUsername(String emailPartner) {
		//logger.info("Login for Partner '" + emailPartner + "'");

		Partner partner = (Partner) getSqlSession().selectOne("PARTNER.getByEmail", emailPartner);
        if (partner == null) {
            //logger.warn("EmailPartner'"+emailPartner+"' not found in database ");
            throw new UsernameNotFoundException(emailPartner);
        } else {
           // logger.info("EmailPartner '" + emailPartner +"' found");
        }
        
        return partner;
	}
}
