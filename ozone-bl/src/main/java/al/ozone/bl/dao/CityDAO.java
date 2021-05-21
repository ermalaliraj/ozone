package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;

public interface CityDAO extends GenericDAO<City, String>{
	
	public List<City> getAllActives();
	
	public List<City> getCustomerSubscriptions(Customer c);

	public void insertCityForDeal(Integer dealId, String cityId);

	public void deleteCityForDeal(Integer dealId, String cityId);

	public void deleteCitiesForDeal(Integer id);

}
