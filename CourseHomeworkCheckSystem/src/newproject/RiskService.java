package newproject;

import java.util.List;

import newproject.model.RiskItem;

public interface RiskService {

	public boolean addRisk(RiskItem item);
	
	public boolean deleteRisk(String riskId);
	
	public boolean modifyRisk(RiskItem item);
	
	public List<RiskItem> retrieveAllRisks();
	
	public List<RiskItem> retrieveRisks(String developterId);
	
	public RiskItem getRiskInfo(String riskId);
	
}
