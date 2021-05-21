package al.ozone.bl.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.ClassUtils;

import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.dao.GenericDAO;

public class GenericDAOImpl<T extends Object, PK extends Serializable> extends BaseDAO implements GenericDAO<T, PK>, Serializable  {

	private static final long serialVersionUID = 1941268494775750423L;
	
	private String sqlMapPrefix ;
	
	public String getSqlMapPrefix() {
		return sqlMapPrefix;
	}

	public GenericDAOImpl(){

	}

	public GenericDAOImpl(final Class<T> persistentClass) {
        this.sqlMapPrefix = ClassUtils.getShortClassName(persistentClass);
    }
	
	public void setSqlMapPrefix(String sqlMapPrefix) {
		this.sqlMapPrefix = sqlMapPrefix;
	}

	@SuppressWarnings("unchecked")
	public T get(PK key) {
		T object = (T) getSqlSession().selectOne(sqlMapPrefix.toUpperCase()+".get", key);  
		return object;
	}

	public void delete(T record) {
		getSqlSession().delete(sqlMapPrefix.toUpperCase()+".delete", record);
	}

	public void insert(T record) {
		getSqlSession().insert(sqlMapPrefix.toUpperCase()+".insert", record);
	}
	
	public void update(T record) {
		getSqlSession().update(sqlMapPrefix.toUpperCase()+".update", record);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> search(T record) {
		List<T> list = getSqlSession().selectList(sqlMapPrefix.toUpperCase()+".search", record);
        return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> list = getSqlSession().selectList(sqlMapPrefix.toUpperCase()+".getAll", null);
        return list;
	}
}
