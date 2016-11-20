package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import rms.common.RiskState;
import newproject.model.Project;
import newproject.model.RA;
import newproject.model.RiskItem;
import newproject.model.StateItem;
import newproject.service.dao.RADao;
import newproject.service.dao.RiskDao;
import newproject.service.dao.StateItemDao;

public class RADaoImpl implements RADao{
	private DBTool db;

	public RADaoImpl() {
		db = new DBTool();
	}
	
	@Override
	public boolean insert(RA ra) {
		String sql = "insert into ra value('"
				+ra.getRaId()+"','"+ra.getSetter()
				+"','"+ra.getDescription()+"');";
		boolean flag = this.db.executeCUD(sql);
		List<StateItem> items = ra.getRiskList();
		List<RiskItem> risks = ra.getRiskItemList();
		StateItemDao stateItem = new StateItemDaoImpl();
		RiskDao risk = new RiskDaoImpl();
		for(int i=0;i<items.size();i++){
			sql = "insert into belongto value('"
					+items.get(i).getStateId()+"','"+ra.getRaId()
					+"','"+items.get(i).getProjectId()+"');";
			db.executeCUD(sql);
			stateItem.insert(items.get(i));
			if(risk.find(items.get(i).getRiskId())==null){
				risk.insert(risks.get(i));
			}
		}
		
		return flag;
	}

	@Override
	public boolean remove(String raid) {
		String sql = "delete from ra where raid='"+raid+"';";
		boolean flag = this.db.executeCUD(sql);
		sql = "select sid from belongto where raid='"+raid+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return false;
		
		int row = 0;
		List<String> list = new ArrayList<String>();
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return false;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
			try {
				String sid = resultSet.getString(1);
				list.add(sid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			row--;
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "delete from belongto where raid='"+raid+"';";
		this.db.executeCUD(sql);
		
		for(int i=0;i<list.size();i++){
			new StateItemDaoImpl().remove(list.get(i));
		}

		return flag;
	}

	@Override
	public boolean modify(RA ra) {
		String sql = "update ra set description='"+ra.getDescription()
	            +"' where raid='"
	            +ra.getRaId()+"';";
		boolean flag = this.db.executeCUD(sql);
		List<StateItem> list = ra.getRiskList();
		for(int i=0;i<list.size();i++){
			new StateItemDaoImpl().modify(list.get(i));
		}
		return flag;
	}

	@Override
	public List<RA> findById(String userId) {
		List<RA> list = new ArrayList<RA>();
		String sql = "select raid,description from ra where setter='"+userId+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return null;
		RA ra = new RA();
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ra.setRaId(resultSet.getString(1));
			ra.setDescription(resultSet.getString(2));
			ra.setSetter(userId);
		} catch (SQLException e) {
			return null;
		}
		
		for(int i=0;i<list.size();i++){
			List<String> sids = getSids(list.get(i).getRaId());
			List<StateItem> items = new ArrayList<StateItem>();
			for(int j=0;j<sids.size();j++){
				StateItem item = retrieve(sids.get(j));
				items.add(item);
			}
			list.get(i).setRiskList(items);
		}
		return list;
		
	}

	@Override
	public List<RA> findByProject(String pid) {
		List<String> raids = getRids(pid);
		List<RA> ras = new ArrayList<RA>();
		for(int i=0;i<raids.size();i++){
			RA temp = getRA(raids.get(i));
			temp.setProjectId(pid);
			List<String> sids = getSids(raids.get(i));
			List<StateItem> items = new ArrayList<StateItem>();
			for(int j=0;j<sids.size();j++){
				StateItem item = retrieve(sids.get(j));
				items.add(item);
			}
			temp.setRiskList(items);
			ras.add(temp);
		}
		return ras;
	}

	private RA getRA(String string) {
		RA ra = new RA();
		String sql = "select setter,description from ra where raid='"+string+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return null;
		int row = 0;
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return null;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
			try {
				ra.setRaId(string);
				ra.setSetter(resultSet.getString(1));
				ra.setDescription(resultSet.getString(2));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			row--;
		}
		return ra;
	}

	private List<String> getSids(String string) {
		String sql = "select sid from belongto where raid='"+string+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<String> sids =  new ArrayList<String>();
		if(resultSet == null)
			return null;
		int row = 0;
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return null;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
			try {
				sids.add(resultSet.getString(1));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			row--;
		}
		return sids;
	}

	private List<String> getRids(String pid) {
		String sql = "select raid from belongto where pid='"+pid+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<String> raids = new ArrayList<String>();
		if(resultSet == null)
			return null;
		int row = 0;
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return null;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
			try {
				raids.add(resultSet.getString(1));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			row--;
		}
		return raids;
	}

	private StateItem retrieve(String sid){
		String sql = "select * from riskstate where sid='"+sid+"');";
		StateItem item = new StateItem();
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return item;
		int row = 0;
		try {
			row = resultSet.getRow();
		} catch (SQLException e) {
			return item;
		}
		while(row > 0){
			try {
				resultSet.next();
			} catch (SQLException e) {
				row--;
				continue;
			}
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
			row--;
		}
		return item;
	}
}
