package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import rms.model.RiskItem;

public class RiskListBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RiskItem> riskList;
	
	public void setRiskList(List<RiskItem> riskList){
		this.riskList = riskList;
	}
	
	public List<RiskItem> getRiskList(){
		return this.riskList;
	}
	
	public RiskItem getRisk(int index){
		return riskList.get(index);
	}
	
	public int getSize(){
		return riskList.size();
	}

}
