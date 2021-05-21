package al.ozone.bl.manager;

import java.util.List;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchBeanEmailNewsletter;
import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.model.PagedResult;

public interface EmailsIntroManager {
   
	public EmailNewsletter get(String email);
	
	public List<String> getAll();
	
	public List<String> search(String email);

	public int searchCount(SearchBeanEmailNewsletter email);

	public PagedResult<EmailNewsletter> loadLazy(SearchBeanEmailNewsletter email, int first, int pageSize);

	public int update(EmailNewsletter en);
	
	public void add(EmailNewsletter en);

	public List<ResultStatisticBean> getEmailsGroupByRegMonths();

	public Integer getCountEmails();
	
}
