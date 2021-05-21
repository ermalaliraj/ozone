package al.ozone.bl.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import al.ozone.bl.bean.ResultStatisticBean;
import al.ozone.bl.bean.SearchBeanEmailNewsletter;
import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.EmailsIntroDAO;
import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.model.PagedResult;

public class EmailsIntroDAOImpl extends BaseDAO implements EmailsIntroDAO, Serializable {

	private static final long serialVersionUID = 9130287716550440293L;

	@Override
	public EmailNewsletter get(String email) {
		EmailNewsletter en = (EmailNewsletter) getSqlSession().selectOne("EMAILS_INTRO.get", email);
        return en;
	}

	
	@Override
	public List<String> getAll() {
		@SuppressWarnings("unchecked")
		List<String> list = getSqlSession().selectList("EMAILS_INTRO.getAll", null);
        return list;
	}

	@Override
	public List<String> search(String email) {
		@SuppressWarnings("unchecked")
		List<String> list = getSqlSession().selectList("EMAILS_INTRO.search", email);
        return list;
	}

	@Override
	public int searchCount(SearchBeanEmailNewsletter email) {
		int totalCount = (Integer) getSqlSession().selectOne("EMAILS_INTRO.searchCount", email);
		return totalCount;
	}

	@Override
	public PagedResult<EmailNewsletter> loadLazy(SearchBeanEmailNewsletter e, int first, int pageSize) {
		RowBounds rowBound = new RowBounds(first, pageSize);
        PagedResult<EmailNewsletter> result = new PagedResult<EmailNewsletter>();
        
        @SuppressWarnings("unchecked")
		List<EmailNewsletter> list = getSqlSession().selectList("EMAILS_INTRO.searchLazy", e, rowBound);
        int totalCount = (Integer) getSqlSession().selectOne("EMAILS_INTRO.searchCount", e);
        result.setRowList(list);
        result.setTotalCount(totalCount);
        return result;
	}

	@Override
	public int update(EmailNewsletter en) {
		int a = getSqlSession().update("EMAILS_INTRO.update", en);
		return a;
	}


	@Override
	public List<String> getAdmins() {
		@SuppressWarnings("unchecked")
		List<String> list = getSqlSession().selectList("EMAILS_INTRO.getAdmins", null);
        return list;
	}


	@Override
	public void addEmail(EmailNewsletter en) {
		getSqlSession().insert("EMAILS_INTRO.insert", en);
	}
	

	@Override
	public void addEmailForCity(EmailNewsletter en) {
		en.setOpDate(new Date());
		getSqlSession().insert("EMAILS_INTRO.insertForCity", en);
	}
	
	@Override
	public List<ResultStatisticBean> getEmailsGroupByRegMonths() {
		@SuppressWarnings("unchecked")
		List<ResultStatisticBean> list = getSqlSession().selectList("EMAILS_INTRO.getEmailsGroupByRegMonths", null);
        return list;
	}

	@Override
	public Integer getCountEmails() {
		int totalCount = (Integer) getSqlSession().selectOne("EMAILS_INTRO.getCountEmails", null);
		return totalCount;
	}

}
