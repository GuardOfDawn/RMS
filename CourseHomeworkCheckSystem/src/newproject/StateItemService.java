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
	
	/**
	 * 查找某个软件开发者需要负责的风险条目
	 * @param developterId
	 * @return
	 */
	public List<StateItem> retrieveRisks(String developterId);

}
