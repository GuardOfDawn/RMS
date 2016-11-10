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
	
	private int[] followCondition;
	
	public void setRiskList(List<RiskItem> riskList,String userId){
		this.riskList = riskList;
		followCondition = new int[riskList.size()];
		for(int i=0;i<riskList.size();i++){
			String followerId = riskList.get(i).getFollowerId();
			if(followerId.equals("null")){
				followCondition[i] = 0;
			}
			else{
				String[] Ids = followerId.split(",");
				for(int j=0;j<Ids.length;j++){
					if(userId.equals(Ids[j])){
						followCondition[i] = 1;
						break;
					}
				}
			}
		}
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
	
	public void setFollowCondition(int index,int follow){
		followCondition[index] = follow;
	}
	
	public int isFollowed(int index){
		return followCondition[index];
	}

}
