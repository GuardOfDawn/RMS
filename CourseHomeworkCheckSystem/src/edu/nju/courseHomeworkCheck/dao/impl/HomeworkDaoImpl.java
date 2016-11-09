package edu.nju.courseHomeworkCheck.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.nju.courseHomeworkCheck.dao.DaoHelper;
import edu.nju.courseHomeworkCheck.dao.HomeworkDao;
import edu.nju.courseHomeworkCheck.models.Course;
import edu.nju.courseHomeworkCheck.models.Homework;

public class HomeworkDaoImpl implements HomeworkDao{

	private static HomeworkDaoImpl homeworkDao = new HomeworkDaoImpl();
	private static DaoHelper daoHelper=DaoHelperImpl.getBaseDaoInstance();
	
	private HomeworkDaoImpl(){}
	
	public static HomeworkDaoImpl getInstance(){
		return homeworkDao;
	}

	public List findByStudent(String studentid) {
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("select hw.homeworkid,"
					+ "hw.courseid,c.courseName,c.courseTerm,"
					+ "hw.homeworktitle,hw.homeworkContent,hw.dueTime "
					+ "from homework hw,course c,courseselection cs "
					+ "WHERE hw.courseid=c.courseid "
					+ "AND hw.courseid=cs.courseid "
					+ "AND cs.studentid=?");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				homework.setStudentid(studentid);
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setDueTime(result.getString(7));
				list.add(homework);
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
	
	public List findUploadConditionByStudent(String studentid){
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("SELECT t.homeworkid,"
					+ "t.courseid,t.courseName,t.courseTerm,"
					+ "t.homeworktitle,t.homeworkContent,t.dueTime,hu.uploadTime "
					+ "FROM "
						+ "(SELECT cs.studentid,h.homeworkid,"
						+ "c.courseid,c.courseName,c.courseTerm,"
						+ "h.homeworktitle,h.homeworkContent,h.dueTime "
						+ "FROM homework h,course c,courseselection cs "
						+ "WHERE cs.studentid=? "
						+ "AND h.courseid=c.courseid "
						+ "AND cs.courseid=c.courseid) t "
					+ "LEFT JOIN homeworkupload hu "
					+ "ON t.studentid=hu.studentid AND t.homeworkid=hu.homeworkid");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				homework.setStudentid(studentid);
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setDueTime(result.getString(7));
				homework.setUploadTime(result.getString(8));
				list.add(homework);
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

	public List findUnUploadByStudent(String studentid){
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("SELECT t.homeworkid,"
					+ "t.courseid,t.courseName,t.courseTerm,"
					+ "t.homeworktitle,t.homeworkContent,t.dueTime,hu.uploadTime "
					+ "FROM "
						+ "(SELECT cs.studentid,h.homeworkid,"
						+ "c.courseid,c.courseName,c.courseTerm,"
						+ "h.homeworktitle,h.homeworkContent,h.dueTime "
						+ "FROM homework h,course c,courseselection cs "
						+ "WHERE cs.studentid=? "
						+ "AND h.courseid=c.courseid "
						+ "AND cs.courseid=c.courseid) t "
					+ "LEFT JOIN homeworkupload hu "
					+ "ON t.studentid=hu.studentid AND t.homeworkid=hu.homeworkid "
					+ "WHERE t.dueTime>CURRENT_TIME() "
					+ "AND ISNULL(hu.uploadTime)");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				homework.setStudentid(studentid);
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setDueTime(result.getString(7));
				homework.setUploadTime(result.getString(8));
				list.add(homework);
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
	
	public List findGradeByStudent(String studentid){
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("SELECT h.homeworkid,"
					+ "c.courseid,c.courseName,c.courseTerm,"
					+ "h.homeworktitle,h.homeworkContent,hg.grade "
					+ "FROM homeworkgrade hg,homework h,course c "
					+ "WHERE hg.studentid=? "
					+ "AND hg.homeworkid=h.homeworkid "
					+ "AND h.courseid=c.courseid");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				homework.setStudentid(studentid);
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setGrade(result.getInt(7));
				list.add(homework);
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

	public List findFailedGradeByStudent(String studentid){
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("SELECT h.homeworkid,"
					+ "c.courseid,c.courseName,c.courseTerm,"
					+ "h.homeworktitle,h.homeworkContent,hg.grade "
					+ "FROM homeworkgrade hg,homework h,course c "
					+ "WHERE hg.studentid=? "
					+ "AND hg.homeworkid=h.homeworkid "
					+ "AND h.courseid=c.courseid "
					+ "AND hg.grade<60");
			stmt.setString(1, studentid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				homework.setStudentid(studentid);
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setGrade(result.getInt(7));
				list.add(homework);
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
	
	public List findByCourse(int courseid){
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("select hw.homeworkid,"
					+ "hw.courseid,c.courseName,c.courseTerm,"
					+ "hw.homeworktitle,hw.homeworkContent,hw.dueTime "
					+ "from homework hw,course c "
					+ "WHERE hw.courseid=c.courseid "
					+ "AND hw.courseid=?");
			stmt.setInt(1, courseid);
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setDueTime(result.getString(7));
				list.add(homework);
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

	public List find() {
		Connection con=daoHelper.getConnection();
		PreparedStatement stmt=null;
		ResultSet result=null;
		ArrayList<Homework> list=new ArrayList<Homework>();
		try 
		{
			stmt=con.prepareStatement("select hw.homeworkid,"
					+ "hw.courseid,c.courseName,c.courseTerm,"
					+ "hw.homeworktitle,hw.homeworkContent,hw.dueTime "
					+ "from homework hw,course c "
					+ "WHERE hw.courseid=c.courseid");
			result=stmt.executeQuery();
			while(result.next())
			{
				Homework homework = new Homework();
				homework.setHomeworkid(result.getInt(1));
				Course course = new Course();
				course.setCourseid(result.getInt(2));
				course.setCourseName(result.getString(3));
				course.setCourseTerm(result.getString(4));
				homework.setCourse(course);
				homework.setHomeworkTitle(result.getString(5));
				homework.setHomeworkContent(result.getString(6));
				homework.setDueTime(result.getString(7));
				list.add(homework);
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
