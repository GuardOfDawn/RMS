package newproject.service.dao;

import java.util.Calendar;
import java.util.List;

import newproject.model.RiskCondition;

public interface StatDao {
	public List<RiskCondition> findFrequentlyAddedRisks(Calendar start,Calendar end);
	
	public List<RiskCondition> findProblemRisks(Calendar start,Calendar end);
}
