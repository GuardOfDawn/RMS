package rms.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rms.common.Role;
import rms.model.User;

public class UserDAOImpl implements UserDAO{
	
	private DaoHelper daoHelper;
	
	public UserDAOImpl(){
		this.daoHelper = DaoHelperImpl.getBaseDaoInstance();
	}
	
	private boolean execute(String sql){
		Connection connection = this.daoHelper.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return false;
		}
		boolean state = false;
		try {
			state = statement.execute(sql);
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return false;
		}
		this.daoHelper.closeConnection(connection);
		return state;
	}

	@Override
	public boolean add(User user) {
		String sql = "insert into user value("+user.getUserID()+
				","+user.getPassword()+",";
		switch(user.getRole()){
		case QualityManager:
			sql += "0);";
			break;
		case SoftwareEngineer:
			sql += "1);";
			break;
		}
		boolean state = this.execute(sql);
		return state;
	}

	@Override
	public boolean remove(String userID) {
		String sql = "delete from user where uid='"+userID+"';";
		boolean state = execute(sql);
		return state;
	}

	@Override
	public boolean modify(User user) {
		String sql = "update user set password='"+user.getPassword()+"',role=";
		Role role = user.getRole();
		if(role == Role.QualityManager){
			sql += "0";
		} else if(role == Role.SoftwareEngineer){
			sql += "1";
		} else {
			sql += "2";
		}
		sql += " where uid='"+user.getUserID()+"';";
		boolean state = execute(sql);
		return state;
	}

	@Override
	public User find(String userID) {
		String sql = "select uid,password,role from user where uid='"+userID+"';";
		Connection connection = this.daoHelper.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			return null;
		}
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e1) {
			return null;
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			return null;
		}
		User user = new User();
		int role = 2;
		try {
			user.setUserID(resultSet.getString(1));
			user.setPassword(resultSet.getString(2));
			role = resultSet.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(role == 0){
			user.setRole(Role.QualityManager);
		}
		else if(role == 1){
			user.setRole(Role.SoftwareEngineer);
		}
		else{
			user.setRole(Role.Failure);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		String sql = "select uid,password,role from user;";
		List<User> list = new ArrayList<User>();
		Connection connection = this.daoHelper.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			return null;
		}
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e1) {
			return null;
		}
		try {
			int role = 2;
			while(resultSet.next()){
				User user = new User();
				user.setUserID(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				role = resultSet.getInt(3);
				switch(role){
				case 0:
					user.setRole(Role.QualityManager);
					break;
				case 1:
					user.setRole(Role.SoftwareEngineer);
					break;
				default:
					user.setRole(Role.Failure);
				}
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
