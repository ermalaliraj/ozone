package al.ozone.bl.model;

import java.util.List;
/**
 * The Class PagedResult, used for pages' grid Pagination
 * @param <T> the generic type
 * 
 * @author Ermal Aliraj
 */
public class PagedResult<T> {

    Integer totalCount;
    List<T> rowList;

    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public List<T> getRowList() {
        return rowList;
    }
    public void setRowList(List<T> rowList) {
        this.rowList = rowList;
    }

}
