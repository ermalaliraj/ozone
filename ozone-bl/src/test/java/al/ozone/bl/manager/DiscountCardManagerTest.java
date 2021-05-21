package al.ozone.bl.manager;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;

public class DiscountCardManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(DiscountCardManagerTest.class);
    
    protected DiscountCardManager discountCardManager;
	protected DiscountCard discountCard = null;
    
	protected void setUp() throws Exception {
		super.setUp();
		discountCardManager = (DiscountCardManager) applicationContext.getBean("discountCardManager");
	}
	
    public void testDiscountCardCRUD() throws Exception{
    	
    	discountCard = TestUtils.createDiscountCard(10);
		discountCardManager.insert(discountCard);
    	
    	//2. Read the DiscountCard inserted in step 1
		DiscountCard readDiscountCard = discountCardManager.get(discountCard.getId());
    	assertEquals(discountCard, readDiscountCard);

    	//3. Update the DiscountCard in DB, and read again to check if the update took effect
    	readDiscountCard.setPercDiscount(20);
    	readDiscountCard.setUsedDate(new Date());
    	DiscountCard readDiscountCardUpdated = discountCardManager.get(discountCard.getId());
    	assertEquals(readDiscountCard, readDiscountCardUpdated);
    	
    	//4. Delete the DiscountCard, and read from DB to check that does not exist anymore
    	discountCardManager.delete(discountCard);    	
    	DiscountCard readDiscountCardDeleted = discountCardManager.get(discountCard.getId());
    	assertNull(readDiscountCardDeleted);  
    }
    
    public void testDiscountCard2() throws Exception{
    	
    	discountCard = TestUtils.createDiscountCard(10);
		discountCardManager.insert(discountCard);
    	
    	//2. Read the DiscountCard inserted in step 1
		List<DiscountCard> list = discountCardManager.search(discountCard);
		System.out.println(list);
    }
    
    protected void tearDown(){
		try{
			discountCardManager.delete(discountCard);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
