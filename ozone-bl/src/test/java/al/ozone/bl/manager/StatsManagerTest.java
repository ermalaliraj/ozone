package al.ozone.bl.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.ContratPublicationBean;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentPayPal;
import al.ozone.bl.unitTest.AbstractSpringTestCase;

public class StatsManagerTest extends AbstractSpringTestCase {
	protected static final transient Log logger = LogFactory.getLog(StatsManagerTest.class);

	protected DealManager dealManager;

	protected void setUp() throws Exception {
		super.setUp();
		dealManager = (DealManager) applicationContext.getBean("dealManager");
	}
	
	public void testPurchaseCRUD() throws Exception{	
		List<ContratPublicationBean> list = dealManager.getContratsGroupByMonths(null);
		for (ContratPublicationBean contratPublicationBean : list) {
			System.out.println(contratPublicationBean);
		}
    }
	
	
    private void createPdf(String fileName, byte[] pdfData) {
        File f = new File(fileName);
        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(f);

            if (f.exists()) {
                stream.write(pdfData);
                stream.flush();
                logger.info("PDF created");
            } else {
                RuntimeException re = new RuntimeException("The file " + fileName + " can not be created");
                logger.error("The file " + fileName + " can not be created", re);
                //throw re;
            }
        } catch (FileNotFoundException e) {            
            logger.error("Can not create a stream with the file " + f.getName(), e);
            //throw e;
        } catch (IOException e) {
            logger.error("I/O Exception ", e);
            //throw e;
        }
        finally{
            if(stream!=null){
               try{
                   stream.close();
               }catch(IOException e){;}
            }
        }
    }
    
	protected void tearDown(){

	}
	
}
