package al.ozone.bl.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.dao.CodeGeneratorDAO;

public class CodeGeneratorTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CodeGeneratorTest.class);
    
    protected String[] getApplicationContextPath() {
        return new String[] { "classpath*:**//applicationContext-*.xml" };
    }
    
    public void testPartner(){
    	CodeGeneratorDAO codeGeneratorDAO = (CodeGeneratorDAO) applicationContext.getBean("codeGeneratorDAO");
    	
    	Map<String, String> m = new HashMap<String, String>();
    	
    	//1. generate DC 1000 codes
    	String id = "";
    	long start = System.currentTimeMillis();
    	for (int i = 0; i < 1000; i++) {
    		id = codeGeneratorDAO.generateDiscountCardCode();
    		m.put(id, id);
		}
    	long end = System.currentTimeMillis();
    	System.out.println("map.size = "+m.size());
    	System.out.println("Generated codes in "+(end-start)+" milliseconds");
    	
    	
    	//Once creating a map with 1000 codes generated from the system, lets see if 
    	//we can catch at least 1 in 10.000.000 tries.
    	for (int i = 0; i < 10000000; i++) {
    		id = getRandomAlphaNumeric(DCARD_CODE_LEN);
    		if(m.containsKey(id)){
        		System.err.println(i+"th iteration exit with value found:"+m.get(id));
        		break;
        	}
		}
    	System.out.println("exitId:"+id);
    	end = System.currentTimeMillis();
    	System.out.println("Total Finished in "+(end-start)+" seconds");
    }
    
    private static final int DCARD_CODE_LEN = 7;
    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String getRandomAlphaNumeric(int len) {  
        StringBuffer sb = new StringBuffer(len);  
        for (int i=0;  i<len;  i++) {  
           int ndx = (int)(Math.random()*ALPHA_NUM.length());  
           sb.append(ALPHA_NUM.charAt(ndx));  
        }  
        return sb.toString();  
    }  
}
