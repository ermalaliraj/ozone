package al.ozone.bl.dao;
import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T extends Object, PK extends Serializable>{	
	
	public T get(PK key);
	
	public void insert(T record);
	
	public void update(T record);
	
	public void delete(T record);
	
	public List<T> search(T object);
	
	public List<T> getAll();
	
}


