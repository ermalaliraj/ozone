package al.ozone.bl.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Prova {

	public final static double WEEK_1 = 0.23;
	public final static double WEEK_2 = 0.46;
	public final static double WEEK_3 = 0.7;	
		
	public static void main(String[]args) throws UnsupportedEncodingException{
		
//		String str = "Rr. \"Besim Alla\" P. eabej 1/1/9";
//		System.out.println(str);
//		str = URLDecoder.decode(str, "UTF-8");
//		System.out.println(str);
		
		int a = 621;
		int b = 1125;
		
		long c = a/10;
		
		System.out.println("c:"+c);
		System.out.println("b:"+b/10);
		
		String stre2 = "Rr. \"Besim Alla\" P. eabej 1/1/9";
		stre2 = URLEncoder.encode(stre2, "windows-1252");		
		System.out.println(stre2);
		
		stre2 = URLDecoder.decode(stre2, "windows-1252");
		System.out.println(stre2);
		
//		int priceSellOZone = 2500;
//		double percentCommission = 10.0;
//		
//		BigDecimal singleEarning = new BigDecimal(priceSellOZone);
//		singleEarning = singleEarning.multiply(new BigDecimal(percentCommission));
//		singleEarning = singleEarning.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
//		
//		System.out.println(singleEarning.intValue());
//		
//		
//		double d = 30.00;
//		System.out.println(d);
//		int i = (int) d;
//		System.out.println(i);
//		Date startDate = null;
//		Date endDate = null;
//		
//		String date = "01/10/2012";
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		try {
//			startDate = dateFormat.parse(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		endDate = ZUtils.addDaysToDate(startDate, 7);
//		System.out.println("is today in range of ["+startDate+", "+endDate+"]");
//		
//		boolean isWithin = ZUtils.isTodayWithinRange( startDate, endDate);
//		System.out.println(isWithin);
//		BigDecimal priceB = new BigDecimal(159);
//		BigDecimal fullPrice = new BigDecimal(199);
//	
//		priceB = priceB.divide(fullPrice, 2, BigDecimal.ROUND_HALF_UP);
//		
//		System.out.println(priceB);

//		GregorianCalendar calendar = new GregorianCalendar();
//		calendar.setTime(startDate);
//		
//		int DAY_OF_YEAR = calendar.get(Calendar.DAY_OF_YEAR);
//		int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
//		int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
//		int weekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
//		System.out.println("DAY_OF_YEAR="+DAY_OF_YEAR);
//		System.out.println("DAY_OF_MONTH="+DAY_OF_MONTH);
//		System.out.println("DAY_OF_WEEK="+DAY_OF_WEEK);
//		System.out.println("weekInMonth="+weekInMonth);
//
//		int YEAR = calendar.get(Calendar.YEAR);
//		int MONTH = calendar.get(Calendar.MONTH);
//		int WEEK_OF_MONTH = calendar.get(Calendar.WEEK_OF_MONTH);
//		int WEEK_OF_YEAR = calendar.get(Calendar.WEEK_OF_YEAR);
//		//int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
//		
//		System.out.println("YEAR="+YEAR);
//		System.out.println("MONTH="+MONTH);
//		System.out.println("WEEK_OF_MONTH="+WEEK_OF_MONTH);
//		System.out.println("WEEK_OF_YEAR="+WEEK_OF_YEAR);
//		System.out.println("WEEK_OF_YEAR="+WEEK_OF_YEAR);
//		
//		useCal();
//		TimeZone tz= TimeZone.getTimeZone("Europe/Rome");
//		
//		Date midnight = midnight(startDate, tz);
//		System.out.println("midnight:"+midnight);
    }

	@SuppressWarnings("unused")
	private static void useCal() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2009, Calendar.DECEMBER, 31, 20, 15, 00);
		cal.set(Calendar.MILLISECOND, 0);
		Date d = cal.getTime();
		System.out.println("useCal:"+d);
	}
	
	public static Date midnight(Date date){
		Calendar cal = new GregorianCalendar();
		  cal.setTime(date);
		  cal.set(Calendar.HOUR_OF_DAY, 0);
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MILLISECOND, 0);
		  return cal.getTime();
	}
	public static Date midnight(Date date, TimeZone tz) {
		  Calendar cal = new GregorianCalendar(tz);
		  cal.setTime(date);
		  cal.set(Calendar.HOUR_OF_DAY, 0);
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MILLISECOND, 0);
		  return cal.getTime();
	}
}
