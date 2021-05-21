package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.bean.SearchInviteBean;
import al.ozone.bl.dao.CreditDAO;
import al.ozone.bl.dao.InviteDAO;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.CustomerInvited;
import al.ozone.bl.model.Invite;

public class InviteManagerImpl implements InviteManager, Serializable {

	private static final long serialVersionUID = 7695771521447452402L;
	protected static final transient Log logger = LogFactory.getLog(InviteManagerImpl.class);
	
	private InviteDAO inviteDAO;
	private CreditDAO creditDAO;
	
	public InviteDAO getInviteDAO() {
		return inviteDAO;
	}
	public void setInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}
	public CreditDAO getCreditDAO() {
		return creditDAO;
	}
	public void setCreditDAO(CreditDAO creditDAO) {
		this.creditDAO = creditDAO;
	}
	@Override
	public void insert(Invite invite) {
		inviteDAO.insert(invite);
	}
	@Override
	public List<CustomerInvited> getInvitationsForCustomer(Integer inviterId) {
		return inviteDAO.getInvitationsForCustomer(inviterId);
	}
	
	/**
	 * Return  whom invited the customer passed as parameter,
	 * only if firstPurchase is False.
	 * @param cusId customerId to check in DB who invited him.
	 * @return the inviter id
	 */
	@Override
	public Integer getWhoInvitedCustomer(Integer cusId) {
		return inviteDAO.getWhoInvitedCustomer(cusId);
	}
	
	/**
	 * Insert new credit to the inviter, and set his firstPurchase as DONE.
	 */
	@Override
	public void insertCreditToInviter(Credit newCredit, Invite invite) {
		creditDAO.insert(newCredit);		
		inviteDAO.firstPurchaseDone(invite);
		logger.info("Added "+newCredit.getValue()+"L to customer "+invite.getInviterId()+" for invitation of customer "+invite.getInvitedEmail()+" ("+invite.getInvitedId()+"). firsPurchase set to Y");
	}
	
	@Override
	public List<Invite> search(SearchInviteBean invite) {
		return inviteDAO.search(invite);
	}
	
}
