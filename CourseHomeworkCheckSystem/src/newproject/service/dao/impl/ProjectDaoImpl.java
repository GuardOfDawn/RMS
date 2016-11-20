package newproject.service.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import newproject.model.Project;
import newproject.model.User;
import newproject.service.dao.ProjectDao;
import rms.common.Role;

public class ProjectDaoImpl implements ProjectDao{
	
	private DBTool db;
	
	public ProjectDaoImpl(){
		this.db = new DBTool();
	}

	@Override
	public boolean insert(Project project) {
		String sql = "insert into project value('"
				+project.getProjectId()+"','"+project.getProjectName()
				+"','"+project.getDescription()+"');";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	@Override
	public boolean remove(String pid) {
		String sql1 = "delete from project where pid='"+pid+"';";
		boolean flag = this.db.executeCUD(sql1);
		String sql2 = "delete from belongto where pid='"+pid+"';";
		flag = flag && this.db.executeCUD(sql2);
		return flag;
	}

	@Override
	public boolean modify(Project project) {
		String sql = "update project set pname='"+project.getProjectName()
		            +"',description='"+project.getDescription()+"' where pid='"
		            +project.getProjectId()+"';";
		boolean flag = this.db.executeCUD(sql);
		return flag;
	}

	@Override
	public List<Project> findAll() {
		String sql = "select pid,pname,description from project;";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<Project> list = new ArrayList<Project>();
		if(resultSet == null)
			return list;
		try {
			while(resultSet.next()){
				Project p = new Project();
				p.setProjectId(resultSet.getString(1));
				p.setProjectName(resultSet.getString(2));
				p.setDescription(resultSet.getString(3));
				list.add(p);
			}
			resultSet.close();
			DBTool.connectionList.get(0).close();
			DBTool.connectionList.remove(0);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	@Override
	public List<User> retrieveUsers(String pid) {
		String sql = "select uid,uname,role from user where uid in(select uid from `join` where pid='"+pid+"');";
		ResultSet resultSet = this.db.executeQuery(sql);
		List<User> list = new ArrayList<User>();
		if(resultSet == null)
			return list;
		try {
			while(resultSet.next()){
				User user = new User();
				user.setUserID(resultSet.getString(1));
				user.setUserName(resultSet.getString(2));
				user.setPassword(null);
				user.setRole(Role.valueOf(resultSet.getString(3)));
				list.add(user);
			}
			resultSet.close();
			DBTool.connectionList.get(0).close();
			DBTool.connectionList.remove(0);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

}
