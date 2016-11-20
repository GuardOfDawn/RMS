package newproject.service.impl;

import java.util.List;

import newproject.RiskService;
import newproject.model.RiskItem;
import newproject.service.dao.RiskDao;
import newproject.service.dao.impl.RiskDaoImpl;

public class RiskServiceImpl implements RiskService{
	
	private RiskDao riskDao;
	
	public RiskServiceImpl(){
		this.riskDao = new RiskDaoImpl();
	}

	@Override
	public boolean addRisk(RiskItem item) {
		boolean flag = this.riskDao.insert(item);
		return flag;
	}

	@Override
	public boolean deleteRisk(String riskId) {
		boolean flag = this.riskDao.remove(riskId);
		return flag;
	}

	@Override
	public boolean modifyRisk(RiskItem item) {
		boolean flag = this.riskDao.modify(item);
		return flag;
	}

	@Override
	public List<RiskItem> retrieveAllRisks() {
		List<RiskItem> list = this.riskDao.retrieveAllRisks();
		return list;
	}

	@Override
	public List<RiskItem> retrieveRisks(String developterId) {
		List<RiskItem> list = this.riskDao.retrieveRisks(developterId);
		return list;
	}

	@Override
	public RiskItem getRiskInfo(String riskId) {
		RiskItem result = this.riskDao.find(riskId);
		return result;
	}

}
