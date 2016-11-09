package rms.dao.impl;

import java.util.List;

import rms.model.RiskItem;
import rms.model.StateItem;

public interface RiskItemDAO {
	
	public boolean add(RiskItem riskItem);
	
	public boolean remove(String riskID);
	
	public boolean modify(RiskItem riskItem);
	
	public List<RiskItem> findAll(String userID);
	
	public RiskItem find(String riskID);
	
	public boolean addStateItem(String riskID,StateItem stateItem);
	
	public boolean deleteStateItem(String riskID,String stateID);
	
	public boolean modifyStateItem(String riskID,StateItem stateItem);
	
	public List<StateItem> findStateItem(String riskID);
}
