package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import newproject.model.Project;

public class ProjectListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Project> projectList;

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	
	public Project getProject(int index){
		return projectList.get(index);
	}
	
	public int getSize(){
		return projectList.size();
	}

}
