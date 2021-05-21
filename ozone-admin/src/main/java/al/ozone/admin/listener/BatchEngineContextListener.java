package al.ozone.admin.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import al.ozone.engine.batch.BatchEngine;
import al.ozone.engine.email.EmailEngine;

/**
 * Listener which starts BatchEngine 
 * 
 * @deprecated Actually BatchEngine system is been running using a 
 * Operating System Task Scheduler, which runs the batch in the 
 * specific time defined in the Task description.
 */
public class BatchEngineContextListener implements ServletContextListener {

	protected static final transient Log logger = LogFactory.getLog(BatchEngineContextListener.class);
    private BatchEngine batchEngine;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        logger.info("Starting BatchEngine from the Listener");
        batchEngine = (BatchEngine) applicationContext.getBean("batchEngine");
        batchEngine.start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	logger.info("Stoping BatchEngine from the Listener");
        if (batchEngine != null) {
        	//batchEngine.stopRunningSucceded(); 
        }
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:**//applicationContext-*.xml");
        EmailEngine emailEngine = (EmailEngine) applicationContext.getBean("emailEngine");
        emailEngine.start();
    }
}