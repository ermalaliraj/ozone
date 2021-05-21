package al.ozone.bl.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.dao.EmailsIntroDAO;

public class EmailsIntroManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(EmailsIntroManagerTest.class);
    
    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }
    
    public void testGetAll(){
    	EmailsIntroDAO emailsIntroDAO = (EmailsIntroDAO) applicationContext.getBean("emailsIntroDAO");
    	
    	List<String> list = emailsIntroDAO.getAll();
    	
    	for (String string : list) {
			System.out.println(string);
		}
    	
    	list = emailsIntroDAO.search("TR");
    	for (String string : list) {
			System.out.println(string);
		}
    }
    
    public void testUpdate(){
    	EmailsIntroManager emailsIntroManager = (EmailsIntroManager) applicationContext.getBean("emailsIntroManager");
    	String email = "ermalalsiraj@yahoo.it";
    	
    	EmailNewsletter en = new EmailNewsletter(email);
    	en.setAcceptNewsletter(false);
    	int res = emailsIntroManager.update(en);
    	System.out.println(res);
    	
    	EmailNewsletter enRead = emailsIntroManager.get(email);
    	assertEquals(en.isAcceptNewsletter(), enRead.isAcceptNewsletter());
    	
//    	en.setAcceptNewsletter(true);
//    	emailsIntroManager.update(en);
//    	EmailNewsletter enRead2 = emailsIntroManager.get(email);
//    	assertEquals(en.isAcceptNewsletter(), enRead2.isAcceptNewsletter());
    }
}
