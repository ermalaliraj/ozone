package al.ozone.bl.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.dao.CodeGeneratorDAO;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class EnvConfigTest extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(UtilsTest.class);

	public void testEnvConfig() {
		
		EnvConfig envConfig = (EnvConfig) applicationContext.getBean("envConfig");
		String f = envConfig.getUploadsFolder();
		String u = envConfig.getUploadsUrl();
		
		System.out.println(f);
		System.out.println(u);
	   
	}
}