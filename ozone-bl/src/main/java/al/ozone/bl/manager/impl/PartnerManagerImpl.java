package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import al.ozone.bl.dao.PartnerDAO;
import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.model.Partner;
import org.springframework.transaction.annotation.Transactional;

public class PartnerManagerImpl implements PartnerManager, UserDetailsService, Serializable {

	private static final long serialVersionUID = 2080516648759316284L;
	
	private PartnerDAO partnerDAO; 
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private void getPasswordEncoderFromSpring() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
	        			new String[] {
							"applicationContext-manager.xml",
							"applicationContext-dao.xml",
							"applicationContext-resources.xml",
							"applicationContext-service.xml"
				        });
        passwordEncoder = (ShaPasswordEncoder) applicationContext.getBean("passwordEncoder");
	}
	
	public Partner get(Integer id) {
		return partnerDAO.get(id);
	}

	public void insert(Partner partner) throws Exception {
        getPasswordEncoderFromSpring();
        String encodedPassword = null;
        if(partner.getPassword()!=null){
        	encodedPassword = passwordEncoder.encodePassword(partner.getPassword(), null);
        }
        partner.setPassword(encodedPassword);
		partnerDAO.insert(partner);
	}

	public void update(Partner partner) throws Exception {
		getPasswordEncoderFromSpring();
		Partner dbPartner = partnerDAO.get(partner.getId());
		
        boolean encode = partner.getPassword()!=null && !partner.getPassword().equals(dbPartner.getPassword());
        if (encode) {
            String encodedPassword = passwordEncoder.encodePassword(partner.getPassword(), null);
            partner.setPassword(encodedPassword);
        }
        
		partnerDAO.update(partner);
	}

	public void delete(Partner partner) throws Exception {
		partnerDAO.delete(partner);
	}
	
	public List<Partner> search(Partner partner) {
		return partnerDAO.search(partner);
	}

	@Override
	public List<Partner> getAll() {
		return partnerDAO.getAll();
	}

	@Override
	public List<Category> getCategories() {	
		return partnerDAO.getCategories();
	}

	public PartnerDAO getPartnerDAO() {
		return partnerDAO;
	}

	public void setPartnerDAO(PartnerDAO partnerDAO) {
		this.partnerDAO = partnerDAO;
	}

	@Override
	public Category getCategory(Integer id) {
		return partnerDAO.getCategory(id);
	}

	@Override
	public PagedResult<Partner> loadLazy(Partner partner, int skipResults, int maxResults) {
		return partnerDAO.loadLazy(partner, skipResults, maxResults);
	}

	@Override
	public int searchCount(Partner p) {
		return partnerDAO.searchCount(p);
	}
	
    public UserDetails loadUserByUsername(String emailPartner) {
        return partnerDAO.loadUserByUsername(emailPartner);
    }

}
