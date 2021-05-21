package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.CityManager;
import al.ozone.bl.dao.CityDAO;
import al.ozone.bl.model.City;

public class CityManagerImpl implements CityManager, Serializable {

	private static final long serialVersionUID = -4378678859425839940L;
	protected static final transient Log logger = LogFactory.getLog(CityManagerImpl.class);
	
	private CityDAO cityDAO;
	
	@Override
	public City get(String id) {
		return cityDAO.get(id);
	}

	@Override
	public void insert(City city) {
		cityDAO.insert(city);
	}

	@Override
	public void update(City city) {
		cityDAO.update(city);
	}

	@Override
	public void delete(City city) {
		cityDAO.delete(city);
	}

	public List<City> search(City city) {
		return cityDAO.search(city);
	}

	public List<City> getAll() {
		return cityDAO.getAll();
	}
	
	@Override
	public List<City> getAllActives() {
		return cityDAO.getAllActives();
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}
}
