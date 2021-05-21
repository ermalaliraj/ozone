package al.ozone.admin.backing;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.manager.DealManager;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.Partner;

@ManagedBean(name="couponSingleController")
@ViewScoped
public class CouponSingleController implements Serializable{

	private static final long serialVersionUID = -3065547715405275931L;
	protected static final transient Log logger = LogFactory.getLog(CouponSingleController.class);

	private DealChoice dealChoice;
	private Coupon coupon;
	private MapModel mapEmptyModel;  

	@ManagedProperty(value="#{dealManager}") 
	private DealManager dealManager;
	@ManagedProperty(value="#{couponManager}") 
	private CouponManager couponManager;	
	
	//TODO 
	private String uploadsFolder = "http://www.ozone.al/uploads/";
	
	@PostConstruct
	public void init() {
		String couponId = (String) JSFUtils.getObjectFromSession("couponToCheck");
		logger.debug("Entering with couponToCheck="+couponId);		
		coupon = couponManager.get(couponId);
		dealChoice = dealManager.getDealChoice(coupon.getDealId(), coupon.getDealChoiceNr());
		createMarkerOnMap();
	}

	/**
	 * Considering the partner coordinates, creates the marker on the map
	 */
	private void createMarkerOnMap() {
		Partner partner = dealChoice.getDeal().getPartner();
		
		double mapLat = partner.getLat();
		double mapLng = partner.getLng();
		mapEmptyModel = new DefaultMapModel();
		Marker marker = new Marker(new LatLng(mapLat, mapLng), partner.getName());
		marker.setDraggable(true);
		mapEmptyModel.addOverlay(marker);
	}
	
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public CouponManager getCouponManager() {
		return couponManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public String getUploadsFolder() {
		return uploadsFolder;
	}

	public void setUploadsFolder(String uploadsFolder) {
		this.uploadsFolder = uploadsFolder;
	}
	
	public MapModel getMapEmptyModel() {
		return mapEmptyModel;
	}

	public void setMapEmptyModel(MapModel mapEmptyModel) {
		this.mapEmptyModel = mapEmptyModel;
	}

	public DealChoice getDealChoice() {
		return dealChoice;
	}

	public void setDealChoice(DealChoice dealChoice) {
		this.dealChoice = dealChoice;
	}

	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}
	
}