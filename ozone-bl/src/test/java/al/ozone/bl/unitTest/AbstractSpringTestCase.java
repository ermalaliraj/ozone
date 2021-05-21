package al.ozone.bl.unitTest;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

public class AbstractSpringTestCase extends TestCase {

    protected ClassPathXmlApplicationContext applicationContext;

    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        applicationContext = new ClassPathXmlApplicationContext(getApplicationContextPath());
        DataSourceInitializer dataSourceInitializer = (DataSourceInitializer) applicationContext.getBean("dataSourceInitializer");

    }
}
