package newproject;

import java.util.Calendar;
import java.util.List;

import newproject.model.RiskCondition;

public interface StatService {

	/**
	 * 查询特定时间段被添加最多的风险
	 * @param start
	 * @param end
	 * @return
	 */
	public List<RiskCondition> retrieveFrequentlyAddedRisks(Calendar start,Calendar end);

	/**
	 * 查询特定时间段演变成问题最多的风险
	 * @param start
	 * @param end
	 * @return
	 */
	public List<RiskCondition> retrieveProblemTransformedRisks(Calendar start,Calendar end);

}
