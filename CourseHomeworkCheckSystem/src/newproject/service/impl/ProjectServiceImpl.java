package newproject.service.impl;

import java.util.List;

import newproject.ProjectService;
import newproject.model.Project;
import newproject.model.User;
import newproject.service.dao.ProjectDao;
import newproject.service.dao.impl.ProjectDaoImpl;

public class ProjectServiceImpl implements ProjectService{
	
	private ProjectDao projectDao;
	
	public ProjectServiceImpl(){
		this.projectDao = new ProjectDaoImpl();
	}

	@Override
	public boolean addProject(Project project) {
		boolean flag = this.projectDao.insert(project);
		return flag;
	}

	@Override
	public boolean deleteProject(String pid) {
		boolean flag = this.projectDao.remove(pid);
		return flag;
	}

	@Override
	public boolean modifyProject(Project project) {
		boolean flag = this.projectDao.modify(project);
		return flag;
	}

	@Override
	public List<Project> retrieveProjects() {
		List<Project> list = this.projectDao.findAll();
		return list;
	}

	@Override
	public List<User> retrieveUsersInProject(String projectId) {
		List<User> list = this.projectDao.retrieveUsers(projectId);
		return list;
	}

}
