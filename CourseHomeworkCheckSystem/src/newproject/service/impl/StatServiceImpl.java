package newproject.service.impl;

import java.util.Calendar;
import java.util.List;

import newproject.StatService;
import newproject.model.RiskCondition;
import newproject.service.dao.StatDao;
import newproject.service.dao.impl.StatDaoImpl;

public class StatServiceImpl implements StatService{
	private StatDao dao;
	
	public StatServiceImpl() {
		dao = new StatDaoImpl();
	}

	@Override
	public List<RiskCondition> retrieveFrequentlyAddedRisks(Calendar start,
			Calendar end) {
		List<RiskCondition> condition = this.dao.findFrequentlyAddedRisks(start, end);
		return condition;
	}

	@Override
	public List<RiskCondition> retrieveProblemTransformedRisks(Calendar start,
			Calendar end) {
		List<RiskCondition> condition = this.dao.findProblemRisks(start, end);
		return condition;
	}

}
