package al.ozone.bl.dao;

import java.util.List;

import al.ozone.bl.bean.SearchAuditTrailBean;
import al.ozone.bl.model.AuditTrail;


public interface AuditTrailDAO {
	
	void insert(AuditTrail record);

	List<AuditTrail> search(SearchAuditTrailBean sb);
	
}