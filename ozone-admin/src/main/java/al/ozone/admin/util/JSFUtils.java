package al.ozone.admin.util;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import al.ozone.admin.backing.SystemConfigController;
import al.ozone.bl.model.Category;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Partner;
import al.ozone.bl.utils.ZUtils;

public class JSFUtils {
	
	protected static final transient Log logger = LogFactory.getLog(JSFUtils.class);
	
	public static String getSystemLanguage() {
		SystemConfigController sysConfig = JSFUtils.findBean("systemConfigController");
		return sysConfig.getLanguage();
	}

	public static Partner updateCategoryNameForPartner() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Update Category Name fields on partner list depending on the language
	 */
	
	/**
	 * Update Category Name to the partner passed as parameter
	 * @param p partner to update the category name
	 * @return partner updated
	 */ //TODO consider refactoring on partnerController.updateCategoryNameFields
	public static Partner updateCategoryNameForPartner(Partner p){
		String language = JSFUtils.getSystemLanguage();
		if(language.equals("al")){
	    	Category c = p.getCategory();
	    	c.setName(c.getNameAl());
	    	p.setCategory(c);
		}
		
		if(language.equals("en")){
			Category c = p.getCategory();
			c.setName(c.getNameEn());
			p.setCategory(c);
		}	
		return p;
	}
	
	// Until here ONLy admin classes

	@SuppressWarnings("unchecked")
	public static <T> T findBean(String managedBeanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return null;
//	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + managedBeanName + "}", Object.class);
	}
	
	public static void putObjectInSession(String key, Object value){
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(key, value);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromSession(String key){
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getExternalContext().getSessionMap().get(key);
	}
	public static Object removeObjectFromSession(String key){
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getSessionMap().remove(key);
	}
	
	public static String getParameter(String key) {
    	ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    	String value = context.getRequestParameterMap().get(key);
    	return value;
	}
	
	public static String getStringFromUIInput(UIInput field) {
		if(field!=null && field.getValue()!=null){
			return field.getValue().toString();
		}else{
			return "";
		}		
	}

	public static Double getDoubleFromUIInput(UIInput field) {
		if(field!=null && field.getValue()!=null && !field.getValue().toString().equals("")){
			return Double.valueOf(field.getValue().toString());
		}else{
			return 0.0;
		}		
	}
	
	public static Integer getIntegerFromUIInput(UIInput field) {
		Integer retVal = null;
		
		if(field!=null && field.getValue()!=null && !field.getValue().toString().equals("")){
			try {
				retVal = Integer.valueOf(field.getValue().toString());
			} catch (NumberFormatException e) {
				retVal = null;
			}
		}else{
			retVal = 0;
		}		
		return retVal;
	}
	
	public static Integer getIntegerFromUIOutput(UIOutput field) {
		if(field!=null && field.getValue()!=null && !field.getValue().toString().equals("")){
			return Integer.valueOf(field.getValue().toString());
		}else{
			return 0;
		}		
	}
	
	public static Boolean getBooleanFromUIInput(UIInput sPublished) {
		String val = getStringFromUIInput(sPublished);
		return ZUtils.getBooleanFromString(val);
	}
	
	public static boolean getPrimitiveBoolFromUIInput(UIInput field) {
		String val = getStringFromUIInput(field);
		return Boolean.parseBoolean(val);
	}
	
	public static void redirectToPage(String page){
		FacesContext facesContext = FacesContext.getCurrentInstance();  
		String path = facesContext.getExternalContext().getRequestContextPath();
		path = path+"/"+page;
		try {
			facesContext.getExternalContext().redirect(path);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Can not redirect to the page: "+page);
		}
	}
	
	public static String getIpFromRequest(){
		FacesContext facesContext = FacesContext.getCurrentInstance();  
		HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		String ip = httpServletRequest.getRemoteAddr(); 
		return ip;
	}
	
	
	/**
	 * The ?faces-redirect=true is not necessary, but it effectively sends a redirect 
	 * so that the URL in browser address bar will properly change which is better for 
	 * user experience and bookmarkability of the pages.
	 * 
		<h:form>
		    <h:selectOneMenu value="#{navigator.outcome}">
		        <f:selectItem itemLabel="Select page..." />
		        <f:selectItem itemValue="page1" itemLabel="Page 1" />
		        <f:selectItem itemValue="page2" itemLabel="Page 2" />
		        <f:selectItem itemValue="page3" itemLabel="Page 3" />
		        <f:ajax listener="#{navigator.navigate}" />
		    </h:selectOneMenu>
		</h:form>
	 * @param pageName, pageName to navigate to.
	 */
	public static void navigate(String pageName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
	    navigationHandler.handleNavigation(context, null, pageName + "?faces-redirect=true");
	}
	
	public static String getCreditType_Label(String value){
	    FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
	    String retVal = "default";
	    if(value.equals(Credit.TYPE_BENEFIT)){
			retVal = bundle.getString("credit.type.benefit");
		}else if(value.equals(Credit.TYPE_REIMBORSEMENT)){
			retVal = bundle.getString("credit.type.reimborsement");
		}
		return retVal;
	}
	
	public static String getCreditType_Value(String label){
	    FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
	    String retVal = "default";
		if(label.equals(bundle.getString("credit.type.benefit"))){
			retVal = Credit.TYPE_BENEFIT;
		}else if(label.equals(bundle.getString("credit.type.reimborsement"))){
			retVal = Credit.TYPE_REIMBORSEMENT;
		}
		return retVal;
	}

	public static String getMessageFromBundle(String key){
	    FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
	    String retVal = bundle.getString(key);
		return retVal;
	}
	
	public static void addInfoMessage(String msg) {
		addFacesMessage(FacesMessage.SEVERITY_INFO, "Success", msg);
	}
	
	public static void addWarnMessage(String msg) {
		addFacesMessage(FacesMessage.SEVERITY_WARN, "Warning", msg);
	}
	
	public static void addErrorMessage(String msg) {
		addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg);
	}
	
	public static void addFacesMessage(Severity severity, String summary, String detail) {
		FacesMessage message = new FacesMessage(severity, summary, detail);   
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static UIInput setValueIfNotNull(UIInput field, String val) {
		if(field!=null){
			field.setValue(val);
		}
		return field;
	}
	
	public static String getAppContextUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String contextURL = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "") + request.getContextPath();
		return contextURL;
	}

	public static void createCookie(String cName, String cValue){
		int minutes = 60;
		int hours = 60 * minutes;
		
		// create cookies
		HttpServletResponse httpServletResponse = 
			(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		Cookie cookie = new Cookie(cName, cValue);   
		cookie.setMaxAge(12*hours);
		cookie.setComment("A Comment for who invited a friend");
		httpServletResponse.addCookie(cookie);  
		logger.debug("Created cookie ["+cookie.getName()+", "+cValue+"]");
	  
		// get cookies
		HttpServletRequest httpServletRequest = 
			(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for(int i=0; i<cookies.length; i++){
				if (cookies[i].getName().equalsIgnoreCase(cName)){
					String cookieValue = cookies[i].getValue(); 
				}
			}
		}
	}

	public static String getCookieByName(String cookieName) {
		String rv = "";
		boolean found = false;
		
		// get cookies
		HttpServletRequest httpServletRequest = 
			(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for(int i=0; i<cookies.length && !found; i++){
				if (cookies[i].getName().equalsIgnoreCase(cookieName)){
					rv = cookies[i].getValue(); 
					found = true;
				}
			}
		}
		return rv;
	}



}
