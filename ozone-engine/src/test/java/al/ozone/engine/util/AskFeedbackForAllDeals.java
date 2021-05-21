package al.ozone.engine.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.bl.manager.DealManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Purchase;
import al.ozone.engine.email.EmailRobot;

public class AskFeedbackForAllDeals{

    protected static final transient Log logger = LogFactory.getLog(AskFeedbackForAllDeals.class);
    
    public static void main(String[] args) {
    	ClassPathXmlApplicationContext applicationContext;
    	applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath*:**//applicationContext-*.xml" });
    		
    	DealManager dealManager = (DealManager) applicationContext.getBean("dealManager");    	
    	PurchaseManager purchaseManager = (PurchaseManager) applicationContext.getBean("purchaseManager");
    	EmailRobot emailRobot = (EmailRobot) applicationContext.getBean("emailRobot");

    	// for each closed deals
    	// take all emails, of coupons with status USED
    	// send email to each one asking feedback
    	
    	List<Deal> allDeals = dealManager.getAllClosedDeals();
    	logger.debug("All closed deals:"+allDeals.size());
    	for (Deal deal : allDeals) {    			
    		List<Purchase> purList = purchaseManager.getAllPurchasesForDeal(deal.getId());
    		logger.debug(purList.size()+" purchases for deal "+deal.getId());
    		for (Purchase pur : purList) {
    			//only if any coupons of this purchase has been used
    			if(purchaseManager.isPurchaseUsed(pur.getId())){
					//if the purchase to which this coupon belongs to,
					//isFeedbackRequestedForDeal=false, ask the feedback
					if(!pur.isFeedbackRequested()){
						try {	
							Email email = new Email("DealFeedback");
							email.setSubject("Vleresim per sherbimin e "+deal.getPartner().getName());
							email.addParameter("dealId", deal.getId());
							email.addParameter("dealTitle", deal.getTitle());
							email.addParameter("partner", deal.getPartner().getName());
							email.addParameter("cusEmail", pur.getCustomer().getEmail());
							email.addParameter("cusPwd", pur.getCustomer().getPassword());
							email.addTo(pur.getCustomer().getEmail());
							
							emailRobot.sendEmail(email);    
							
							purchaseManager.setFeedbackRequestedForPurchase(pur.getId(), true);
							logger.debug("Purchase "+pur.getId()+" feedbackRequestedForPurchase set to TRUE");
						} catch (Exception e) {
							logger.error("A problem occoured trying to send feedback for customer "+pur.getCustomer().getEmail()+" of purchase:"+pur.getId());
						}
					}else{
						logger.info("Purchase "+pur.getId()+" has already sent the feedback");
					}	
    			} else{
    				logger.info("Purchase "+pur.getId()+" has not been used.");
    			}
			}
		}
	}        
}
