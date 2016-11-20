package newproject.service.impl;

import java.util.List;

import newproject.RAService;
import newproject.model.RA;
import newproject.service.dao.RADao;

public class RAServiceImpl implements RAService{
	private RADao dao;
	
	@Override
	public boolean addRA(RA ra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRA(String raId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyRA(RA ra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RA> retrieveRAForProject(String projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RA> retrieveRA(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
