package al.ozone.bl.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import al.ozone.bl.dao.CityDAO;
import al.ozone.bl.model.City;
import al.ozone.bl.model.Customer;

public class CityDAOImpl extends GenericDAOImpl<City, String> implements CityDAO {

	private static final long serialVersionUID = -2240651884323664094L;

	public CityDAOImpl(Class<City> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<City> getAllActives() {
		@SuppressWarnings("unchecked")
		List<City> list = getSqlSession().selectList("CITY.getAllActives", null);
        return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<City> getCustomerSubscriptions(Customer c){
		List<City> list = getSqlSession().selectList("CITY.getCustomerSubscriptions",c.getId());
		return list;
	}

	@Override
	public void insertCityForDeal(Integer dealId, String cityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("cityId", cityId);
		getSqlSession().update("CITY.insertCityForDeal", map);
	}

	@Override
	public void deleteCityForDeal(Integer dealId, String cityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("cityId", cityId);
		getSqlSession().delete("CITY.deleteCityForDeal", map);
	}

	@Override
	public void deleteCitiesForDeal(Integer dealId) {
		getSqlSession().delete("CITY.deleteCitiesForDeal", dealId);
	}	
}
