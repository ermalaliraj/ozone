package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.model.Coupon;

public interface CouponManager {
	
	public Coupon get(String id);
	
	public void insert(Coupon coupon) throws Exception;
	
	@Secured( { "ROLE_ADMIN", "ROLE_PARTNER" })
	public void changeStatus(Coupon coupon) throws Exception;

	@Secured( { "ROLE_ADMIN" })
	public List<Coupon> search(SearchCouponBean sb);

	public List<Coupon> getAllCouponsForDeal(Integer dealId);
	
	public List<Coupon> getAllUsedCouponsForDeal(Coupon sb);
	
}
