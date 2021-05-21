package al.ozone.bl.manager.impl;

import java.util.List;

import al.ozone.bl.bean.SearchOrderBean;
import al.ozone.bl.dao.OrdersDAO;
import al.ozone.bl.manager.OrdersManager;
import al.ozone.bl.model.Orders;

public class OrdersManagerImpl implements OrdersManager{

	private OrdersDAO ordersDAO;

	public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}
	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}
	
	@Override
	public Orders get(Integer id) {
		return ordersDAO.get(id);
	}

	@Override
	public void insert(Orders order) {
		ordersDAO.insert(order);
	}

	@Override
	public List<Orders> getAll() {
		return ordersDAO.getAll();
	}

	@Override
	public List<Orders> search(SearchOrderBean sb) {
		return ordersDAO.search(sb);
	}
	
	@Override
	public void addNote(Integer orderId, String note, boolean canceled) {
		ordersDAO.addNote(orderId, note, canceled);
	}

}
