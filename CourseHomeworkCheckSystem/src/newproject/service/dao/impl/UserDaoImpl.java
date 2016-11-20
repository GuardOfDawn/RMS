package newproject.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import newproject.model.User;
import newproject.service.dao.UserDao;
import rms.common.Role;

public class UserDaoImpl implements UserDao{
	
	private DBTool db;
	
	public UserDaoImpl(){
		this.db = new DBTool();
	}

	@Override
	public boolean insert(User user) {
		String sql = "insert into user value('"+user.getUserID()+"','"+
				user.getUserName()+"','"+user.getPassword()+"','"
				+user.getRole().toString()+"');";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	@Override
	public boolean remove(String id) {
		String sql1 = "delete from user where uid='"+id+"';";
		boolean flag = this.db.executeCUD(sql1);
		String sql2 = "delete from `join` where uid='"+id+"';";
		flag = flag && this.db.executeCUD(sql2);
		return flag;
	}

	@Override
	public boolean modify(User user) {
		String sql = "update user set uname='"+user.getUserName()+"',password='"
				+user.getPassword()+"',role='"+user.getRole().toString()+"' where uid='"
				+user.getUserID()+"';";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	@Override
	public User find(String id) {
		String sql = "select uid,uname,password,role from user where uid='"+id+"';";
		ResultSet resultSet = this.db.executeQuery(sql);
		if(resultSet == null)
			return null;
		int row = 0;
		try {
			resultSet.next();
			row = resultSet.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(row == 0)
			return null;
		User user = new User();
		try {
			user.setUserID(resultSet.getString(1));
			user.setUserName(resultSet.getString(2));
			user.setPassword(resultSet.getString(3));
			user.setRole(Role.valueOf(resultSet.getString(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> retrieveUsers() {
		String sql = "select uid,uname,password,role from user;";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<User> list = new ArrayList<User>();
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
			User user = new User();
			try {
				user.setUserID(resultSet.getString(1));
				user.setUserName(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setRole(Role.valueOf(resultSet.getString(4)));
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
		return list;
	}

}
