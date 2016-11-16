package newproject;

import java.util.List;

import newproject.model.Project;
import newproject.model.User;

public interface ProjectService {
	
	public boolean addProject(Project project);
	
	public boolean deleteProject(String pid);
	
	public boolean modifyProject(Project project);
	
	public List<Project> retrieveProjects();
	
	/**
	 * 查找某个项目里的人员
	 * @param projectId
	 * @return
	 */
	public List<User> retrieveUsersInProject(String projectId);

}
