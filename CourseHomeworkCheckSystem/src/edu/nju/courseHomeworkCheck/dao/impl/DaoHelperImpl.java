package edu.nju.courseHomeworkCheck.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.nju.courseHomeworkCheck.dao.DaoHelper;

public class DaoHelperImpl implements DaoHelper{
	private static DaoHelperImpl baseDao = new DaoHelperImpl();
	
	private InitialContext jndiContext = null;
	private Connection connection = null;
	private DataSource datasource = null;
	
	private DaoHelperImpl(){
		Properties properties = new Properties();
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
		"org.apache.naming.java.javaURLContextFactory");

		try {
			jndiContext = new InitialContext(properties);
			datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/studentinfo");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("got context");
		System.out.println("About to get ds---DaoHelper");
	}
	
	public static DaoHelperImpl getBaseDaoInstance(){
		return baseDao;
	}
	
	public Connection getConnection() {
		try {
			connection = datasource.getConnection();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection con) {
		if(con!=null)
		{
			try 
			{
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closePreparedStatement(PreparedStatement stmt) {
		if(stmt!=null)
		{
			try
			{
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeResult(ResultSet result) {
		if(result!=null)
		{
			try
			{
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
