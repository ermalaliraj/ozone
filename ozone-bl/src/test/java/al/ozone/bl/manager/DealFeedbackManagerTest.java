package al.ozone.bl.manager;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.DealFeedback;

public class DealFeedbackManagerTest  extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(DealFeedbackManagerTest.class);
	
	private DealFeedbackManager dealFeedbackManager;
	
	protected DealFeedback dfb = null;

	protected void setUp() throws Exception {
		super.setUp();
	}
	
    public void testDealCRUD() throws Exception{
    	dealFeedbackManager = (DealFeedbackManager) applicationContext.getBean("dealFeedbackManager");

//    	DealFeedback dealFeedback = dealFeedbackManager.get(1);
//    	System.out.println(dealFeedback);
//    	
//    	DealFeedback df = new DealFeedback();
//    	
//    	df.setDealId(1);
//    	df.setCustomerId(1);
//    	df.setBody("prova nga junit");
//    	
//    	System.out.println("inserting feedback:"+df);
//    	dealFeedbackManager.insert(df);
    	
    	DealFeedback sb = new DealFeedback();
    	sb.setDealId(1);
    	List<DealFeedback> list = dealFeedbackManager.search(sb);
    	System.out.println(list);
    	
    }
    
    
}
