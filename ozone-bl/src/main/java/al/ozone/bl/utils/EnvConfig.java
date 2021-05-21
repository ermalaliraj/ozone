package al.ozone.bl.utils;

import java.io.Serializable;

public class EnvConfig implements Serializable{

	private static final long serialVersionUID = 743918664975774436L;
	
	private String uploadsFolder;
	private String uploadsUrl;
	
	private String ppEnv;
	private String ppSellerEmail;
	private String ppCurrencyCode;
	private String ppReturnUrl;
	private String ppCancelUrl;
	private String ppNotifyUrl;
	private String ppButtonImg;
	private double deltaExchangeCurr; //delta exchange currency when converting LEK to EUR
	private String ppEwpCertPath;
	private String ppPrivateKey;
	private String ppCertId;
	private String ppCertPath;
	private String ppIdentifyToken; //token used to call PayPal on PDT
	
	private String epEnv;
	private String epMerchantUser;
	private String epMerchantRefNr;
	
	private String fbAppId;
	
	public String getUploadsFolder() {
		return uploadsFolder;
	}
	public void setUploadsFolder(String uploadsFolder) {
		this.uploadsFolder = uploadsFolder;
	}
	public String getUploadsUrl() {
		return uploadsUrl;
	}
	public void setUploadsUrl(String uploadsUrl) {
		this.uploadsUrl = uploadsUrl;
	}
	public String getPpEnv() {
		return ppEnv;
	}
	public void setPpEnv(String ppEnv) {
		this.ppEnv = ppEnv;
	}
	public String getPpSellerEmail() {
		return ppSellerEmail;
	}
	public void setPpSellerEmail(String ppSellerEmail) {
		this.ppSellerEmail = ppSellerEmail;
	}
	public String getPpCurrencyCode() {
		return ppCurrencyCode;
	}
	public void setPpCurrencyCode(String ppCurrencyCode) {
		this.ppCurrencyCode = ppCurrencyCode;
	}
	public String getPpReturnUrl() {
		return ppReturnUrl;
	}
	public void setPpReturnUrl(String ppReturnUrl) {
		this.ppReturnUrl = ppReturnUrl;
	}
	public String getPpButtonImg() {
		return ppButtonImg;
	}
	public void setPpButtonImg(String ppButtonImg) {
		this.ppButtonImg = ppButtonImg;
	}
	public String getPpCancelUrl() {
		return ppCancelUrl;
	}
	public void setPpCancelUrl(String ppCancelUrl) {
		this.ppCancelUrl = ppCancelUrl;
	}
	public double getDeltaExchangeCurr() {
		return deltaExchangeCurr;
	}
	public void setDeltaExchangeCurr(double deltaExchangeCurr) {
		this.deltaExchangeCurr = deltaExchangeCurr;
	}
	public String getPpNotifyUrl() {
		return ppNotifyUrl;
	}
	public void setPpNotifyUrl(String ppNotifyUrl) {
		this.ppNotifyUrl = ppNotifyUrl;
	}
	public String getPpPrivateKey() {
		return ppPrivateKey;
	}
	public void setPpPrivateKey(String ppPrivateKey) {
		this.ppPrivateKey = ppPrivateKey;
	}
	public String getPpEwpCertPath() {
		return ppEwpCertPath;
	}
	public void setPpEwpCertPath(String ppEwpCertPath) {
		this.ppEwpCertPath = ppEwpCertPath;
	}
	public String getPpCertId() {
		return ppCertId;
	}
	public void setPpCertId(String ppCertId) {
		this.ppCertId = ppCertId;
	}
	public String getPpCertPath() {
		return ppCertPath;
	}
	public void setPpCertPath(String ppCertPath) {
		this.ppCertPath = ppCertPath;
	}
	public String getPpIdentifyToken() {
		return ppIdentifyToken;
	}
	public void setPpIdentifyToken(String ppIdentifyToken) {
		this.ppIdentifyToken = ppIdentifyToken;
	}
	public String getEpEnv() {
		return epEnv;
	}
	public void setEpEnv(String epEnv) {
		this.epEnv = epEnv;
	}
	public String getEpMerchantUser() {
		return epMerchantUser;
	}
	public void setEpMerchantUser(String epMerchantUser) {
		this.epMerchantUser = epMerchantUser;
	}
	public String getEpMerchantRefNr() {
		return epMerchantRefNr;
	}
	public void setEpMerchantRefNr(String epMerchantRefNr) {
		this.epMerchantRefNr = epMerchantRefNr;
	}
	public String getFbAppId() {
		return fbAppId;
	}
	public void setFbAppId(String fbAppId) {
		this.fbAppId = fbAppId;
	}
	
}