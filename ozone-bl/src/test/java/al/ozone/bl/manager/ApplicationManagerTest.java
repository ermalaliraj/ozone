package al.ozone.bl.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.SystemConfigBean;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class ApplicationManagerTest extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(ApplicationManagerTest.class);

	protected SystemConfigManager systemConfigManager;

	protected void setUp() throws Exception {
		super.setUp();
		systemConfigManager = (SystemConfigManager) applicationContext.getBean("systemConfigManager");
	}
	
	public void testPurchaseCRUD() throws Exception{	
    	List<SystemConfigBean> list  = systemConfigManager.getAll();
    	assertNotNull(list);
    	
    	SystemConfigBean entry = list.remove(0);
    	entry.setValue("value changed from junit");
    	list.add(entry);
   
    	systemConfigManager.save(list);
    }
	
	protected void tearDown(){

	}
	
}
