package al.ozone.bl.manager;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import al.ozone.bl.model.City;

public interface CityManager {
	
    public City get(String id);
    
    @Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
    public void insert (City city);
    
    @Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
    public void update (City city);
    
    @Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
    public void delete (City city);
    
    @Secured( { "ROLE_ADMIN","ROLE_PUBLISH","ROLE_WRITE" })
    public List<City> search(City city);
	
	public List<City> getAll();

	public List<City> getAllActives();
	
}
