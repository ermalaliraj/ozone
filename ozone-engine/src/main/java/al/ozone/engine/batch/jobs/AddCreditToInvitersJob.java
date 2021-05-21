package al.ozone.engine.batch.jobs;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Invite;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.ZUtils;

/** 

 * @author Ermal Aliraj
 */
public class AddCreditToInvitersJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(AddCreditToInvitersJob.class);
	
	//private PublicationManager publicationManager;
	private DealManager dealManager;
	private PurchaseManager purchaseManager;
	private InviteManager inviteManager;
	private ApplicationConfig applicationConfig;

	public void execute() throws Exception {
		List<Deal> closedDeals = dealManager.getDealsEndToday();
		
		for (Deal d : closedDeals) {
			List<Purchase> purchList = purchaseManager.getAllPurchasesForDeal(d.getId());
			for (Purchase purchase : purchList) {
				Integer cusId = purchase.getCustomer().getId(); //invited
				Integer invitedBy = inviteManager.getWhoInvitedCustomer(cusId);//inviter
				if(invitedBy!=null){
					int defCreditVal = applicationConfig.getBonusValue();
					int creditDuration = applicationConfig.getCreditDuration();
					Credit newCredit = new Credit();
					newCredit.setAssignedDate(new Date());
					newCredit.setValidDate(ZUtils.addMonthsToDate(new Date(), creditDuration)); 
					newCredit.setValue(defCreditVal);
					newCredit.setType(Credit.TYPE_BENEFIT);
					newCredit.setCustomer(new Customer(invitedBy));
					newCredit.setAbout("Shtim "+defCreditVal+" Lek per ftesen e perdoruesit nr."+cusId);
					
					Invite invite = new Invite();
					invite.setInviterId(invitedBy);
					invite.setInvitedId(cusId);
					inviteManager.insertCreditToInviter(newCredit, invite);
				}
			}
		}
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public InviteManager getInviteManager() {
		return inviteManager;
	}
	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
	}
	public PurchaseManager getPurchaseManager() {
		return purchaseManager;
	}
	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}
	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
