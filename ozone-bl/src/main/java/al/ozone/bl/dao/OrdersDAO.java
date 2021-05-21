package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchOrderBean;
import al.ozone.bl.model.Orders;

public interface OrdersDAO extends GenericDAO<Orders, Integer>  {

	public List<Orders> search(SearchOrderBean sb);

	public void addNote(Integer orderId, String note, boolean canceled);

}