package al.ozone.admin.lazymodel;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import al.ozone.bl.bean.SearchCustomerBean;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.PagedResult;
import al.ozone.bl.utils.ZUtils;

/**
 *
 * @author Ermal Aliraj
 */
public class CustomerLazyDataModel extends LazyDataModel<Customer> {

	private static final long serialVersionUID = 439436675713252646L;
	protected static final transient Log logger = LogFactory.getLog(CustomerLazyDataModel.class);
	
	private CustomerManager customerManager;
	
	public CustomerLazyDataModel() {
	}
	public CustomerLazyDataModel(CustomerManager customerManager) {
		super();
		this.customerManager = customerManager;
	}
	
	public CustomerManager getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
	@Override
	public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder,
								Map<String, String> filters) {
		//System.out.println("sortField:"+sortField+" sortOrder:"+sortOrder);
		String sortDir = "";
        if (SortOrder.ASCENDING.equals(sortOrder)) {
            sortDir = "ASC";
        } else if (SortOrder.DESCENDING.equals(sortOrder)) {
            sortDir = "DESC";
        }
        
        SearchCustomerBean c = new SearchCustomerBean();
        c.setId(filters.get("id"));
        c.setName(filters.get("name"));
        c.setSurname(filters.get("surname"));
        c.setEmail(filters.get("email"));
        c.setSex(filters.get("sex"));
        c.setActive(ZUtils.getBooleanFromString((filters.get("active"))));
        
        if(ZUtils.isEmptyString(sortField)){
        	sortField = "REG_DATE";
        	sortDir = "DESC";
        }
		c.setSortColumn(sortField);
		c.setSortDirection(sortDir);

		PagedResult<Customer> pagedResult = customerManager.loadLazy(c, first, pageSize);
		setRowCount(pagedResult.getTotalCount());  
		setWrappedData(pagedResult.getRowList());
		//ZUtils.printListOnLogger(pagedResult.getRowList(), logger, "debug");
		//logger.debug("Got from DB "+pagedResult.getRowList().size()+" customers");
		return pagedResult.getRowList(); 
	}
	
	
	@Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }
}

