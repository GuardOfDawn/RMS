package newproject.service.dao;

import java.util.List;

import newproject.model.Project;
import newproject.model.User;

public interface ProjectDao {
	
	public boolean insert(Project project);
	
	public boolean remove(String pid);
	
	public boolean modify(Project project);
	
	public List<Project> findAll();
	
	public List<User> retrieveUsers(String pid);
}
