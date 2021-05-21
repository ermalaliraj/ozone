package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchBeanEmailNewsletter;
import al.ozone.bl.manager.EmailsIntroManager;
import al.ozone.bl.model.EmailNewsletter;
import al.ozone.bl.model.PagedResult;

/**
 */

@ManagedBean(name="emailsIntroController")
@RequestScoped
public class EmailsIntroController implements Serializable{
	
	private static final long serialVersionUID = 2070975351651452814L;
	protected static final transient Log logger = LogFactory.getLog(EmailsIntroController.class);
	
	private HtmlInputText sEmail;
	private HtmlSelectOneMenu sAcceptNewsletter;
	private List<EmailNewsletter> emailsList;
	private LazyDataModel<EmailNewsletter> lazyModel;
	
	@ManagedProperty(value="#{emailsIntroManager}") 
	private EmailsIntroManager emailsIntroManager;
	
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
		sEmail.setValue(null);
		sAcceptNewsletter.setValue("");
	}

	/**
	 * This method performs a search in the database filtering by the field values inserted
	 * in the search form.
	 */
	public void search(){		
		String em = JSFUtils.getStringFromUIInput(sEmail);
		SearchBeanEmailNewsletter sb = new SearchBeanEmailNewsletter(em);
		sb.setAcceptNewsletter(JSFUtils.getBooleanFromUIInput(sAcceptNewsletter));
		int count = emailsIntroManager.searchCount(sb);
		lazyModel = null;
		getLazyModel().setRowCount(count);
	}

	public HtmlInputText getsEmail() {
		return sEmail;
	}

	public void setsEmail(HtmlInputText sEmail) {
		this.sEmail = sEmail;
	}

	public List<EmailNewsletter> getEmailsList() {
		return emailsList;
	}

	public void setEmailsList(List<EmailNewsletter> emailsList) {
		this.emailsList = emailsList;
	}

	public void setEmailsIntroManager(EmailsIntroManager emailsIntroManager) {
		this.emailsIntroManager = emailsIntroManager;
	}
	public HtmlSelectOneMenu getsAcceptNewsletter() {
		return sAcceptNewsletter;
	}
	public void setsAcceptNewsletter(HtmlSelectOneMenu sAcceptNewsletter) {
		this.sAcceptNewsletter = sAcceptNewsletter;
	}

	public LazyDataModel<EmailNewsletter> getLazyModel() {
		if (lazyModel==null){  
			lazyModel = new LazyDataModel<EmailNewsletter>() {
				private static final long serialVersionUID = -4347190403366939315L;

				@Override
				public List<EmailNewsletter> load(int first, int pageSize, String sortField,
						SortOrder sortOrder, Map<String, String> filters) {
			        
					String sortDir = "";
			        if (SortOrder.ASCENDING.equals(sortOrder)) {
			            sortDir = "ASC";
			        } else if (SortOrder.DESCENDING.equals(sortOrder)) {
			            sortDir = "DESC";
			        }
			        
			        SearchBeanEmailNewsletter e = new SearchBeanEmailNewsletter(JSFUtils.getStringFromUIInput(sEmail));
			        e.setAcceptNewsletter(JSFUtils.getBooleanFromUIInput(sAcceptNewsletter));
			        e.setSortColumn(sortField);
					e.setSortDirection(sortDir);

					PagedResult<EmailNewsletter> pagedResult = emailsIntroManager.loadLazy(e, first, pageSize);
					setRowCount(pagedResult.getTotalCount());  
					setWrappedData(pagedResult.getRowList());
					setEmailsList(pagedResult.getRowList());
					
					//logger.debug("Got from DB "+pagedResult.getRowList().size()+" emails");
					return pagedResult.getRowList(); 
				}
				
			    @Override
			    /**
			     * Override setRowIndex() as workaround for exception on RequestScope
			     * Issue 1544: LazyDataTable.setRowIndex throws arithmetic exception: division by 0.
			     */
			    public void setRowIndex(int rowIndex) {
			        /*
			         * The following is in ancestor (LazyDataModel):
			         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
			         */
			        if (rowIndex == -1 || getPageSize() == 0) {
			            super.setRowIndex(-1);
			        }
			        else
			            super.setRowIndex(rowIndex % getPageSize());
			    }

	        };
		}
        return lazyModel;
	}
}
