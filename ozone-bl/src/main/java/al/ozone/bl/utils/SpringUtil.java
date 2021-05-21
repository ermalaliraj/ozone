package al.ozone.bl.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

public class SpringUtil {
    public static <T> T getBeanFromSpring(String beanName) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath*:**//applicationContext-*.xml" });
//        DataSourceInitializer dataSourceInitializer = (DataSourceInitializer) applicationContext.getBean("dataSourceInitializer");

//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//                new String[] {
//                        "applicationContext-email.xml",
//                        "applicationContext-BatchEngine.xml",
//                        "applicationContext-manager.xml",
//                        "applicationContext-dao.xml",
//                        "applicationContext-resources.xml",
//                        "applicationContext-service.xml"
//                });

        @SuppressWarnings("unchecked")
        T object = (T) applicationContext.getBean(beanName);
        return object;

    }
}
