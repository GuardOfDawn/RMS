package newproject.service.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import newproject.service.dao.DaoHelper;
import newproject.service.dao.DaoHelperImpl;

public class DBTool {
	private DaoHelper daoHelper;
	public static List<Connection> connectionList = new ArrayList<Connection>();
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
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} 
		connectionList.add(connection);
//		try {
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return resultSet;
	}
	public String convert(Calendar time){
		StringBuilder sb = new StringBuilder();
		sb.append(time.get(Calendar.YEAR));
		sb.append("/");
		sb.append(time.get(Calendar.MONTH)+1);
		sb.append("/");
		sb.append(time.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}
	public Calendar convert(String s){
		Calendar time = new GregorianCalendar();
		String[] e = s.split("/");
		time.set(Calendar.YEAR, Integer.valueOf(e[0]));
		time.set(Calendar.MONTH,Integer.valueOf(e[1])-1);
		time.set(Calendar.DAY_OF_MONTH,Integer.valueOf(e[2]));
		return time;
	}
}
