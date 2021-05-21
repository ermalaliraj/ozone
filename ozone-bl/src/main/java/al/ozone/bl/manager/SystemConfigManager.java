package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.model.SystemConfigBean;

public interface SystemConfigManager {
	
    public List<SystemConfigBean> getAll();

    @Secured( { "ROLE_ADMIN" })
    public void save(List<SystemConfigBean> list);
    
}