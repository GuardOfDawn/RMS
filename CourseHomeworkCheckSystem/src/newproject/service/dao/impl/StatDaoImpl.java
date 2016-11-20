package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import rms.common.CountLimit;
import newproject.model.RiskCondition;
import newproject.model.RiskItem;
import newproject.service.dao.StatDao;

public class StatDaoImpl implements StatDao{
	private DBTool db;
	
	public StatDaoImpl() {
		db = new DBTool();
	}

	@Override
	public List<RiskCondition> findFrequentlyAddedRisks(Calendar start,
			Calendar end) {
		String sql = "select rid,time from riskstate where state='Problem' or state='ProblemSolved'";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<RiskCondition> list = new ArrayList<RiskCondition>();
		if(resultSet == null)
			return list;
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		try {
			while(resultSet.next()){
				String rid = resultSet.getString(1);
				Calendar time = db.convert(resultSet.getString(2));
				if((time.compareTo(start)>=0)&&(time.compareTo(end)<=0)){
					if(map.containsKey(resultSet.getString(1))){
						map.put(rid,map.get(rid)+1);
					}
					else{
						map.put(rid,1);
					}
				}
			}
			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		RiskDaoImpl temp = new RiskDaoImpl();
		Iterator<String> iter = map.keySet().iterator();
		int count = 0;
		while(iter.hasNext()){
			String key = iter.next();
			int value = map.get(key);
			RiskItem item = temp.find(key);
			RiskCondition risk = new RiskCondition();
			risk.setRisk(item);
			risk.setProblemTransformedCount(value);
			list.add(risk);
			if((++count)>=CountLimit.COUNT)
				break;
		}
		
		return list;
	}

	@Override
	public List<RiskCondition> findProblemRisks(Calendar start, Calendar end) {
		String sql = "select rid,time from riskstate where state='Removed' or state='UnRemoved'";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<RiskCondition> list = new ArrayList<RiskCondition>();
		if(resultSet == null)
			return list;
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		try {
			while(resultSet.next()){
				String rid = resultSet.getString(1);
				Calendar time = db.convert(resultSet.getString(2));
				if((time.compareTo(start)>=0)&&(time.compareTo(end)<=0)){
					if(map.containsKey(resultSet.getString(1))){
						map.put(rid,map.get(rid)+1);
					}
					else{
						map.put(rid,1);
					}
				}
			}
			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		RiskDaoImpl temp = new RiskDaoImpl();
		Iterator<String> iter = map.keySet().iterator();
		int count = 0;
		while(iter.hasNext()){
			String key = iter.next();
			int value = map.get(key);
			RiskItem item = temp.find(key);
			RiskCondition risk = new RiskCondition();
			risk.setRisk(item);
			risk.setRecognizedCount(value);
			list.add(risk);
			if((++count)>=CountLimit.COUNT)
				break;
		}
		return list;
	}
}
