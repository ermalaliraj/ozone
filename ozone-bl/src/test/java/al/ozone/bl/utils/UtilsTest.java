package al.ozone.bl.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;



public class UtilsTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(UtilsTest.class);
    
    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }
    
    public void testMessageUtil(){
//    	ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) applicationContext.getBean("messageSource");
//    	
//    	String s = MessageUtils.getMessage(messageSource, "uploads_folderName")	;
//    	System.out.println(s);
    	
    	int lengthToCut = 28;
    	
    	String string = "Ozone eshte nje faqe interneti qe ju jep mundesine te blini online nje sherbim ose produkt me ulje emimi deri ne 90%! Propozojme edo dite Oferta nga me te larmishmet, duke filluar nga sherbime s...";
    	System.out.println("'"+string.charAt(lengthToCut+1)+"'");
    	while(string.charAt(lengthToCut+1) != ' '){
    		System.out.println(string.charAt(lengthToCut+1));
    		lengthToCut++;
    	}
    	
    	char c = string.charAt(lengthToCut);
    	System.out.println("c:"+c);
    	String s = string.substring(0, lengthToCut+1);
    	System.out.println("s:"+s);
    }
    
    public void testFileExtension(){
    	String ext = ZUtils.getFileExtension("dfddg.fefwewe-dfgfd.jpeg");
    	System.out.println(ext);
    	assertEquals(ext, ".jpeg");
    }
    
    
}
