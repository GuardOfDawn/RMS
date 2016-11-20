package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import newproject.model.StateItem;

public class StateItemBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<StateItem> stateItemList;

	public List<StateItem> getStateItemList() {
		return stateItemList;
	}

	public void setStateItemList(List<StateItem> stateItemList) {
		this.stateItemList = stateItemList;
	}
	
	public StateItem getStateItem(int index){
		return stateItemList.get(index);
	}
	
	public int getSize(){
		return stateItemList.size();
	}

}
