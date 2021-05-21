package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.SystemConfigManager;
import al.ozone.bl.manager.impl.ApplicationConfig;
import al.ozone.bl.model.SystemConfigBean;

/**
 * @author Ermal Aliraj
 * 
 * Session Managed Bean.
 */
@ManagedBean(name="systemConfigController")
@RequestScoped
public class SystemConfigController implements Serializable{
	
	private static final long serialVersionUID = 5866673902858855703L;
	protected static final transient Log logger = LogFactory.getLog(SystemConfigController.class);
	
	private List<SystemConfigBean> entryList;
	private HtmlSelectOneMenu fLanguage; 
	private HtmlInputText fPublicationDuration;
	private HtmlInputText fBonusValue;
	private HtmlInputText fDealDuration;
	private HtmlInputTextarea fInviteMessage;
	
	private String language; 
	private String theme;
	private String publicationDuration;
	private String bonusValue;
	private String dealDuration;
	private String inviteMessage;
	
	
	private Map<String, String> themes;
	private Locale locale;
	
	// Injected properties
	@ManagedProperty(value="#{systemConfigManager}") 
	private SystemConfigManager systemConfigManager;	
	
	@PostConstruct
	public void init(){		
		List<SystemConfigBean> list = systemConfigManager.getAll();
		setEntryList(list);
		
		String key = "";
		for (SystemConfigBean entry : list) {
			key = entry.getKey();
			theme = takeVal(entry, key, ApplicationConfig.THEME, theme);
			language = takeVal(entry, key, ApplicationConfig.LANGUAGE, language);
			publicationDuration = takeVal(entry, key, ApplicationConfig.PUBLICATION_DURATION, publicationDuration);
			bonusValue = takeVal(entry, key, ApplicationConfig.BONUS_VALUE, bonusValue);
			dealDuration = takeVal(entry, key, ApplicationConfig.DEAL_DURATION, dealDuration);
			inviteMessage = takeVal(entry, key, ApplicationConfig.INVITE_MESSAGE, inviteMessage);
		}
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		//System.out.println("locale got from context:"+locale);
		
		locale = new Locale(language);	
		//logger.debug("Default values:publicationDuration="+publicationDuration+", theme="+theme+", language="+language+", locale="+locale+", bonusValue="+bonusValue);

        themes = new TreeMap<String, String>();  
        themes.put("Aristo", "aristo");  
        themes.put("Cupertino", "cupertino");  
	}

	/**
	 * Returns the value of the SystemConfigBean only if key is equals to keyDB
	 * 
	 * @param entry entry (key, value) of SystemConfig section
	 * @param key key which we want to compare
	 * @param keyDB key in DB table
	 * @return value of DB with the specific key
	 */
	private String takeVal(SystemConfigBean entry, String key, String keyDB, String origVal) {
		String ret = "";
		if(key.equals(keyDB)){
			ret = entry.getValue();
		}else{
			ret = origVal;
		}
		return ret;
	}

	public void save(){		
		List<SystemConfigBean> list = new ArrayList<SystemConfigBean>();
		//list.add(new ApplicationConfig("LANGUAGE", JSFUtils.getStringFromUIInput(fLanguage)));
		//list.add(new ApplicationConfig("THEME", JSFUtils.getStringFromUIInput(fTheme)));
		list.add(new SystemConfigBean("PUBLICATION_DURATION", JSFUtils.getStringFromUIInput(fPublicationDuration)));
		list.add(new SystemConfigBean("DEAL_DURATION", JSFUtils.getStringFromUIInput(fDealDuration)));
		list.add(new SystemConfigBean("BONUS_VALUE", JSFUtils.getStringFromUIInput(fBonusValue)));
		list.add(new SystemConfigBean("INVITE_MESSAGE", JSFUtils.getStringFromUIInput(fInviteMessage)));
		
		try {
			systemConfigManager.save(list);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "Success -", "Variables saved successfully in the system.");
		} catch (Exception e) {
			logger.error("Can not save the list with the parameters", e);
			JSFUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Update Error", e.getCause().toString());  
		}
	}
	
    public void onThemeChange() {  
        System.out.println("entering on the listener: "+theme);  
    }  
	
	public List<SystemConfigBean> getEntryList() {
		return entryList;
	}
	public void setEntryList(List<SystemConfigBean> entryList) {
		this.entryList = entryList;
	}
	public SystemConfigManager getSystemConfigManager() {
		return systemConfigManager;
	}
	public void setSystemConfigManager(SystemConfigManager systemConfigManager) {
		this.systemConfigManager = systemConfigManager;
	}
	public HtmlSelectOneMenu getfLanguage() {
		return fLanguage;
	}
	public void setfLanguage(HtmlSelectOneMenu fLanguage) {
		this.fLanguage = fLanguage;
	}
	public HtmlInputText getfPublicationDuration() {
		return fPublicationDuration;
	}
	public void setfPublicationDuration(HtmlInputText fPublicationDuration) {
		this.fPublicationDuration = fPublicationDuration;
	}
	
	public String getLanguage() {
		String ln = locale.getLanguage();
		//System.out.println("***getLanguage:"+ln);
		return ln;
	}
	public void setLanguage(String language) {
		locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
       // System.out.println("***setLanguage locale:"+locale);
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		UserPreferences userPreferences = JSFUtils.findBean("userPreferences");
		userPreferences.setTheme(theme);
		this.theme = theme;
	}
	public String getPublicationDuration() {
		return publicationDuration;
	}
	public void setPublicationDuration(String publicationDuration) {
		this.publicationDuration = publicationDuration;
	}
	public Map<String, String> getThemes() {
		return themes;
	}
	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}
	public String getBonusValue() {
		return bonusValue;
	}
	public void setBonusValue(String bonusValue) {
		this.bonusValue = bonusValue;
	}
	public HtmlInputText getfBonusValue() {
		return fBonusValue;
	}
	public void setfBonusValue(HtmlInputText fBonusValue) {
		this.fBonusValue = fBonusValue;
	}
	public HtmlInputText getfDealDuration() {
		return fDealDuration;
	}
	public void setfDealDuration(HtmlInputText fDealDuration) {
		this.fDealDuration = fDealDuration;
	}
	public String getDealDuration() {
		return dealDuration;
	}
	public void setDealDuration(String dealDuration) {
		this.dealDuration = dealDuration;
	}
	public HtmlInputTextarea getfInviteMessage() {
		return fInviteMessage;
	}
	public void setfInviteMessage(HtmlInputTextarea fInviteMessage) {
		this.fInviteMessage = fInviteMessage;
	}
	public String getInviteMessage() {
		return inviteMessage;
	}
	public void setInviteMessage(String inviteMessage) {
		this.inviteMessage = inviteMessage;
	}
	
}
