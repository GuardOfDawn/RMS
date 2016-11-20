package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import newproject.model.StateItem;
import newproject.service.dao.StateItemDao;
import rms.common.RiskState;
public class StateItemDaoImpl implements StateItemDao{
	
	private DBTool db;
	
	public StateItemDaoImpl(){
		this.db = new DBTool();
	}

	@Override
	public boolean insert(StateItem stateItem) {
		String sql = "insert into riskstate value('"+stateItem.getStateId()+"','"
				+stateItem.getRiskId()+"','"+stateItem.getDescription()+"','"
				+stateItem.getState().toString()+"','"+stateItem.getPossibility().toString()
				+"','"+stateItem.getEffectlevel().toString()+"','"+stateItem.getThreshold()
				+"','"+stateItem.getComitter()+"','"+stateItem.getFollower()+"','"
				+this.db.convert(stateItem.getTime())+"');";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	@Override
	public boolean remove(String sid) {
		String sql1 = "delete from riskstate where sid='"+sid+"';";
		boolean flag = this.db.executeCUD(sql1);
		String sql2 = "delete from belongto where sid='"+sid+"';";
		flag = flag && this.db.executeCUD(sql2);
		return flag;
	}

	@Override
	public boolean modify(StateItem stateItem) {
		String sql = "update riskstate set rid='"+stateItem.getRiskId()
		        +"',description='"+stateItem.getDescription()
		        +"',state='"+stateItem.getState().toString()
		        +"',possibility='"+stateItem.getPossibility().toString()
				+"',effectlevel='"+stateItem.getEffectlevel().toString()
				+"',threshold='"+stateItem.getThreshold()
				+"',committer='"+stateItem.getComitter()
				+"',follower='"+stateItem.getFollower()
				+"',time='"+this.db.convert(stateItem.getTime())
				+" where sid='"+stateItem.getStateId()+"';";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	
	@Override
	public List<StateItem> retrieve(String raId, String riskId) {
		String sql = "select * from riskstate where ";
		List<StateItem> list = new ArrayList<StateItem>();
		if(raId == null && riskId == null)
			return list;
		else if(raId != null && riskId == null){
			sql = sql + "sid in (select sid from belongto where raid='" 
		         + raId + "');";
		}
		else if(raId == null && riskId != null){
			sql = sql + "rid='" + riskId + "';";
		}
		else{
			sql = sql + "sid in (select sid from belongto where raid='" 
			         + raId + "') and rid='"+riskId+"';";
		}
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return list;
		int row = 0;
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
			StateItem item = new StateItem();
			try {
				item.setStateId(resultSet.getString(1));
				item.setRiskId(resultSet.getString(2));
				item.setDescription(resultSet.getString(3));
				item.setState(RiskState.valueOf(resultSet.getString(4)));
				item.setPossibility(resultSet.getString(5));
				item.setEffectlevel(resultSet.getString(6));
				item.setThreshold(resultSet.getString(7));
				item.setComitter(resultSet.getString(8));
				item.setFollower(resultSet.getString(9));
				item.setTime(this.db.convert(resultSet.getString(10)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list.add(item);
			row--;
		}
		return list;
	}

	@Override
	public List<StateItem> retrieve(String developperID) {
		String sql = "select * from riskstate where follower='"+developperID+"');";
		List<StateItem> list = new ArrayList<StateItem>();
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return list;
		int row = 0;
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
			StateItem item = new StateItem();
			try {
				item.setStateId(resultSet.getString(1));
				item.setRiskId(resultSet.getString(2));
				item.setDescription(resultSet.getString(3));
				item.setState(RiskState.valueOf(resultSet.getString(4)));
				item.setPossibility(resultSet.getString(5));
				item.setEffectlevel(resultSet.getString(6));
				item.setThreshold(resultSet.getString(7));
				item.setComitter(resultSet.getString(8));
				item.setFollower(resultSet.getString(9));
				item.setTime(this.db.convert(resultSet.getString(10)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list.add(item);
			row--;
		}
		return list;
	}

}
