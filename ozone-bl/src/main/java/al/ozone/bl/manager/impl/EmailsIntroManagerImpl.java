package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.EmailsIntroManager;
import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchBeanEmailNewsletter;
import al.ozone.bl.dao.EmailsIntroDAO;

public class EmailsIntroManagerImpl implements EmailsIntroManager, Serializable {

	private static final long serialVersionUID = 4005234381093654605L;
	protected static final transient Log logger = LogFactory.getLog(EmailsIntroManagerImpl.class);
	
	private EmailsIntroDAO emailsIntroDAO;
	
	public void setEmailsIntroDAO(EmailsIntroDAO emailsIntroDAO) {
		this.emailsIntroDAO = emailsIntroDAO;
	}

	@Override
	public EmailNewsletter get(String email) {
		return emailsIntroDAO.get(email);
	}
	
	@Override
	public List<String> getAll() {
		return emailsIntroDAO.getAll();
	}

	@Override
	public List<String> search(String email) {
		return emailsIntroDAO.search(email);
	}

	@Override
	public int searchCount(SearchBeanEmailNewsletter email) {
		return emailsIntroDAO.searchCount(email);
	}

	@Override
	public PagedResult<EmailNewsletter> loadLazy(SearchBeanEmailNewsletter email, int first, int pageSize) {
		return emailsIntroDAO.loadLazy(email, first, pageSize);
	}

	@Override
	public int update(EmailNewsletter en) {
		return emailsIntroDAO.update(en);
	}

	@Override
	public void add(EmailNewsletter en) {
		emailsIntroDAO.addEmail(en);
		emailsIntroDAO.addEmailForCity(en);
	}

	@Override
	public List<ResultStatisticBean> getEmailsGroupByRegMonths() {
		return emailsIntroDAO.getEmailsGroupByRegMonths();
	}

	@Override
	public Integer getCountEmails() {
		return emailsIntroDAO.getCountEmails();
	}
	
}
