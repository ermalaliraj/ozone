package al.ozone.bl.unitTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.model.Category;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Credit;
import al.ozone.bl.model.Customer;
import al.ozone.bl.model.Deal;
import al.ozone.bl.model.DealChoice;
import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.Partner;
import al.ozone.bl.model.Payment;
import al.ozone.bl.model.PaymentCash;
import al.ozone.bl.model.Publication;
import al.ozone.bl.model.Purchase;

public class TestUtils {
	
	protected static final transient Log logger = LogFactory.getLog(TestUtils.class);
	
	public static City createCity(String cityCode, String cityName) {
    	City city = new City();
    	city.setId(cityCode);
    	city.setName(cityName); 
    	return city;		
	}

	public static Partner createPartner(String partnerName, City city) {
    	Partner p = new Partner();
    	p.setName(partnerName);
    	p.setCity(city);
    	p.setCategory(new Category(1));
		return p;
	}
	
	public static Deal createDeal(String nomeDeal, Partner p){
    	Deal deal = new Deal();
    	deal.setTitle(nomeDeal);
    	deal.setPartner(p);
    	deal.setClientFullName("rappresentante nome");
    	deal.setClientCel("rep cel");
    	deal.setBrokerFullName("broker junit");
    	deal.setBrokerCel("broker cel");	
    	
    	deal.setMainImgName("nomeImg.jsp");
    	deal.setSynthesis("synthesis....");
    	deal.setConditions("conditions....");
    	deal.setDescription("descriptions...."); 
    	deal.setApprovedForPublish(true);
    	deal.setApprovedUser("ermal");
    	deal.setLastUpdateUser("ermal");
    	//this deal needs for publishing. Because the publishManager.insert() will set the deal status 
    	//to published, this assignment helps for not failing the test
    	//deal.setPublished(true);
    	return deal;
	}

	public static Publication createPublication(Deal deal) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		try {
			startDate = dateFormat.parse("20/12/2011");
		} catch (ParseException e) {
			logger.debug("Not a date string", e);
		}
		
		Date endDate = null;
		try {
			endDate = dateFormat.parse("23/12/2011");
		} catch (ParseException e) {
			logger.debug("Not a date string", e);
		}
		
		Publication pub = new Publication();
		pub.setStartDate(startDate);
		pub.setEndDate(endDate);
		pub.setOrder(1);
		pub.setStatus(Publication.STATUS_WAITING);
		pub.setDeal(deal);
		pub.setCity(deal.getPartner().getCity());
		return pub;
	}

	public static Customer createCustomer(String id) {
		Customer c = new Customer();
    	c.setName("name");
    	c.setSurname("surname");
    	c.setBirthdate(new Date());
    	c.setEmail(id);
    	c.setPassword("password");
    	c.setPhone("1010");
		return c;
	}

	public static Credit createCredit(Customer customer, int value) {
		Credit credit = new Credit();
		Date now = new Date();
		credit.setAssignedDate(now);
		credit.setValidDate(now);
		//credit.setUsedDate(now);
		credit.setValue(value);
		credit.setCustomer(customer);
		credit.setAbout("credit assigned to customer through junit test");
		credit.setAboutUse(null);
		
		return credit;
	}

	public static DiscountCard createDiscountCard(int perc) {
		DiscountCard dc = new DiscountCard();
		dc.setId("dc"+perc);
		dc.setPercDiscount(perc);
		dc.setUsedDate(new Date());
		return dc;
	}

	public static Purchase createPurchase(DealChoice dealChoice, Customer customer,
											Payment payment, DiscountCard discountCard) {
		Purchase pur = new Purchase();
		pur.setQuantity(1);
		pur.setPurchDate(new Date());
		pur.setAmount(2500);
		pur.setMoneySpent(2000);
		pur.setCreditSpent(0);
		pur.setDealChoice(dealChoice);
		pur.setCustomer(customer);
		pur.setPayment(payment);
		//pur.setCredits(customer.getCredits());
		pur.setDiscount(discountCard);
		
		return pur;
	}

	public static Payment createPaymentCash() {
		PaymentCash payment = new PaymentCash();
		payment.setAmount(500);
		payment.setSellerFullName("seller junit");
		payment.setBuyerFullName("buyer junit");
		payment.setBuyerTel("324 junit");
		payment.setCustomerName("junit name cus");
		payment.setCustomerSurname("junit name cus");
		payment.setCustomerEmail("junit name cus");
		payment.setNote("junit note");
		payment.setOperationDate(new Date());
		
		return payment;
	}

	public static DealChoice createDealChoice(int dealId, int choiceNr) {
		DealChoice dc = new DealChoice(dealId, choiceNr);
		dc.setChoiceTitle("choice title "+choiceNr);
		dc.setFullPrice(200);
		dc.setPrice(100);
		dc.setMinCustomers(1);
		dc.setMinCustomers(5);
		return dc;
	}

}
