package al.ozone.bl.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PaymentBank extends Payment implements Serializable{

	private static final long serialVersionUID = -3053147355025474986L;
	public static final String DETAIL_PAGE_NAME = "paymentBankDetailDlg.xhtml";

	private String bankName;
	private String refNr;
	private String fullPayerName;
	private Date transferDate; // date of transfer. When user payed the money
	private String note;
	
	public PaymentBank(){
		paymentType = Payment.TYPE_BANK;
	}
	
	@Override
	public String getPaymentType() {	
		return paymentType;
	}
	
	public String getDetailPageName() {
		return DETAIL_PAGE_NAME;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRefNr() {
		return refNr;
	}

	public void setRefNr(String refNr) {
		this.refNr = refNr;
	}

	public String getFullPayerName() {
		return fullPayerName;
	}

	public void setFullPayerName(String fullPayerName) {
		this.fullPayerName = fullPayerName;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("bankName", bankName)
        	.append("fullPayerName", fullPayerName)
        	.append("refNr", refNr)
        	.append("transferDate", transferDate)
        	.append("note", note) 	
        	.toString();
    }

}
