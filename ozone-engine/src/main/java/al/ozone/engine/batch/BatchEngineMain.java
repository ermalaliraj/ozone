package al.ozone.engine.batch;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.batch.BatchEngine;

public class BatchEngineMain{

	protected static final transient Log logger = LogFactory.getLog(BatchEngineMain.class);
	private ClassPathXmlApplicationContext applicationContext;
	
	protected String[] getApplicationContextPath() {
        return new String[] {
	        "applicationContext-email.xml",
			"applicationContext-BatchEngine.xml",
			"applicationContext-manager.xml",
			"applicationContext-dao.xml",
			"applicationContext-resources.xml",
			"applicationContext-service.xml"
        };
    }
	
	/**
	 * Creates and Runs <code>nameEngine<code> Engine .
	 * @param nameEngine name of BatchEngine to run
	 */
	public BatchEngineMain(String nameEngine){
		setup();
		BatchEngine batchEngine = null;
		
		if(!ZUtils.isEmptyString(nameEngine) && nameEngine.equals("newsletterEngine")){
			batchEngine = (BatchEngine) applicationContext.getBean("newsletterEngine");
		}else{
			batchEngine = (BatchEngine) applicationContext.getBean("batchEngine");
		}
		
		batchEngine.start();
	}
	
	private void setup() {
		applicationContext = new ClassPathXmlApplicationContext(getApplicationContextPath());
	}
	
	/**
	 * Runs newsletterEngine if specified as argument when calling the class example: <br>
	 * java -cp OZoneEngine.jar al.ozone.engine.batch.BatchEngineMain -newsletterEngine <br>
	 * or default BatchEngine (the one running at midnight) if no arguments were specified
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> mapArgs = ZUtils.parseArgs(args);
		
		String nameEngine;
		boolean isNewsletter = mapArgs.containsKey("newsletterEngine");
		if(isNewsletter){
			nameEngine = "newsletterEngine";
		}else{
			nameEngine = "batchEngine";
		}
			
		logger.info("Running Engine "+nameEngine);
		new BatchEngineMain(nameEngine);
	}
}
