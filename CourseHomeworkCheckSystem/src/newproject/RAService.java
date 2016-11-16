package newproject;

import java.util.List;

import newproject.model.RA;

public interface RAService {
	
	public boolean addRA(RA ra);
	
	public boolean deleteRA(String raId);
	
	public boolean modifyRA(RA ra);
	
	/**
	 * 查找某个项目的RA
	 * @param projectId
	 * @return
	 */
	public List<RA> retrieveRAForProject(String projectId);

	/**
	 * 查找某个质量管理员创建的RA
	 * @param userId
	 * @return
	 */
	public List<RA> retrieveRA(String userId);
	
}
