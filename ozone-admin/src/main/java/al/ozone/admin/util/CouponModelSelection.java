package al.ozone.admin.util;

import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  

import al.ozone.bl.model.Coupon;
  
public class CouponModelSelection extends ListDataModel<Coupon> implements SelectableDataModel<Coupon> {    
  
    public CouponModelSelection() {  
    }  
  
    public CouponModelSelection(List<Coupon> data) {  
        super(data);  
    }  
    
	@Override  
    public Coupon getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
		@SuppressWarnings("unchecked")
        List<Coupon> coupons = (List<Coupon>) getWrappedData();  
          
        for(Coupon c : coupons) {  
            if(c.getCode().equals(rowKey))  
                return c;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Coupon c) {  
        return c.getCode();  
    }  
}  