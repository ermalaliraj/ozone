package al.ozone.engine.batch.jobs;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.bl.dao.EmailsIntroDAO;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Newsletter;
import al.ozone.bl.utils.EnvConfig;
import al.ozone.bl.utils.UrlEncrypter;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailRobot;

/**
 * For each Active Publication send the newsletter to all customers subscribed 
 * to the publication's city.
 * 
 * @author Ermal Aliraj
 */
public class SendNewsletterJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(SendNewsletterJob.class);
	
	//private PublicationManager publicationManager;
	private DealManager dealManager;
	private CustomerManager customerManager;
	private EmailRobot emailRobot;
	private EmailsIntroDAO emailsIntroDAO;
	private EnvConfig envConfig;
	private UrlEncrypter urlEncrypter;
	
	public void execute() throws Exception {
		
		List<Newsletter> newsletters = dealManager.getDealsStartTodayGroupedByCity();
		List<Deal> alreadyActive = dealManager.getDealsAlreadyActive();
		//ZUtils.printListOnLogger(newsletters, logger, "debug");
		
		if(newsletters.size() != 0){
			logger.info("There are "+newsletters.size()+" Cities with new deals, and "+alreadyActive.size()+" already active deals.");
			
			loggDealsToEmail(newsletters, alreadyActive);
			
			//List<String> cusListForCity = emailsIntroDAO.getAll();
			List<String> cusListForCity = emailsIntroDAO.getAdmins();
			logger.info("Sending emails to all NewsLetter list. tot:"+cusListForCity.size());
			
			String citiesString = getCitiesString(newsletters);
			String titleString = "";
			try {
				titleString = getTitleString(newsletters, alreadyActive);
			} catch(Exception e) {
				;
			}
			
			int rowNr = alreadyActive.size() / 2;
			int mod = alreadyActive.size() % 2;
			rowNr += mod;
			
			Email email;		
			for (String cusEmail : cusListForCity) {
				email = new Email("NewsletterMultiCities");
				titleString = titleString.trim();
				if(ZUtils.isEmptyString(titleString)){
					titleString = "OZone - Ofertat e reja";
				}
				email.setSubject(titleString);
				email.addTo(cusEmail);
				email.addParameter("newsletters", newsletters);
				email.addParameter("citiesString", citiesString);
				email.addParameter("alreadyActive", alreadyActive);
				email.addParameter("rowNr", rowNr-1);
				email.addParameter("mod", mod);
				email.addParameter("date", ZUtils.getDateAsStringAsDDMMYYYY(new Date()));
				email.addParameter("cusEmail", urlEncrypter.encrypt(cusEmail));
				
				// TODO get upload folder path from a configuration file
				email.addParameter("fileUpload", envConfig.getUploadsUrl());
				emailRobot.addEmail(email);
			}	
		} else {
			logger.info("No NEW deals to send newsletter");
		}
		
		// TODO parametrize city
//		String cityId = "TR";
//		List<Publication> publications = publicationManager.getAllPublicationForCity(cityId, true);
//		logger.info("There are " + publications.size() + " new publications for city " + cityId);
		
//		//List<String> cusListForCity = customerManager.getCitySubscribers(cityId);
//		List<String> cusEmails = customerManager.getAllEmails();
//		logger.info("Sending emails to all customers. tot:"+cusEmails.size());
//	
//		for (String cusEmail : cusEmails) {
//			email = new Email("Newsletter");
//			email.setSubject("OZone - Ofertat e reja");
//			email.addTo(cusEmail);
//			email.addParameter("publications", publications);
//			email.addParameter("date", ZUtils.getDateAsStringAsDDMMYYYY(new Date()));
//			
//			// TODO get upload folder path from a configuration file
//			email.addParameter("fileUpload", "http://www.ozone.al/uploads/");
//			emailRobot.addEmail(email);
//		}

	}

	private void loggDealsToEmail(List<Newsletter> newsletters, List<Deal> alreadyActive) {
		
		logger.debug("**New Deals**");
		for (Newsletter news : newsletters) {
			List<Deal> list = news.getActiveDeals();
			int i = 1;
			for (Deal d : list) {
				logger.debug(i+". Id:"+d.getId()+" Title:"+d.getTitle());
				i++;
			}
		}
		
		logger.debug("**Deals Already Active**");
		int i = 1;
		for (Deal d : alreadyActive) {
			logger.debug(i+". Id:"+d.getId()+" Title:"+d.getTitle());
			i++;
		}
	}

	private String getTitleString(List<Newsletter> newsletters, List<Deal> alreadyActive) {
		List<Deal> activeDeals;
		StringBuilder sBuilder = new StringBuilder();
		boolean exite = false;
		for (Newsletter news : newsletters){
			activeDeals = news.getActiveDeals();
			for (Deal deal : activeDeals) {
				if(!exite){
					if( (sBuilder.length() + deal.getTitleNewsletter().length()) < 190) {
						sBuilder.append(deal.getTitleNewsletter());
						sBuilder.append(" / ");
					} else {
						sBuilder.append("etj...");
						sBuilder.append(" / ");
						exite = true;
					}
				}
			}
		}
		
		// put already actives in title too
		for (Deal deal : alreadyActive) {
			if(!exite){
				if( ((sBuilder.length() + deal.getTitleNewsletter().length()) < 190)){
					sBuilder.append(deal.getTitleNewsletter());
					sBuilder.append(" / ");
				} else {
					sBuilder.append("etj...");
					sBuilder.append(" / ");
					exite = true;
				}
			}
		}
		
		String titleString = sBuilder.toString();
		titleString = titleString.substring(0, titleString.length()-2);
		
		if(titleString.length() > 190 ){
			titleString.substring(0,  185);
		}
		
		return titleString;
	}

	private String getCitiesString(List<Newsletter> newsletters) {
		//for to concatenate the cities + logging for number pubs for each city
		StringBuilder sBuilder = new StringBuilder();
		int nDeals = 0, i = 0;
		for (Newsletter news : newsletters){
			i++;
			if (i > 1){
				sBuilder.append(", ");
			}
			sBuilder.append(news.getCityName());
			nDeals += news.getActiveDeals().size();
		}
		String citiesString = sBuilder.toString();
		logger.info("There are "+nDeals+" total new Deals for "+citiesString);
		return citiesString;
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	public CustomerManager getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	public EmailRobot getEmailRobot() {
		return emailRobot;
	}
	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}
	public EmailsIntroDAO getEmailsIntroDAO() {
		return emailsIntroDAO;
	}
	public void setEmailsIntroDAO(EmailsIntroDAO emailsIntroDAO) {
		this.emailsIntroDAO = emailsIntroDAO;
	}
	public void setEnvConfig(EnvConfig envConfig) {
		this.envConfig = envConfig;
	}
	public void setUrlEncrypter(UrlEncrypter urlEncrypter) {
		this.urlEncrypter = urlEncrypter;
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
		        "applicationContext-email.xml",
				"applicationContext-BatchEngine.xml",
				"applicationContext-manager.xml",
				"applicationContext-dao.xml",
				"applicationContext-resources.xml",
				"applicationContext-service.xml"
	        });

		SendNewsletterJob job = (SendNewsletterJob) context.getBean("sendNewsletterJob");
		try {
            job.execute();
            logger.info("*Job Executed: " + job.getJobName());
        } catch (Exception e) {
            logger.error("Job Error: " + job.getJobName(), e);
        }
	}
}
