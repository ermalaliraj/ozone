package al.ozone.bl.manager;

import java.util.List;

import al.ozone.bl.bean.SearchOrderBean;
import al.ozone.bl.model.Orders;

public interface OrdersManager {
	
    public Orders get(Integer id);
    
    public void insert (Orders order);

	public List<Orders> getAll();

	public List<Orders> search(SearchOrderBean sb);

	public void addNote(Integer orderId, String note, boolean canceled);
	
}
