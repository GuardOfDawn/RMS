package newproject.service.impl;

import java.util.List;

import newproject.RAService;
import newproject.model.RA;
import newproject.service.dao.RADao;
import newproject.service.dao.impl.RADaoImpl;

public class RAServiceImpl implements RAService{
	private RADao dao;
	
	public RAServiceImpl() {
		dao = new RADaoImpl();
	}
	@Override
	public boolean addRA(RA ra) {
		boolean flag = dao.insert(ra);
		return flag;
	}

	@Override
	public boolean deleteRA(String raId) {
		boolean flag = dao.remove(raId);
		return flag;
	}

	@Override
	public boolean modifyRA(RA ra) {
		boolean flag = dao.modify(ra);
		return flag;
	}

	@Override
	public List<RA> retrieveRAForProject(String projectId) {
		List<RA> list = dao.findByProject(projectId);
		return list;
	}

	@Override
	public List<RA> retrieveRA(String userId) {
		List<RA> list = dao.findById(userId);
		return list;
	}

}
