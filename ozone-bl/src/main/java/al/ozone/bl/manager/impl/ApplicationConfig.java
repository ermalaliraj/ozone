package al.ozone.bl.manager.impl;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.event.ConfigurationErrorEvent;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationConfig implements ConfigurationErrorListener, Serializable {

	private static final long serialVersionUID = -6508987431585805939L;

	protected static transient Log logger = LogFactory.getLog(ApplicationConfig.class);
	
	public final static String MAX_BAD_LOGINS = "MAX_BAD_LOGINS";
	public final static String PUBLICATION_DURATION = "PUBLICATION_DURATION";
	public final static String DEAL_DURATION = "DEAL_DURATION";
    public final static String THEME = "THEME";
    public final static String LANGUAGE = "LANGUAGE";
    public final static String BONUS_VALUE = "BONUS_VALUE";
    public final static String DEFAULT_CITY = "DEFAULT_CITY";
    public final static String CREDIT_DURATION = "CREDIT_DURATION";
    public final static String INVITE_MESSAGE = "INVITE_MESSAGE";
    public final static String EXCHANGE_RATE = "EXCHANGE_RATE";
    
    private transient DatabaseConfiguration configuration;

    public ApplicationConfig(DataSource datasource, String table, String keyColumn, String valueColumn) {
        configuration = new DatabaseConfiguration(datasource, table, keyColumn, valueColumn);
        configuration.addErrorListener(this);
        configuration.setDelimiterParsingDisabled(true);
    }

    public void configurationError(ConfigurationErrorEvent event) {
        logger.error("Error updating configuration property " + event.getPropertyName(), event.getCause());
    }

    public void setMaxBadLogins(int value) {
        configuration.setProperty(MAX_BAD_LOGINS, value);
    }
    
    //second parameter is default value, if the key is not present in database
    public int getMaxBadLogins() {
        return configuration.getInt(MAX_BAD_LOGINS, 3);
    }

    public void setPublicationDuration(int value) {
        configuration.setProperty(PUBLICATION_DURATION, value);
    }
    
    public int getPublicationDuration(){
        return configuration.getInt(PUBLICATION_DURATION, 3);
    }
    
    public void setDealDuration(int value) {
        configuration.setProperty(DEAL_DURATION, value);
    }
    
    public int getDealDuration(){
        return configuration.getInt(DEAL_DURATION, 3);
    }
    
    public void setTheme(String value) {
        configuration.setProperty(THEME, value);
    }
    
    public String getTheme(){
        return configuration.getString(THEME, "aristo");
    }
    
    public void setLanguage(String value) {
        configuration.setProperty(LANGUAGE, value);
    }
    
    public String getLanguage(){
        return configuration.getString(LANGUAGE, "al");
    }
    
    public void setBonusValue(int value) {
        configuration.setProperty(BONUS_VALUE, value);
    } 
    
	public int getBonusValue() {
		return configuration.getInt(BONUS_VALUE, 300);
	}

	public String getDefaultCity() {
		return configuration.getString(DEFAULT_CITY, "TR");
	}
	
	public void setDefaultCity(String value) {
        configuration.setProperty(DEFAULT_CITY, value);
    }
  
    public void setCreditDuration(int value) {
        configuration.setProperty(CREDIT_DURATION, value);
    } 
	
	public int getCreditDuration() {
		return configuration.getInt(CREDIT_DURATION, 6);
	}
	
	public String getInviteMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("Pershendetje,");
		sb.append("\n");
		sb.append("Doja te te sugjeroja kete akord.");
		sb.append("\n");
		sb.append("Te fala,");
		sb.append("xxxx");
		return configuration.getString(INVITE_MESSAGE, sb.toString());
	}
	
	public void setInviteMessage(String value) {
        configuration.setProperty(INVITE_MESSAGE, value);
    }

	public void setExchangeRate(double rate) {
		configuration.setProperty(EXCHANGE_RATE, rate);
	} 
	
	public double getExchangeRate(){
		return configuration.getDouble(EXCHANGE_RATE, 0.007);
	}
	
}
