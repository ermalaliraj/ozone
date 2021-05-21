package al.ozone.bl.dao.impl;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchOrderBean;
import al.ozone.bl.dao.OrdersDAO;
import al.ozone.bl.model.Orders;

public class OrdersDAOImpl extends GenericDAOImpl<Orders, Integer> implements OrdersDAO {

	private static final long serialVersionUID = -8261967770649797275L;
	protected static final transient Log logger = LogFactory.getLog(OrdersDAOImpl.class);

	public OrdersDAOImpl(Class<Orders> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<Orders> search(SearchOrderBean sb) {
		@SuppressWarnings("unchecked")
		List<Orders> list = getSqlSession().selectList("ORDERS.search", sb);
        return list;
	}

	@Override
	public void addNote(Integer orderId, String note, boolean canceled) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderId", orderId);
        param.put("note", note);
        param.put("canceled", canceled);
		getSqlSession().update("ORDERS.addNote", param);
	}

}
