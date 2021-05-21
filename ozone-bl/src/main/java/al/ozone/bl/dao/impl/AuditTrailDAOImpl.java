package al.ozone.bl.dao.impl;

import java.util.List;

import al.ozone.bl.bean.SearchAuditTrailBean;
import al.ozone.bl.dao.AuditTrailDAO;
import al.ozone.bl.dao.BaseDAO;
import al.ozone.bl.model.AuditTrail;

public class AuditTrailDAOImpl extends BaseDAO implements AuditTrailDAO {

	public AuditTrailDAOImpl(){
		super();
	}
	
	public void insert(AuditTrail record) {
		getSqlSession().insert("AUDITTRAIL.insert", record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrail> search(SearchAuditTrailBean sb) {
		List<AuditTrail> list = getSqlSession().selectList("AUDITTRAIL.search",sb);
		return list;
	}
}
