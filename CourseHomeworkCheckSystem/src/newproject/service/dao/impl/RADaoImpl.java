package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import newproject.model.Project;
import newproject.model.RA;
import newproject.service.dao.RADao;

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
		sql = "insert into belongto value('"
				+ra.getRaId()+"','"+ra.getSetter()
				+"','"+ra.getDescription()+"');";
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
		return null;
		
	}

	@Override
	public List<RA> findByProject(String pid) {
		return null;
	}

}
