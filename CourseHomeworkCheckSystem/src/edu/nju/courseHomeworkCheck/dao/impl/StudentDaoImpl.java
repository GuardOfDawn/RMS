package edu.nju.courseHomeworkCheck.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.nju.courseHomeworkCheck.dao.DaoHelper;
import edu.nju.courseHomeworkCheck.dao.StudentDao;
import edu.nju.courseHomeworkCheck.models.Student;

public class StudentDaoImpl implements StudentDao{

	private static StudentDaoImpl studentDao = new StudentDaoImpl();
	private static DaoHelper daoHelper=DaoHelperImpl.getBaseDaoInstance();
	
	private StudentDaoImpl(){}
	
	public static StudentDaoImpl getInstance(){
		return studentDao;
	}
	
	public boolean checkLogin(String student,String password){
		boolean res = false;
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		try 
		{
			stmt=con.prepareStatement("SELECT s.studentid "
					+ "FROM student s "
					+ "WHERE (s.studentid=? OR s.nickname=?) "
					+ "AND s.`password`=?");
			stmt.setString(1, student);
			stmt.setString(2, student);
			stmt.setString(3, password);
			result=stmt.executeQuery();
			while(result.next())
			{
				res = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			daoHelper.closeConnection(con);
			daoHelper.closePreparedStatement(stmt);
			daoHelper.closeResult(result);
		}
		return res;
	}
	
	public List findById(String studentid) {
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Student> list = new ArrayList<Student>();
		try 
		{
			stmt=con.prepareStatement("SELECT s.nickname,s.age,s.gender "
					+ "FROM student s "
					+ "WHERE s.studentid=?");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Student s = new Student();
				s.setStudentid(studentid);
				s.setNickname(result.getString(1));
				s.setAge(result.getInt(2));
				s.setGender(result.getInt(3));
				list.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			daoHelper.closeConnection(con);
			daoHelper.closePreparedStatement(stmt);
			daoHelper.closeResult(result);
		}
		return list;
	}

}
