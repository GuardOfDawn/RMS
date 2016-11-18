package newproject.service.dao;

import java.util.List;

import newproject.model.RiskItem;

public interface RiskDao {
	
	public boolean insert(RiskItem item);
	
	public boolean remove(String id);
	
	public boolean modify(RiskItem item);
	
	public RiskItem find(String riskId);
	
	public List<RiskItem> retrieveAllRisks();

	public List<RiskItem> retrieveRisks(String developterId);
}
