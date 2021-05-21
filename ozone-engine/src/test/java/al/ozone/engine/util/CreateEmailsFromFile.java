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

public class CreateEmailsFromFile{

    protected static final transient Log logger = LogFactory.getLog(CreateEmailsFromFile.class);

    
    public static void main(String[] args) {
    	ClassPathXmlApplicationContext applicationContext;
    	applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath*:**//applicationContext-*.xml" });
    	
    	CustomerManager customerManager = (CustomerManager) applicationContext.getBean("customerManager");
    	CreditManager creditManager = (CreditManager) applicationContext.getBean("creditManager");
    	EmailRobot emailRobot = (EmailRobot) applicationContext.getBean("emailRobot");
    	
    	try {
    		File file = new File("BKT_Eagle_Mobile_Phone_Numbers.xls");
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
					Cell name = row.getCell(2);
					Cell surname = row.getCell(3);
					//logger.debug("name:" + name + " surname: " + surname);
					String s = name.toString().substring(0, 1);
					String email = s+surname+"@bkt.com.al";
					//logger.debug("email:"+email.toLowerCase());
					System.out.println(email.toLowerCase());
				}
    		}else{
    			logger.error("No sheet present in the file");
    		}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:", e);
		} catch (IOException e) {
			logger.error("IOException:", e);
		}
    }

}
