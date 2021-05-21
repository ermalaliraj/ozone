package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.dao.CouponDAO;
import al.ozone.bl.dao.PurchaseDAO;
import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.model.Coupon;

public class CouponManagerImpl implements CouponManager, Serializable {
	
	private static final long serialVersionUID = -3998134133070215115L;
	protected static final transient Log logger = LogFactory.getLog(CouponManagerImpl.class);
	
	public CouponDAO couponDAO;
	public PurchaseDAO purchaseDAO;
	//private CodeGeneratorDAO codeGeneratorDAO;
	
	public CouponDAO getCouponDAO() {
		return couponDAO;
	}
	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}
	public PurchaseDAO getPurchaseDAO() {
		return purchaseDAO;
	}
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	
	@Override
	public Coupon get(String id) {
		return couponDAO.get(id);
	}

	@Override
	public void insert(Coupon coupon) {
		couponDAO.insert(coupon);
	}

	@Override
	public void changeStatus(Coupon coupon) {
		couponDAO.changeStatus(coupon);
	}

	@Override
	public List<Coupon> search(SearchCouponBean sb) {
		return couponDAO.search(sb);
	}
	@Override
	public List<Coupon> getAllCouponsForDeal(Integer dealId) {
		return couponDAO.getAllCouponsForDeal(dealId);
	}
	
	@Override
	/**
	 * Search the coupons for specific deal
	 */
	public List<Coupon> getAllUsedCouponsForDeal(Coupon sb) {
		return couponDAO.getAllUsedCouponsForDeal(sb);
	}

}
