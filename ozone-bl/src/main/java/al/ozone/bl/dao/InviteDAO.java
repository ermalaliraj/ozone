package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchInviteBean;
import al.ozone.bl.model.CustomerInvited;
import al.ozone.bl.model.Invite;

public interface InviteDAO{
	
	public void insert(Invite invite);

	public List<CustomerInvited> getInvitationsForCustomer(Integer inviterId);

	public Integer getWhoInvitedCustomer(Integer cusId);
	
	public void firstPurchaseDone(Invite invite);

	public List<Invite> search(SearchInviteBean invite);

}
