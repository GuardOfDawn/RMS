package newproject.service.dao.impl;

import newproject.service.dao.DaoHelper;
import newproject.service.dao.DaoHelperImpl;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DBTool {
	private DaoHelper daoHelper;
	public DBTool(){
		this.daoHelper = DaoHelperImpl.getBaseDaoInstance();
	}
	public boolean executeCUD(String sql){
		Connection connection = this.daoHelper.getConnection();
		if(connection == null)
			return false;
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return false;
		}
		if(statement == null)
			return false;
		boolean result = false;
		try {
			result = statement.execute(sql);
		} catch (SQLException e) {
			
		} finally{
			this.daoHelper.closeConnection(connection);
		}
		return result;
	}
	public ResultSet executeQuery(String sql){
		Connection connection = this.daoHelper.getConnection();
		if(connection == null)
			return null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return null;
		}
		if(statement == null)
			return null;
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
		} finally {
			this.daoHelper.closeConnection(connection);
		}
		return resultSet;
	}
	public String convert(Calendar time){
		StringBuilder sb = new StringBuilder();
		sb.append(time.get(Calendar.YEAR));
		sb.append("/");
		sb.append(time.get(Calendar.MONTH));
		sb.append("/");
		sb.append(time.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}
	public Calendar convert(String s){
		Calendar time = new GregorianCalendar();
		String[] e = s.split("/");
		time.set(Calendar.YEAR, Integer.valueOf(e[0]));
		time.set(Calendar.MONTH,Integer.valueOf(e[1]));
		time.set(Calendar.DAY_OF_MONTH,Integer.valueOf(e[2]));
		return time;
	}
}
