package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import newproject.model.RiskItem;
import newproject.service.dao.RiskDao;

public class RiskDaoImpl implements RiskDao{
	
	private DBTool db;
	
	public RiskDaoImpl(){
		this.db = new DBTool();
	}

	@Override
	public boolean insert(RiskItem item) {
		String sql = "insert into risk value('"+item.getRiskId()+"','"
				+item.getTitle()+"','"+item.getDescription()+"');";
		this.db.executeCUD(sql);
		return true;
	}

	@Override
	public boolean remove(String id) {
		String sql1 = "delete from risk where rid='"+id+"';";
		boolean flag = this.db.executeCUD(sql1);
		String sql2 = "delete from riskstate where rid='"+id+"';";
		flag = flag && this.db.executeCUD(sql2);
		return flag;
	}

	@Override
	public boolean modify(RiskItem item) {
		String sql = "update risk set title='"+item.getTitle()+"',description='"
				+item.getDescription()+"' where rid='"+item.getRiskId()+"';";
		this.db.executeCUD(sql);
		return true;
	}

	@Override
	public RiskItem find(String riskId) {
		String sql = "select rid,title,description from risk where rid='"+riskId+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return null;
		RiskItem result = null;
		try {
			if(resultSet.next()){
				result = new RiskItem();
				result.setRiskId(resultSet.getString(1));
				result.setTitle(resultSet.getString(2));
				result.setDescription(resultSet.getString(3));
			}
			resultSet.close();
			DBTool.connectionList.get(0).close();
			DBTool.connectionList.remove(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<RiskItem> retrieveAllRisks() {
		String sql = "select rid,title,description from risk;";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<RiskItem> list = new ArrayList<RiskItem>();
		if(resultSet == null)
			return list;
		try {
			while(resultSet.next()){
				RiskItem item = new RiskItem();
				item.setRiskId(resultSet.getString(1));
				item.setTitle(resultSet.getString(2));
				item.setDescription(resultSet.getString(3));
				list.add(item);
			}
			resultSet.close();
			DBTool.connectionList.get(0).close();
			DBTool.connectionList.remove(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<RiskItem> retrieveRisks(String developterId) {
		return null; //这个接口貌似没什么卵用
//		String sql = "select rid,title,description from risk where rid in ";
//		ResultSet resultSet = this.db.executeQuery(sql);
//		List<RiskItem> list = new ArrayList<RiskItem>();
//		if(resultSet == null)
//			return list;
//		int row = 0;
//		try {
//			row = resultSet.getRow();
//		} catch (SQLException e) {
//			return list;
//		}
//		while(row > 0){
//			try {
//				resultSet.next();
//			} catch (SQLException e) {
//				row--;
//				continue;
//			}
//			//TODO
//			row--;
//		}
//		return list;
	}

}
