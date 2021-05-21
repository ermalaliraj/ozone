package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.SelectEvent;

import al.ozone.admin.util.CouponModelSelection;
import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.bean.SearchCouponBean;
import al.ozone.bl.manager.AuditTrailManager;
import al.ozone.bl.manager.CouponManager;
import al.ozone.bl.manager.CustomerManager;
import al.ozone.bl.manager.PurchaseManager;
import al.ozone.bl.model.Coupon;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.Email;
import al.ozone.bl.model.Purchase;
import al.ozone.bl.utils.UrlEncrypter;
import al.ozone.bl.utils.ZUtils;
import al.ozone.engine.email.EmailEngine;

/**
 * View managed backing bean of "couponsList.jsp". It provides a list view of
 * the coupons present in the db and a search (filter) functionality.
 * 
 * @author Florjan Gogaj
 */

@ManagedBean(name = "couponController")
@ViewScoped
public class CouponController implements Serializable {

	private static final long serialVersionUID = 8791732704898739708L;
	protected static final transient Log logger = LogFactory
			.getLog(CouponController.class);

	// Binding variables - Search form
	private HtmlInputText sPurchaseId;
	private HtmlInputText sContractId;
	private HtmlInputText sDealTitle;
	private HtmlInputText sCouponCode;
	private HtmlInputText sCouponSecCode;
	private HtmlInputText sEmail;
	private HtmlSelectOneMenu sStatus;
	private boolean sNoFake = true;

	// Coupon Usage
	private HtmlSelectOneMenu fStatus;
	private String aboutUse;
	private HtmlSelectOneMenu fAllStatus;// multiCoupons

	private List<Coupon> couponsList;
	private String couponToCheck;
	private Coupon couponSelected;
	private Coupon[] selectedCoupons;
	private boolean editMode;

	private CouponModelSelection couponModelSelection;

	// Injected properties
	@ManagedProperty(value = "#{couponManager}")
	private CouponManager couponManager;
	@ManagedProperty(value = "#{auditTrailManager}")
	private AuditTrailManager auditTrailManager;
	@ManagedProperty(value = "#{emailEngine}")
	private EmailEngine emailEngine;
	@ManagedProperty(value = "#{customerManager}")
	private CustomerManager customerManager;
	@ManagedProperty(value = "#{purchaseManager}")
	private PurchaseManager purchaseManager;
	@ManagedProperty(value = "#{urlEncrypter}")
	private UrlEncrypter urlEncrypter;

	/**
	 * Initializes the page. It calls first the search() method.
	 */
	@PostConstruct
	public void init() {
		search();
	}

	/**
	 * Resets the search form cleaning all fields values.
	 */
	public void cleanSearchForm() {
		sPurchaseId.setValue(null);
		sContractId.setValue(null);
		sDealTitle.setValue(null);
		sCouponCode.setValue(null);
		sCouponSecCode.setValue(null);
		sEmail.setValue(null);
		sStatus.setValue("");
		sNoFake = true;
	}

	/**
	 * This method performs a search in the database filtering by the field
	 * values inserted in the search form. A dedicated searching bean is used.
	 */
	public void search() {
		SearchCouponBean sb = new SearchCouponBean();
		sb.setContractId(JSFUtils.getIntegerFromUIInput(sContractId));
		sb.setDealTitle(JSFUtils.getStringFromUIInput(sDealTitle));
		sb.setPurchaseId(JSFUtils.getIntegerFromUIInput(sPurchaseId));
		sb.setCouponCode(JSFUtils.getStringFromUIInput(sCouponCode));
		sb.setCouponSecCode(JSFUtils.getStringFromUIInput(sCouponSecCode));
		sb.setEmail(JSFUtils.getStringFromUIInput(sEmail));
		sb.setStatus(JSFUtils.getStringFromUIInput(sStatus));
		sb.setNoFake(Boolean.valueOf(sNoFake));
		// logger.debug("sb:"+sb);
		couponsList = couponManager.search(sb);
		couponModelSelection = new CouponModelSelection(couponsList);
	}

	public String checkCoupon() {
		JSFUtils.putObjectInSession("couponToCheck", couponToCheck);
		return "success";
	}

	public void printCoupon() {
		checkCoupon();
		JSFUtils.redirectToPage("printCoupon?couponId=" + couponToCheck);
	}

	public void useCoupon() {
		String newStatus = JSFUtils.getStringFromUIInput(fStatus);
		try {
			// logger.debug("going to change stats of coupon "+couponSelected.getCode()+" from "+couponSelected.getStatus()+" to "+newStatus);

			couponSelected.setStatus(newStatus);
			couponSelected.setLastStatusChange(new Date());
			couponManager.changeStatus(couponSelected);
			auditTrailManager.auditCouponStatusChange(couponSelected);
			logger.info("New status " + newStatus
					+ " set successfully for coupon "
					+ couponSelected.getCode() + " customer "
					+ couponSelected.getCustomerEmail());
			JSFUtils.addInfoMessage("New status changed successfully");

			sendFeedback(couponSelected.getCode(), newStatus);
		} catch (Exception e) {
			logger.error("Stats for coupon " + couponSelected.getCode()
					+ " can not be changed from " + couponSelected.getStatus()
					+ " to " + newStatus, e);
			JSFUtils.addErrorMessage(ZUtils.getShortString(e.getCause()
					.toString(), 50));
			auditTrailManager.auditErrorCouponStatusChange(couponSelected);
		}

		fStatus.setValue(null);
	}

	public void useMultiCoupon() {
		String newStatus = JSFUtils.getStringFromUIInput(fAllStatus);

		for (int i = 0; i < selectedCoupons.length; i++) {
			try {
				// logger.debug("going to change state of coupon "+selectedCoupons[i].getCode()+" from "+selectedCoupons[i].getStatus()+" to "+newStatus);

				selectedCoupons[i].setStatus(newStatus);
				selectedCoupons[i].setLastStatusChange(new Date());
				couponManager.changeStatus(selectedCoupons[i]);
				auditTrailManager.auditCouponStatusChange(selectedCoupons[i]);
				logger.info("New status " + newStatus
						+ " set successfully for coupon "
						+ selectedCoupons[i].getCode() + " customer "
						+ selectedCoupons[i].getCustomerEmail());

				sendFeedback(selectedCoupons[i].getCode(), newStatus);
			} catch (Exception e) {
				logger.error(
						"Stats for coupon " + selectedCoupons[i].getCode()
								+ " can not be changed from "
								+ selectedCoupons[i].getStatus() + " to "
								+ newStatus, e);
				auditTrailManager
						.auditErrorCouponStatusChange(selectedCoupons[i]);
				JSFUtils.addErrorMessage(ZUtils.getShortString(e.getCause()
						.toString(), 50));
			}

			fAllStatus.setValue(null);
		}

		JSFUtils.addInfoMessage("New status changed successfully for all coupons");
	}

	/**
	 * Send feedback to the customer which purchased this coupon, if newStatus
	 * is USED
	 * 
	 * @param couponCode
	 * @param newStatus
	 */
	private void sendFeedback(String couponCode, String newStatus) {
		if (newStatus.equals(Coupon.STATUS_USED)) {
			Purchase pur = purchaseManager.getPurchaseForCoupon(couponCode);

			// if the purchase to which this coupon belongs to,
			// isFeedbackRequestedForDeal=false, ask the feedback
			if (!pur.isFeedbackRequested()) {
				try {
					Deal deal = pur.getDealChoice().getDeal();

					Email email = new Email("DealFeedback");
					email.setSubject("Nje mendim per sherbimin tek "
							+ deal.getPartner().getName());
					email.addParameter("dealId", deal.getId());
					email.addParameter("dealTitle", deal.getTitle());
					email.addParameter("partner", deal.getPartner().getName());
					email.addParameter("cusEmail",
							urlEncrypter.encrypt(pur.getCustomer().getEmail()));
					// when the user will login directly from email's link, we
					// can not use the password
					// for authentication since spring 3.0 do not allow login
					// with hashed password.
					// For security reason we use as token registration_date
					email.addParameter(
							"token",
							urlEncrypter.encrypt(pur.getCustomer()
									.getReg_Date().toString()));
					email.addTo(pur.getCustomer().getEmail());

					emailEngine.addEmail(email);

					purchaseManager.setFeedbackRequestedForPurchase(
							pur.getId(), true);
					// logger.debug("feedbackRequestedForPurchase=true for purchase "+
					// pur.getId());
				} catch (Exception e) {
					logger.error("A problem occoured trying to send feedback for customer "
							+ pur.getCustomer().getEmail()
							+ " of purchase:"
							+ pur.getId());
				}
			} else {
				logger.debug("Purchase " + pur.getId()
						+ " has already sent the feedback");
			}
		}
	}

	public void rowSelected(SelectEvent event) {
		// System.out.println("array:"+selectedCoupons.length);
		// System.out.println("SELECTED AREA:" + event.getSource());
		// System.out.println("SELECTED AREA:" + event.getBehavior());

		@SuppressWarnings("unused")
		UIComponent c = event.getComponent();
		// System.out.println(c);
	}

	public HtmlInputText getsContractId() {
		return sContractId;
	}

	public void setsContractId(HtmlInputText sContractId) {
		this.sContractId = sContractId;
	}

	public HtmlInputText getsDealTitle() {
		return sDealTitle;
	}

	public void setsDealTitle(HtmlInputText sDealTitle) {
		this.sDealTitle = sDealTitle;
	}

	public HtmlInputText getsPurchaseId() {
		return sPurchaseId;
	}

	public void setsPurchaseId(HtmlInputText sPurchaseId) {
		this.sPurchaseId = sPurchaseId;
	}

	public List<Coupon> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<Coupon> couponsList) {
		this.couponsList = couponsList;
	}

	public CouponManager getCouponManager() {
		return couponManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public String getCouponToCheck() {
		return couponToCheck;
	}

	public void setCouponToCheck(String couponToCheck) {
		this.couponToCheck = couponToCheck;
	}

	public HtmlInputText getsCouponCode() {
		return sCouponCode;
	}

	public void setsCouponCode(HtmlInputText sCouponCode) {
		this.sCouponCode = sCouponCode;
	}

	public HtmlInputText getsEmail() {
		return sEmail;
	}

	public void setsEmail(HtmlInputText sEmail) {
		this.sEmail = sEmail;
	}

	public String getAboutUse() {
		return aboutUse;
	}

	public void setAboutUse(String aboutUse) {
		this.aboutUse = aboutUse;
	}

	public Coupon getCouponSelected() {
		return couponSelected;
	}

	public void setCouponSelected(Coupon couponSelected) {
		this.couponSelected = couponSelected;
	}

	public HtmlSelectOneMenu getfStatus() {
		return fStatus;
	}

	public void setfStatus(HtmlSelectOneMenu fStatus) {
		this.fStatus = fStatus;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public CouponModelSelection getCouponModelSelection() {
		return couponModelSelection;
	}

	public void setCouponModelSelection(
			CouponModelSelection couponModelSelection) {
		this.couponModelSelection = couponModelSelection;
	}

	public Coupon[] getSelectedCoupons() {
		return selectedCoupons;
	}

	public void setSelectedCoupons(Coupon[] selectedCoupons) {
		this.selectedCoupons = selectedCoupons;
	}

	public void setAuditTrailManager(AuditTrailManager auditTrailManager) {
		this.auditTrailManager = auditTrailManager;
	}

	public HtmlSelectOneMenu getfAllStatus() {
		return fAllStatus;
	}

	public void setfAllStatus(HtmlSelectOneMenu fAllStatus) {
		this.fAllStatus = fAllStatus;
	}

	public HtmlSelectOneMenu getsStatus() {
		return sStatus;
	}

	public void setsStatus(HtmlSelectOneMenu sStatus) {
		this.sStatus = sStatus;
	}

	public HtmlInputText getsCouponSecCode() {
		return sCouponSecCode;
	}

	public void setsCouponSecCode(HtmlInputText sCouponSecCode) {
		this.sCouponSecCode = sCouponSecCode;
	}

	public boolean issNoFake() {
		return sNoFake;
	}

	public void setsNoFake(boolean sNoFake) {
		this.sNoFake = sNoFake;
	}

	public void setEmailEngine(EmailEngine emailEngine) {
		this.emailEngine = emailEngine;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setPurchaseManager(PurchaseManager purchaseManager) {
		this.purchaseManager = purchaseManager;
	}

	public void setUrlEncrypter(UrlEncrypter urlEncrypter) {
		this.urlEncrypter = urlEncrypter;
	}
}
