package al.ozone.bl.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class ApplicationConfigTest extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(ApplicationConfigTest.class);

	protected ApplicationConfig applicationConfig;

	protected void setUp() throws Exception {
		super.setUp();
		applicationConfig = (ApplicationConfig) applicationContext.getBean("applicationConfig");
	}
	
	public void testApplicationConfig() {
		String language = applicationConfig.getLanguage();
		assertTrue(language.equals("al"));
		String theme = applicationConfig.getTheme();
		assertEquals("cupertino", theme);
		int pubDuration = applicationConfig.getPublicationDuration();
		assertEquals(3, pubDuration);
		int maxBadLogin = applicationConfig.getMaxBadLogins();
		assertEquals(3, maxBadLogin);

		applicationConfig.setMaxBadLogins(5);
		maxBadLogin = applicationConfig.getMaxBadLogins();
		assertEquals(5, maxBadLogin);
    }
	
	protected void tearDown(){

	}
	
}
