package al.ozone.bl.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import al.ozone.bl.bean.SearchInviteBean;
import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.InviteDAO;
import al.ozone.bl.model.CustomerInvited;
import al.ozone.bl.model.Invite;

public class InviteDAOImpl extends BaseDAO implements InviteDAO, Serializable {

	private static final long serialVersionUID = 6961027000283074749L;

	@Override
	public void insert(Invite invite) {
		invite.setOperationDate(new Date());
		getSqlSession().insert("INVITE.insert", invite);
	}

	@Override
	public List<CustomerInvited> getInvitationsForCustomer(Integer inviterId) {
		@SuppressWarnings("unchecked")
		List<CustomerInvited> list = getSqlSession().selectList("INVITE.getInvitationsForCustomer", inviterId);
		return list;
	}

	@Override
	public Integer getWhoInvitedCustomer(Integer cusId) {
		Integer c = (Integer) getSqlSession().selectOne("INVITE.getWhoInvitedCustomer", cusId);
		return c;
	}

	@Override
	public void firstPurchaseDone(Invite invite) {
		getSqlSession().update("INVITE.firstPurchaseDone", invite);
	}

	@Override
	public List<Invite> search(SearchInviteBean sb) {
		@SuppressWarnings("unchecked")
		List<Invite> list = getSqlSession().selectList("INVITE.search", sb);
		return list;
	}

	
}
