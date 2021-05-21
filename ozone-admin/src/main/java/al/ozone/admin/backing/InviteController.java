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
import javax.faces.component.html.HtmlSelectOneMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchInviteBean;
import al.ozone.bl.manager.InviteManager;
import al.ozone.bl.model.Invite;

/**
 */

@ManagedBean(name="inviteController")
@ViewScoped
public class InviteController implements Serializable{

	private static final long serialVersionUID = -7201097253160527073L;
	protected static final transient Log logger = LogFactory.getLog(InviteController.class);
	
	private HtmlInputText sInviter;
	private HtmlInputText sInvited;
	private List<Invite> invitesList;
	private Date sFrom;  
    private Date sTo;
    private String sToMinDate;
    private String sFromMaxDate;
    private HtmlSelectOneMenu sConfFirstPurchase;
	
	@ManagedProperty(value="#{inviteManager}") 
	private InviteManager inviteManager;
	
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
		sInviter.setValue(null);
		sInvited.setValue(null);
		sFrom = null;
		sTo = null;
		sToMinDate=null;
		sFromMaxDate=null;
		sConfFirstPurchase.setValue("");
	}

	/**
	 * This method performs a search in the database filtering by the field values inserted
	 * in the search form.
	 */
	public void search(){		
		SearchInviteBean sb = new SearchInviteBean();
		sb.setInviterEmail(JSFUtils.getStringFromUIInput(sInviter));
		sb.setInvitedEmail(JSFUtils.getStringFromUIInput(sInvited));
		sb.setFrom(sFrom);
		sb.setTo(sTo);
		sb.setConfFirstPurchase(JSFUtils.getBooleanFromUIInput(sConfFirstPurchase));
		invitesList = inviteManager.search(sb);
	}

	public HtmlInputText getsInviter() {
		return sInviter;
	}

	public void setsInviter(HtmlInputText sInviter) {
		this.sInviter = sInviter;
	}

	public HtmlInputText getsInvited() {
		return sInvited;
	}

	public void setsInvited(HtmlInputText sInvited) {
		this.sInvited = sInvited;
	}

	public List<Invite> getInvitesList() {
		return invitesList;
	}

	public void setInvitesList(List<Invite> invitesList) {
		this.invitesList = invitesList;
	}

	public void setInviteManager(InviteManager inviteManager) {
		this.inviteManager = inviteManager;
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

	public String getsToMinDate() {
		if(sFrom != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sToMinDate = dateFormat.format(sFrom);
		}
		return sToMinDate;
	}
	public String getsFromMaxDate() {
		if(sTo != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sFromMaxDate = dateFormat.format(sTo);
		}
		return sFromMaxDate;
	}

	public void setsToMinDate(String sToMinDate) {
		this.sToMinDate = sToMinDate;
	}

	public void setsFromMaxDate(String sFromMaxDate) {
		this.sFromMaxDate = sFromMaxDate;
	}

	public HtmlSelectOneMenu getsConfFirstPurchase() {
		return sConfFirstPurchase;
	}

	public void setsConfFirstPurchase(HtmlSelectOneMenu sConfFirstPurchase) {
		this.sConfFirstPurchase = sConfFirstPurchase;
	}

	
}
