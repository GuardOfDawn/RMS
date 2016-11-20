package newproject.service.impl;

import java.util.List;

import newproject.StateItemService;
import newproject.model.StateItem;
import newproject.service.dao.StateItemDao;
import newproject.service.dao.impl.StateItemDaoImpl;

public class StateItemServiceImpl implements StateItemService{
	
	private StateItemDao stateItemDao;
	
	public StateItemServiceImpl(){
		this.stateItemDao = new StateItemDaoImpl();
	}

	@Override
	public boolean addStateItem(StateItem stateItem) {
		boolean flag = this.stateItemDao.insert(stateItem);
		return flag;
	}

	@Override
	public boolean deleteStateItem(String sid) {
		boolean flag = this.stateItemDao.remove(sid);
		return flag;
	}

	@Override
	public boolean modifyStateItem(StateItem stateItem) {
		boolean flag = this.stateItemDao.modify(stateItem);
		return flag;
	}

	@Override
	public List<StateItem> retrieve(String raId, String riskId) {
		List<StateItem> list = this.stateItemDao.retrieve(raId, riskId);
		return list;
	}

	@Override
	public List<StateItem> retrieveRisks(String developterId) {
		// TODO Auto-generated method stub
		return null;
	}

}
