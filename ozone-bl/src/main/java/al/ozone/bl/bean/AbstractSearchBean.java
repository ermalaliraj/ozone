package al.ozone.bl.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Used to give an abstraction on search beans throughout the entire
 * application. The class is shipped with the sort information for the grids.
 * 
 * @author Ermal Aliraj
 */
public abstract class AbstractSearchBean {

    private String sortColumn;
    private String sortDirection;
    
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("sortColumn", sortColumn)
        	.append("sortDirection", sortDirection)
        	.toString();
    }
}
