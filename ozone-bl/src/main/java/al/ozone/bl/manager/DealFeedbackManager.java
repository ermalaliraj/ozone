package al.ozone.bl.manager;

import java.util.List;

import al.ozone.bl.model.DealFeedback;

public interface DealFeedbackManager {
	
	public DealFeedback get(Integer id);
	
	public void insert(DealFeedback dFBack) throws Exception;
	
	public void delete(DealFeedback dFBack) throws Exception;
	
	public List<DealFeedback> search(DealFeedback dFBack);
	
}
