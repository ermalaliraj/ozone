package al.ozone.bl.utils;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import NET.webserviceX.www.Currency;
import NET.webserviceX.www.CurrencyConvertor;
import NET.webserviceX.www.CurrencyConvertorSoap;
import al.ozone.bl.unitTest.AbstractSpringTestCase;



public class CurrencyTest  extends AbstractSpringTestCase {

    protected static final transient Log logger = LogFactory.getLog(CurrencyTest.class);

    public void testFileExtension(){
		try {
			
			CurrencyConvertor currencyConvertor = (CurrencyConvertor) applicationContext.getBean("currencyConvertor");
			CurrencyConvertorSoap serv = currencyConvertor.getCurrencyConvertorSoap();
			//double rate = serv.conversionRate(Currency.ALL, Currency.EUR);
			double rate = serv.conversionRate( Currency.EUR, Currency.ALL);
			//double rate = serv.conversionRate( Currency.EUR, Currency.ALL);
			//rate = rate+0.0004;
			System.out.println(rate);
			
			int lek = 1250;
			//int lek = 3450;
			BigDecimal res = new BigDecimal(rate);
			res = res.multiply(new BigDecimal(lek));
			res = res.setScale(2, BigDecimal.ROUND_HALF_UP);  
			
			System.out.println(res);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }
    
    
}
