package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.DealFeedbackManager;
import al.ozone.bl.dao.GenericDAO;
import al.ozone.bl.model.DealFeedback;

public class DealFeedbackManagerImpl implements DealFeedbackManager, Serializable {

	private static final long serialVersionUID = 1L;
	protected static final transient Log logger = LogFactory.getLog(DealFeedbackManagerImpl.class);
	
	//private DealFeedbackDAO dealFeedbackDAO;
	private GenericDAO<DealFeedback, Integer> dealFeedbackDAO;

	public GenericDAO<DealFeedback, Integer> getDealFeedbackDAO() {
		return dealFeedbackDAO;
	}

	public void setDealFeedbackDAO(GenericDAO<DealFeedback, Integer> dealFeedbackDAO) {
		this.dealFeedbackDAO = dealFeedbackDAO;
	}

	@Override
	public DealFeedback get(Integer id) {
		return dealFeedbackDAO.get(id);
	}

	@Override
	public void insert(DealFeedback dFBack) throws Exception {
		dFBack.setOpDate(new Date());
		dealFeedbackDAO.insert(dFBack);
	}

	@Override
	public void delete(DealFeedback dFBack) throws Exception {
		dealFeedbackDAO.delete(dFBack);
	}

	@Override
	public List<DealFeedback> search(DealFeedback dFBack) {
		return dealFeedbackDAO.search(dFBack);
	}	
	
}
