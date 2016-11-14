package rms.service;

import java.util.List;

import rms.model.RiskItem;

public interface RiskService {

	public boolean addRisk(RiskItem item);
	
	public boolean deleteRisk(String riskId);
	
	public boolean modifyRisk(RiskItem item);
	
	public List<RiskItem> retrieveAllRisks();
	
	public List<RiskItem> retrieveRisks(String userId);
	
	public List<RiskItem> retrieveFollowedRisks(String userId);
	
	public RiskItem getRiskInfo(String riskId);
	
}
