package al.ozone.bl.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.model.City;



public class CitiesManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CitiesManagerTest.class);
    
    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }
    
    public void testCityCRUD(){
    	CityManager cityManager = (CityManager) applicationContext.getBean("cityManager");
    	
    	//1. Insert
    	City c = new City();
    	c.setId("TC");
    	c.setName("TestCity");
    	
    	cityManager.insert(c); 
    	
    	//2. Read 
    	City readCity = cityManager.get(c.getId());
    	assertEquals(c, readCity);
    	
    	//4. Update
    	readCity.setName("Vlore1");
    	cityManager.update(readCity);
    	City readCityUpdated = cityManager.get(readCity.getId());
    	assertEquals(readCityUpdated.getName(), readCity.getName());
    	
    	//5. Delete 
    	cityManager.delete(readCityUpdated);
    	City readCityDeleted = cityManager.get(readCity.getId());
    	assertNull(readCityDeleted);  	    	
    }
}
