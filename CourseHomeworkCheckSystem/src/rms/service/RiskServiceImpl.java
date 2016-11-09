package rms.service;

import java.util.List;

import rms.dao.impl.RiskItemDAO;
import rms.dao.impl.RiskItemDAOImpl;
import rms.model.RiskItem;

public class RiskServiceImpl implements RiskService{
	
	private RiskItemDAO dao;
	
	public RiskServiceImpl(){
		this.dao = new RiskItemDAOImpl();
	}

	@Override
	public boolean addRisk(RiskItem item) {
		boolean state = this.dao.add(item);
		return state;
	}

	@Override
	public boolean deleteRisk(String riskId) {
		boolean state = this.dao.remove(riskId);
		return state;
	}

	@Override
	public boolean modifyRisk(RiskItem item) {
		boolean state = this.dao.modify(item);
		return state;
	}

	@Override
	public List<RiskItem> retrieveRisks(String userId) {
		List<RiskItem> result = this.dao.findAll(userId);
		return result;
	}

	@Override
	public RiskItem getRiskInfo(String riskId) {
		RiskItem result = this.dao.find(riskId);
		return result;
	}

}
