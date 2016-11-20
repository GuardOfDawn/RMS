package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		return flag;
	}

	@Override
	public boolean modify(RA ra) {
		String sql = "update ra set description='"+ra.getDescription()
	            +"' where raid='"
	            +ra.getRaId()+"';";
		boolean flag = this.db.executeCUD(sql);
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
			
		}
		return result;
		
	}

	@Override
	public List<RA> findByProject(String pid) {
		return null;
	}

}
