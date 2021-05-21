package NET.webserviceX.www;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;


public class CurrencyClient {
	CurrencyClient(){
		
		try {
			Currency ALL = Currency.ALL;
			Currency EUR = Currency.EUR;
			
			CurrencyConvertorLocator ccl = new CurrencyConvertorLocator();
			CurrencyConvertorSoap serv = ccl.getCurrencyConvertorSoap();
			//double rate = serv.conversionRate(ALL, EUR);
			double rate = serv.conversionRate(EUR, ALL);
			System.out.println(rate);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
			
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CurrencyClient();
	}
}