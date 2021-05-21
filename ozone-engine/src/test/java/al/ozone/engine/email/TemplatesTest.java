package al.ozone.engine.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.model.Publication;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.utils.EnvConfig;
import al.ozone.bl.utils.ZUtils;

/**
 * Test template with VelocityEngine
 * 
 * @author Ermal
 *
 */
public class TemplatesTest extends AbstractSpringTestCase {
	
	protected static final transient Log logger = LogFactory.getLog(TemplatesTest.class);
	private static String templateBase = "mailTemplates/";
	
	private  VelocityEngine velocityEngine;
	private DealManager dealManager;
	private EnvConfig envConfig;

	protected void setUp() throws Exception {
		super.setUp();
		velocityEngine = (VelocityEngine) applicationContext.getBean("velocityEngine");
		dealManager = (DealManager) applicationContext.getBean("dealManager");
		envConfig = (EnvConfig) applicationContext.getBean("envConfig");
	}

//	/**
//	 * Output text produced is a html code which can be used from 
//	 * MimeMessagePreparator when sending email
//	 */
//	public void testTemplate(){
//		String templateToTest = templateBase + "NewsletterMultiCities.vm";
//		
//		List<Publication> alreadyActive = publicationManager.getDealsAlreadyActive();
//		List<Newsletter> newsletters = publicationManager.getDealsStartTodayGroupedByCity();
//		
//		//logger.info("There are "+newsletters.size()+" Cities with new Deals, and "+alreadyActive.size()+" already active pub.");
//		
//		//for to concatenate the cities + logging for number pubs for each city
//		StringBuilder sBuilder = new StringBuilder();
//		int nPubs = 0, i = 0;
//		for (Newsletter news : newsletters){
//			i++;
//			if (i > 1){
//				sBuilder.append(", ");
//			}
//			sBuilder.append(news.getCityName());
//			nPubs += news.getActiveDeals().size();
//		}
//		//logger.info("There are "+nPubs+" total new Deals for "+sBuilder);
//		
//		int rowNr = alreadyActive.size() / 2;
//		int mod = alreadyActive.size() % 2;
//		rowNr += mod;
//
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("newsletters", newsletters);
//		parameters.put("citiesString", sBuilder.toString());
//		parameters.put("alreadyActive", alreadyActive);
//		parameters.put("rowNr", rowNr-1);
//		parameters.put("mod", mod);
//		parameters.put("date", ZUtils.getDateAsStringAsDDMMYYYY(new Date()));
//		parameters.put("fileUpload", envConfig.getUploadsUrl());
//
//		String text = VelocityEngineUtils.mergeTemplateIntoString(
//				velocityEngine, templateToTest, parameters);
//		//logger.debug(text);
//		System.out.println(text);
//	}
	
	
	public void testManualNewsletter(){
		String templateToTest = templateBase + "NewsletterMultiCities.vm";
		List<Deal> alreadyActive = new ArrayList<Deal>();
		
		List<Deal> activeDeals = new ArrayList<Deal>();
		
		Deal deal1 = dealManager.get(42); 
		Deal deal2 = dealManager.get(30);
		Deal deal3 = dealManager.get(37);
		Deal deal4 = dealManager.get(32);
		Deal deal5 = dealManager.get(31);
		Deal deal6 = dealManager.get(55);
		
		activeDeals.add(deal1);
		activeDeals.add(deal2);
		activeDeals.add(deal3);
		activeDeals.add(deal4);
		activeDeals.add(deal5);
		activeDeals.add(deal6);
		
		Newsletter nl = new Newsletter();
		nl.setCityId("TR");
		nl.setCityName("Tirane");
		nl.setActiveDeals(activeDeals);
		
		List<Newsletter> newsletters = new ArrayList<Newsletter>();
		newsletters.add(nl);
		
		StringBuilder sBuilder = new StringBuilder();
		int nPubs = 0, i = 0;
		for (Newsletter news : newsletters){
			i++;
			if (i > 1){
				sBuilder.append(", ");
			}
			sBuilder.append(news.getCityName());
			nPubs += news.getActiveDeals().size();
		}

		int rowNr = alreadyActive.size() / 2;
		int mod = alreadyActive.size() % 2;
		rowNr += mod;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("newsletters", newsletters);
		parameters.put("citiesString", sBuilder.toString());
		parameters.put("alreadyActive", alreadyActive);
		parameters.put("rowNr", rowNr-1);
		parameters.put("mod", mod);
		parameters.put("date", ZUtils.getDateAsStringAsDDMMYYYY(new Date()));
		parameters.put("fileUpload", envConfig.getUploadsUrl());

		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, templateToTest, parameters);
		//logger.debug(text);
		System.out.println(text);
	}
}
