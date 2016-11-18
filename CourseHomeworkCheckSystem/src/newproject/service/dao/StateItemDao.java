package newproject.service.dao;

import java.util.List;

import newproject.model.StateItem;

public interface StateItemDao {
	
	public boolean insert(StateItem stateItem);
	
	public boolean remove(String sid);
	
	public boolean modify(StateItem stateItem);
	
	public List<StateItem> retrieve(String raId, String riskId);
}
