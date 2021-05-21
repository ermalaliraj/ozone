package al.ozone.bl.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import al.ozone.bl.dao.DealChoiceDAO;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Partner;

public class DealManagerTest  extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(DealManagerTest.class);
	
	protected DealManager dealManager;
	protected DealChoiceDAO dealChoiceDAO ;
	protected PartnerManager partnerManager;
	protected CityManager cityManager;
	
	protected City city = null;
	protected Partner p = null;
	protected Deal deal = null;

	protected void setUp() throws Exception {
		super.setUp();
		dealManager = (DealManager) applicationContext.getBean("dealManager");
		dealChoiceDAO = (DealChoiceDAO) applicationContext.getBean("dealChoiceDAO");
		partnerManager = (PartnerManager) applicationContext.getBean("partnerManager");
		cityManager = (CityManager) applicationContext.getBean("cityManager");
		try { 
	        city = TestUtils.createCity("TR", "Test");
	        p = TestUtils.createPartner("Partner for deal junit", city);
//	    	deal = TestUtils.createDeal("Scopri l'oferta del....junit", p);
	    	deal = new Deal();
	    	deal.setId(1);

//	    	cleanDB();
//	    	cityManager.insert(city);
//	        partnerManager.insert(p);
//	        dealManager.insert(deal);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    public void testDealCRUD() throws Exception{
//    	// 1. Insert a new Deal

    	//2. Read the Deal inserted in step 1
    	Deal readDeal = dealManager.get(1);
    	System.out.println(readDeal);
    	assertEquals(deal, readDeal);
    	
//    	//3. Update the partner in DB, and read again to check if the update took effect
//    	readDeal.setTitle("Scopri l'oferta del....junit update");
//    	readDeal.setPrice(1701);
//    	readDeal.setFullPrice(3201);
//    	readDeal.setMinCustomers(4);
//    	readDeal.setMaxCustomers(101);
//    	readDeal.setPartner(p);
//    	readDeal.setClientFullName("rappresentante nome up");
//    	readDeal.setClientCel("rep cel up");
//    	readDeal.setBrokerFullName("broker junit up");
//    	readDeal.setBrokerCel("broker cel up");	
//    	readDeal.setPublished(true);
//    	readDeal.setMainImgName("nomeImg.jsp up");
//    	readDeal.setSynthesis("synthesis...... up");
//    	readDeal.setConditions("conditions.... up");
//    	readDeal.setDescription("descriptions.... up");  
//    	readDeal.setApprovedForPublish(false);
//    	readDeal.setApprovedUser("flori");
//    	readDeal.setLastUpdateUser("ermal");
//    	
//    	dealManager.update(readDeal);
//    	Deal readDealUpdated = dealManager.get(deal.getId());
//    	assertEquals(readDeal, readDealUpdated);
//    	
    	//4. Delete the deal(and the partner), and read from DB to check that does not exist anymore
    	dealManager.delete(deal);    	
    	Deal readDealDeleted = dealManager.get(readDeal.getId());
    	assertNull(readDealDeleted); 
    }
    
    public void testDealChoiceCRUD() throws Exception{
//    	Deal deal = TestUtils.createDeal("Scopri l'oferta del....junit", p);    
//    	dealManager.insert(deal);
    	DealChoice dc = TestUtils.createDealChoice(deal.getId(), 1);
    	DealChoice dc2 = TestUtils.createDealChoice(deal.getId(), 2);
    	DealChoice dc3 = TestUtils.createDealChoice(deal.getId(), 3);
    	
    	dealChoiceDAO.insert(dc);
    	dealChoiceDAO.insert(dc2);
    	dealChoiceDAO.insert(dc3);
    	
    	List<DealChoice> list = dealChoiceDAO.getChoicesForDeal(deal.getId());
    	System.out.println(list);
    	assertEquals(list.size(), 3);
    	
    	dealChoiceDAO.delete(dc);
    	dealChoiceDAO.delete(dc2);
    	dealChoiceDAO.delete(dc3);
    	
    	list = dealChoiceDAO.getChoicesForDeal(deal.getId());
    	System.out.println(list);
    	assertEquals(list.size(), 0);
    	
//    	DealChoice readDeal = dealChoiceDAO.get(1, 2);
//    	System.out.println(readDeal);
    	//assertEquals(deal, readDeal);
    }
    
    public void testDealChoiceGET() throws Exception{
//    	Deal deal = TestUtils.createDeal("Scopri l'oferta del....junit", p);    
//    	dealManager.insert(deal);
    	DealChoice dc = TestUtils.createDealChoice(deal.getId(), 1);
    	
    	dealChoiceDAO.insert(dc);
    	
    	DealChoice dcRead = dealChoiceDAO.get(deal.getId(), 1);
    	System.out.println(dcRead);
    	
    	dealChoiceDAO.delete(dcRead);
    	
    	DealChoice dcReadDeleted = dealChoiceDAO.get(deal.getId(), 1);
    	System.out.println(dcReadDeleted);
    	assertNull(dcReadDeleted);
    	
//    	DealChoice readDeal = dealChoiceDAO.get(1, 2);
//    	System.out.println(readDeal);
    	//assertEquals(deal, readDeal);
    }
    
//    protected void tearDown(){
//		cleanDB();
//	}
//
//	private void cleanDB() {
//		try{
//			dealManager.delete(deal);
//	    	partnerManager.delete(p);
//	    	cityManager.delete(city);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
    
}
