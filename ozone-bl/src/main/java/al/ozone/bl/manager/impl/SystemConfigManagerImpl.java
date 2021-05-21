package al.ozone.bl.manager.impl;

import java.io.Serializable;
import java.util.List;

import al.ozone.bl.dao.GenericDAO;
import al.ozone.bl.manager.SystemConfigManager;
import al.ozone.bl.model.SystemConfigBean;

public class SystemConfigManagerImpl implements SystemConfigManager, Serializable {

	private static final long serialVersionUID = -7442853668542187638L;
	
	private GenericDAO<SystemConfigBean, String> systemConfigDAO;
	
	public GenericDAO<SystemConfigBean, String> getSystemConfigDAO() {
		return systemConfigDAO;
	}
	public void setSystemConfigDAO(GenericDAO<SystemConfigBean, String> systemConfigDAO) {
		this.systemConfigDAO = systemConfigDAO;
	}

	@Override
	public List<SystemConfigBean> getAll() {
		return systemConfigDAO.getAll();
	}

	@Override
	public void save(List<SystemConfigBean> values) {
		for (SystemConfigBean entry : values) {
			systemConfigDAO.update(entry);
		}
//		for (String key : values.keySet()) {
//            String value = values.get(key);
//            ApplicationConfig entry = new ApplicationConfig(key, value);
//            applicationConfigDAO.update(entry);
//        }
	}

}
