package al.ozone.bl.dao.impl;

import java.util.List;

import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.dao.CouponDAO;
import al.ozone.bl.model.Coupon;

public class CouponDAOImpl extends GenericDAOImpl<Coupon, Integer> implements CouponDAO {

	private static final long serialVersionUID = 1L;

	public CouponDAOImpl(Class<Coupon> persistentClass) {
		super(persistentClass);
	}

	@Override
	public Coupon get(String id) {
		Coupon c = (Coupon) getSqlSession().selectOne("COUPON.get", id);
		return c;
	}
	
	@Override
	public void insert(Coupon coupon) {
		getSqlSession().insert("COUPON.insert", coupon);
	}

	@Override
	public void changeStatus(Coupon coupon) {
		getSqlSession().update("COUPON.changeStatus", coupon);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Coupon> search(SearchCouponBean sb) {
		List<Coupon> list = getSqlSession().selectList("COUPON.search",sb);
		return list;
	}
	
	@Override
	public List<Coupon> getAllCouponsForDeal(Integer dealId) {
		@SuppressWarnings("unchecked")
		List<Coupon> list = (List<Coupon>) getSqlSession().selectList("COUPON.getAllCouponsForDeal", dealId);
		return list;
	}
	
	@Override
	public List<Coupon> getAllUsedCouponsForDeal(Coupon sb) {
		@SuppressWarnings("unchecked")
		List<Coupon> list = (List<Coupon>) getSqlSession().selectList("COUPON.getAllUsedCouponsForDeal", sb);
		return list;
	}
}
