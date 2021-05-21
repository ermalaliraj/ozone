package al.ozone.engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import al.ozone.bl.manager.CreditManager;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Email;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailRobot;

public class CreateNewUsersFromFile{

    protected static final transient Log logger = LogFactory.getLog(CreateNewUsersFromFile.class);

    
    public static void main(String[] args) {
    	ClassPathXmlApplicationContext applicationContext;
    	applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath*:**//applicationContext-*.xml" });
    	
    	CustomerManager customerManager = (CustomerManager) applicationContext.getBean("customerManager");
    	CreditManager creditManager = (CreditManager) applicationContext.getBean("creditManager");
    	EmailRobot emailRobot = (EmailRobot) applicationContext.getBean("emailRobot");
    	
    	try {
    		List<String> duplicated = new ArrayList<String>();
    		int insertsOK = 0;
    		File file = new File("emails_durres_08082012.xls");
    		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
    		HSSFSheet sheet = workbook.getSheetAt(0);
    		System.out.println("workbook.getNumberOfSheets():"+workbook.getNumberOfSheets());
    		if (workbook.getNumberOfSheets() > 0) {
				for (Row row : sheet) {
//					for(int i=0; i<row.getRowNum(); i++){
//						Cell cell = row.getCell(i);
//						logger.debug("Row " + (row.getRowNum() + 1) + " column " + (cell.getColumnIndex() + 1) + " value " + cell.toString());
//						
//					}
					Cell email = row.getCell(1);
					Cell name = row.getCell(2);
					Cell city = row.getCell(3);
					//logger.debug("email:" + email + " name: " + name + " city: " + ZUtils.getCityId(city+""));
					
					//only if cell is not null and contains @
					if(email != null && (email+"").contains("@")){
						Customer c = new Customer();
						c.setEmail(email+"");
						c.setName(name+"");
						c.setPassword(name+"");
						c.setCityId(ZUtils.getCityId(city+""));
						c.setActive(true);
						try{
							customerManager.insert(c);
							logger.debug("Inserted email "+c.getEmail());
							insertsOK ++;
							
							Credit credit = new Credit();
							credit.setCustomer(c);
							credit.setAssignedDate(new Date());
							credit.setValidDate(ZUtils.addMonthsToDate(new Date(), 1));
							credit.setAbout("Kredite per fushaten Durres-Gusht2012");
							credit.setType(Credit.TYPE_BENEFIT);
							credit.setValue(300);							
							creditManager.insert(credit);	
							
							Email e = new Email("NewCustomer");
							e.setSubject("Mireseerdhet ne ozone.al");
							e.addParameter("creditExpire", ZUtils.getDateAsStringAsDDMMYYYY(credit.getValidDate()));
							e.addParameter("email", c.getEmail());
							e.addParameter("name", c.getName());
					        e.addTo(c.getEmail());
					        emailRobot.sendEmail(e);
					        
						} catch(DuplicateKeyException e){
							duplicated.add(email+"");
						}						
					}				
				}
				logger.info("Inserted "+insertsOK+". Duplicated "+duplicated.size());
				logger.info("Duplicated List:\n"+duplicated);
    		}else{
    			logger.error("No sheet present in the file");
    		}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:", e);
		} catch (IOException e) {
			logger.error("IOException:", e);
		}
    }


	private static void insertCredit(Customer c, CreditManager creditManager) {
		Credit credit = new Credit();
		credit.setCustomer(c);
		credit.setAssignedDate(new Date());
		credit.setValidDate(ZUtils.addMonthsToDate(new Date(), 1));
		credit.setAbout("Kredite per fushaten Durres-Gusht2012");
		credit.setType(Credit.TYPE_BENEFIT);
		credit.setValue(300);
		
		creditManager.insert(credit);		
	}   
    
}
