package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import rms.common.Role;
import newproject.model.Project;
import newproject.model.RiskCondition;
import newproject.model.User;
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
		
		int row = 0;
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return list;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
			try {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list.add(user);
			row--;
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<RiskCondition> findProblemRisks(Calendar start, Calendar end) {
		
		return null;
	}
}
