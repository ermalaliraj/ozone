package NET.webserviceX.www;

import javax.xml.rpc.ServiceException;

public class CurrencyConverterClient {

	private CurrencyConvertor currencyConvertor;
	private CurrencyConvertorSoap service;
	
	public CurrencyConvertor getCurrencyConvertor() {
		return currencyConvertor;
	}
	public void setCurrencyConvertor(CurrencyConvertor currencyConvertor) {
		this.currencyConvertor = currencyConvertor;
	}
	public CurrencyConvertorSoap getService() {
		return service;
	}
	public void setService(CurrencyConvertorSoap service) {
		this.service = service;
	}

	public CurrencyConverterClient(){
		try {
			service = currencyConvertor.getCurrencyConvertorSoap();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
}
