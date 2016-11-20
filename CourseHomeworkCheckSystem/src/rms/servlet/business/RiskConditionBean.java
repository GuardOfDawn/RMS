package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import newproject.model.RiskCondition;

public class RiskConditionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RiskCondition> riskList;

	public List<RiskCondition> getRiskList() {
		return riskList;
	}

	public void setRiskList(List<RiskCondition> riskList) {
		this.riskList = riskList;
	}
	
	public RiskCondition getRiskCondition(int index){
		return riskList.get(index);
	}
	
	public int getSize(){
		return riskList.size();
	}

}
