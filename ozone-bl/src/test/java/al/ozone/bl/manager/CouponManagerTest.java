package al.ozone.bl.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.unitTest.AbstractSpringTestCase;
import al.ozone.bl.unitTest.TestUtils;

public class CouponManagerTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CouponManagerTest.class);
    
	protected CouponManager couponManager;

	protected void setUp() throws Exception {
		super.setUp();
		couponManager = (CouponManager) applicationContext.getBean("couponManager");
	}
	
	public void testGet() throws Exception{
		Coupon c = couponManager.get("SD12KM3");
		System.out.println(c);
	}
	
}
