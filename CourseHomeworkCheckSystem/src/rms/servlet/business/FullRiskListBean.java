package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

public class FullRiskListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<FullRisk> fullRiskList;

	public List<FullRisk> getFullRiskList() {
		return fullRiskList;
	}

	public void setFullRiskList(List<FullRisk> fullRiskList) {
		this.fullRiskList = fullRiskList;
	}
	
	public FullRisk getFullRisk(int index){
		return fullRiskList.get(index);
	}
	
	public int getSize(){
		return fullRiskList.size();
	}

}
