package al.ozone.admin.backing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchAuditTrailBean;
import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.model.AuditTrail;

/**
 * View managed backing bean of "auditTrailsList.jsp". It provides a list view of the audit trails
 * performed and a search (filter) functionality by user name and operation date range. 
 * 
 * @author Florjan Gogaj
 *
 */

@ManagedBean(name="auditTrailController")
@ViewScoped
public class AuditTrailController implements Serializable{

	private static final long serialVersionUID = 8791732704898739708L;

	protected static final transient Log logger = LogFactory.getLog(AuditTrailController.class);
	
	//Binding variables - Search form
	private HtmlInputText sUsername;
	private Date sFrom;
	private Date sTo;
	private String sToMinDate;
	private String sFromMaxDate;
	
	private List<AuditTrail> auditTrailList;

	@ManagedProperty(value="#{auditTrailManager}") 
	private AuditTrailManager auditTrailManager;
	
	/**
	 * Initializes the page. It calls first the search() method.
	 */
	@PostConstruct
	public void init(){			
		search();		
	}
	
	/**
	 * Resets the search form cleaning all fields values. 
	 */
	public void cleanSearchForm(){
		sUsername.setValue(null);
		sFrom=null;
		sTo=null;
	}
	
	/**
	 * This method performs a search in the database filtering by the field values inserted
	 * in the search form. A dedicated searching bean is used. 
	 */
	public void search(){		
		SearchAuditTrailBean sb = new SearchAuditTrailBean();
		sb.setUsername(JSFUtils.getStringFromUIInput(sUsername));
		sb.setFrom(sFrom);
		sb.setTo(sTo);
		auditTrailList=auditTrailManager.search(sb);
	}

	public HtmlInputText getsUsername() {
		return sUsername;
	}

	public void setsUsername(HtmlInputText sUsername) {
		this.sUsername = sUsername;
	}

	public AuditTrailManager getAuditTrailManager() {
		return auditTrailManager;
	}

	public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
		this.auditTrailManager = auditTrailManager;
	}

	public Date getsFrom() {
		return sFrom;
	}

	public void setsFrom(Date sFrom) {
		this.sFrom = sFrom;
	}

	public Date getsTo() {
		return sTo;
	}

	public void setsTo(Date sTo) {
		this.sTo = sTo;
	}

	public List<AuditTrail> getAuditTrailList() {
		return auditTrailList;
	}

	public void setAuditTrailList(List<AuditTrail> auditTrailList) {
		this.auditTrailList = auditTrailList;
	}

	public String getsToMinDate() {
		if(sFrom != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			sToMinDate = dateFormat.format(sFrom);
		}
		return sToMinDate;
	}

	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}

	public String getsFromMaxDate() {
		if(sTo != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			sFromMaxDate = dateFormat.format(sTo);
		}
		return sFromMaxDate;
	}

	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}
}
