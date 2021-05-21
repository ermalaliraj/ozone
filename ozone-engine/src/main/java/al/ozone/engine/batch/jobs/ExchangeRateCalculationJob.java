package al.ozone.engine.batch.jobs;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import NET.webserviceX.www.Currency;
import NET.webserviceX.www.CurrencyConvertor;
import NET.webserviceX.www.CurrencyConvertorSoap;
import al.ozone.bl.manager.UserManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Role;
import al.ozone.bl.utils.EnvConfig;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailRobot;

/** 

 * @author Ermal Aliraj
 */
public class ExchangeRateCalculationJob implements BatchJobInterface {

	protected static final transient Log logger = LogFactory.getLog(ExchangeRateCalculationJob.class);
	private CurrencyConvertor currencyConvertor;
	private EnvConfig envConfig;
	private UserManager userManager;
	private EmailRobot emailRobot;
	private ApplicationConfig applicationConfig;
	
	public void execute() throws Exception {
		double rate;
		
		// get the rate to convert LEK in EURO
		try {
			CurrencyConvertorSoap serv = currencyConvertor.getCurrencyConvertorSoap();
			double rateFromService = serv.conversionRate(Currency.ALL, Currency.EUR);
			rate = rateFromService + envConfig.getDeltaExchangeCurr();
			logger.debug("LEK will be calculated in EUR with rate:"+rate+". Rate with no DELTA from WebService:"+rateFromService);
			applicationConfig.setExchangeRate(rate);
			logger.info("Updated rate currency in DB to "+rate);
		} catch (RemoteException e1) {
			logger.error("Cannot convert in EURO. e1.", e1);
			sendEmailToAdmins(e1);
		} catch (ServiceException e2) {
			logger.error("Cannot convert in EURO. e2", e2);
			sendEmailToAdmins(e2);
		} 	
		
	}

	private void sendEmailToAdmins(Exception e) {
		Email email = new Email("StrangeSituation");
    	email.setSubject("OZone - LEK in EURO can not been converted!");
    	email.addParameter("where", "ExchangeRateCalculationJob.execute()");
        email.addParameter("msg", " - Error while calculating LEK-EUR rate. Error:\n"+e.toString());
        
		List<String> adminEmails = ZUtils.getEmailsFromUsers(userManager.getUsersByRoleId(Role.ROLE_ADMIN));
		if(!ZUtils.isEmptyList(adminEmails)){
			email.addTo(adminEmails);
    	}else{
    		email.addTo("ermal.aliraj@gmail.com");
    	}
		emailRobot.sendEmail(email);
	}

	public String getJobName() {
		return ClassUtils.getShortClassName(this.getClass());
	}

	public void setCurrencyConvertor(CurrencyConvertor currencyConvertor) {
		this.currencyConvertor = currencyConvertor;
	}

	public void setEnvConfig(EnvConfig envConfig) {
		this.envConfig = envConfig;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setEmailRobot(EmailRobot emailRobot) {
		this.emailRobot = emailRobot;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
}
