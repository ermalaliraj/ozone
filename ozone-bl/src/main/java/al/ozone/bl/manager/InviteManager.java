package al.ozone.bl.manager;

import java.util.List;

import al.ozone.bl.bean.SearchInviteBean;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.CustomerInvited;
import al.ozone.bl.model.Invite;

public interface InviteManager {

	public void insert(Invite invite);

	public List<CustomerInvited> getInvitationsForCustomer(Integer inviterId);

	/**
	 * Return  whom invited the customer passed as parameter,
	 * only if firstPurchase is False.
	 * @param cusId customerId to check in DB who invited him.
	 * @return the inviter id
	 */
	public Integer getWhoInvitedCustomer(Integer cusId);

	public void insertCreditToInviter(Credit newCredit, Invite invite);

	public List<Invite> search(SearchInviteBean invite);
	
}
