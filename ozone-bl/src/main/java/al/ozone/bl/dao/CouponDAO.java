package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.model.Coupon;

public interface CouponDAO {

	public Coupon get(String id);

	public void insert(Coupon coupon);

	public void changeStatus(Coupon coupon);

	public List<Coupon> search(SearchCouponBean sb);

	public List<Coupon> getAllCouponsForDeal(Integer dealId);
	
	public List<Coupon> getAllUsedCouponsForDeal(Coupon sb);
	
}
