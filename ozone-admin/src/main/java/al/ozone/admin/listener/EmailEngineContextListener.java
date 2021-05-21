package al.ozone.admin.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import al.ozone.engine.email.EmailEngine;

public class EmailEngineContextListener implements ServletContextListener {

	protected static final transient Log logger = LogFactory.getLog(EmailEngineContextListener.class);
    private EmailEngine emailEngine;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        logger.debug("Starting EmailEngine from the Listener");
        emailEngine = (EmailEngine) applicationContext.getBean("emailEngine");
        emailEngine.start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	logger.debug("Stoping EmailEngine from the Listener");
        if (emailEngine != null) {
        	emailEngine.stopRunningSucceded();
        }
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:**//applicationContext-*.xml");
        EmailEngine emailEngine = (EmailEngine) applicationContext.getBean("emailEngine");
        emailEngine.start();
    }
}