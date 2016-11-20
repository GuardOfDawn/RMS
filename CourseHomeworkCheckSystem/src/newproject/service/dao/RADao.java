package newproject.service.dao;

import java.util.List;

import newproject.model.RA;

public interface RADao {
	public boolean insert(RA ra);
	
	public boolean remove(String raid);
	
	public boolean modify(RA ra);
	
	public List<RA> findById(String userId);
	
	public List<RA> findByProject(String pid);
}
