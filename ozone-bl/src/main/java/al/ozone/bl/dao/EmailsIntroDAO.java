package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchBeanEmailNewsletter;
import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.model.PagedResult;

public interface EmailsIntroDAO{
	
	public EmailNewsletter get(String email);

	public List<String> getAll();

	public List<String> search(String email);

	public int searchCount(SearchBeanEmailNewsletter email);

	public PagedResult<EmailNewsletter> loadLazy(SearchBeanEmailNewsletter e, int first, int pageSize);

	public int update(EmailNewsletter en);

	public List<String> getAdmins();

	public void addEmail(EmailNewsletter en);

	public void addEmailForCity(EmailNewsletter en);

	public List<ResultStatisticBean> getEmailsGroupByRegMonths();

	public Integer getCountEmails();

}
