package al.ozone.bl.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.PartnerManager;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Partner;

public class PartnerManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(PartnerManagerTest.class);
    
	protected PartnerManager partnerManager;
	protected CityManager cityManager;
	
	protected City city = null;
	protected Partner p = null;
    
	protected void setUp() throws Exception {
		super.setUp();
		partnerManager = (PartnerManager) applicationContext.getBean("partnerManager");
		cityManager = (CityManager) applicationContext.getBean("cityManager");

		city = TestUtils.createCity("TR", "Test");

//		try {
//	        city = TestUtils.createCity("TR", "Test");
//	    	cityManager.insert(city);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
    public void testPartner() throws Exception{
    	//1. Insert a Partner in DB
        p = new Partner();
    	p.setName("insert from junit");
    	p.setAddress("vle dei Cadorna");
    	p.setCity(city);
    	p.setTel("32345677855");
    	p.setCel("32000000000");
    	p.setEmail("ere@erer.com");
    	p.setCategory(new Category(Category.CAT_OTHER));
    	partnerManager.insert(p);
    	
    	//2. Read the partner inserted in step 1
    	Partner readPartner = partnerManager.get(p.getId());
    	assertEquals(p, readPartner);

    	//3. Update the partner in DB, and read again to check if the update took effect
    	readPartner.setName("partner junit updated");
    	readPartner.setAddress("vle dei Cadorna updated");
    	readPartner.setTel("32345677855 updated");
    	readPartner.setCel("32000000000 updated");
    	readPartner.setEmail("ere@erer.com updated");
    	partnerManager.update(readPartner);
    	Partner readPartnerUpdated = partnerManager.get(p.getId());
    	assertEquals(readPartner, readPartnerUpdated);
    	
    	//4. Delete the partner, and read from DB to check that does not exist anymore
    	partnerManager.delete(readPartnerUpdated);    	
    	Partner readPartnerDeleted = partnerManager.get(readPartner.getId());
    	assertNull(readPartnerDeleted);  
    }
    
    public void testLazySearch() throws Exception{
//    	//1. Insert a Partner in DB
//        p = new Partner();
//    	p.setName("insert from junit PartnerXX");
//    	p.setAddress("vle dei Cadorna");
//    	p.setCity(city);
//    	p.setTel("32345677855");
//    	p.setCel("32000000000");
//    	p.setEmail("ere@erer.com");
//    	p.setCategory(new Category(Category.CAT_OTHER));
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//    	partnerManager.insert(p);
//
//    	Partner searchParams = new Partner();
//    	searchParams.setName("PartnerXX");
//    	PagedResult<Partner> result = partnerManager.loadLazy(searchParams, 0, 5);
//    	
//    	System.out.println("result.getTotalCount():"+result.getTotalCount());
//    	ZUtils.printListOnLogger(result.getRowList(), logger, "debug");
    }

}
