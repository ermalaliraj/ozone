package al.ozone.bl.dao.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.CodeGeneratorDAO;

public class CodeGeneratorDAOImpl extends BaseDAO implements CodeGeneratorDAO, Serializable {

	private static final long serialVersionUID = -2949297668181442242L;
	protected static final transient Log logger = LogFactory.getLog(CodeGeneratorDAOImpl.class);

	private static final String NUM = "0123456789";
	private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  
	private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
//	private static final int COUPON_PREFIX_LEN = 3;
//	private static final int COUPON_SUFIX_LEN = 7;
	private static final int COUPON_CODE_LEN = 10;
	private static final int COUPON_SEC_CODE_LEN = 12;
	private static final int DCARD_CODE_LEN = 7;
	
	public String generateDiscountCardCode(){
		return generateNewAlphaNumeric("DISCOUNTCARD.countById", DCARD_CODE_LEN);
	}
	
	public String generateCouponCode(){
		//return getRandomNumber(COUPON_PREFIX_LEN)+getRandomAlphaNumeric(COUPON_SUFIX_LEN);
		return generateNewAlphaNumeric("COUPON.countById", COUPON_CODE_LEN);
	}
	
	public String generateNextSecurityCode(){
		return generateNewNumeric("COUPON.countBySecurityCode", COUPON_SEC_CODE_LEN);
	}
	
	/**
	 * Generate an UNIQUE alphanumeric code with the given length.
	 * Unique to be intend as unique for the given sql statement.
	 * 
	 * @param myBatisStatement sql statement to call in DB
	 * @param len length of generated code
	 * @return unique generated code
	 * @see #isUnique
	 */
	private String generateNewAlphaNumeric(String isUniqueStatement, int len) {
		String id = getRandomAlphaNumeric(len);  
		while (!isUnique(isUniqueStatement, id)) {  
        	id = getRandomAlphaNumeric(len);
        } 
		return id;
	}

	private String getRandomAlphaNumeric(int len) {  
        StringBuffer sb = new StringBuffer(len);  
        for (int i=0;  i<len;  i++) {  
           int ndx = (int)(Math.random()*ALPHA_NUM.length());  
           sb.append(ALPHA_NUM.charAt(ndx));  
        }  
        return sb.toString();  
    }  
	
	private String generateNewNumeric(String isUniqueStatement, int len) {
		String id = getRandomNumeric(len);  
		while (!isUnique(isUniqueStatement, id)) {  
        	id = getRandomNumeric(len);
        } 
		return id;
	}
	
	private String getRandomNumeric(int len) {  
        StringBuffer sb = new StringBuffer(len);  
        for (int i=0;  i<len;  i++) {  
           int ndx = (int)(Math.random()*NUM.length());  
           sb.append(NUM.charAt(ndx));  
        }  
        return sb.toString();  
    }
	
	/**
	 * Returns true if exists at least one record filtering for the given 
	 * <code>field</code> in the given statement <code>myBatisStatement</code>.
	 * Example COUPON.countBySecurityCode returns true, if the given securityCode as 
	 * parameter, is present in DB.
	 * 
	 * @param myBatisStatement sql statement to call in DB
	 * @param field filter field
	 * @return true if a record with the given ID is present in DB, false otherwise
	 */
    private boolean isUnique(String myBatisStatement, String field) {
    	Integer count = (Integer) getSqlSession().selectOne(myBatisStatement, field);
    	
    	if(count>0){
    		return false;
    	}else{
    		return true;
    	}
	}
    
	@SuppressWarnings("unused")
	private String getRandomAlpha(int len) {  
        StringBuffer sb = new StringBuffer(len);  
        for (int i=0;  i<len;  i++) {  
           int ndx = (int)(Math.random()*ALPHA.length());  
           sb.append(ALPHA.charAt(ndx));  
        }  
        return sb.toString();  
    } 
	
	@SuppressWarnings("unused")
	private String getRandomNumber(int len) {  
        StringBuffer sb = new StringBuffer(len);  
        for (int i=0;  i<len;  i++) {  
           int ndx = (int)(Math.random()*NUM.length());  
           sb.append(NUM.charAt(ndx));  
        }  
        return sb.toString();  
    }
	
}
