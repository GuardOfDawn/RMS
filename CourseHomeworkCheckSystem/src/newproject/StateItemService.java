package newproject;

import java.util.List;

import newproject.model.StateItem;

public interface StateItemService {
	
	public boolean addStateItem(StateItem stateItem);

	public boolean deleteStateItem(String sid);

	public boolean modifyStateItem(StateItem stateItem);
	
	/**
	 * 查找某个风险管理计划的某个风险条目的状态记录
	 * @param raid
	 * @param rid
	 * @return
	 */
	public List<StateItem> retrieve(String raId,String riskId);

}
