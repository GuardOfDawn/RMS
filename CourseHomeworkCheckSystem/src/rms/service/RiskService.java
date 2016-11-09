package rms.service;

import java.util.List;

import rms.model.RiskItem;

public interface RiskService {

	public boolean addRisk(RiskItem item);
	
	public boolean deleteRisk(String riskId);
	
	public boolean modifyRisk(RiskItem item);
	
	public List<RiskItem> retrieveRisks(String userId);
	
	public RiskItem getRiskInfo(String riskId);
	
}
