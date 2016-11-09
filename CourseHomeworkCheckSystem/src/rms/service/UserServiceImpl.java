package rms.service;

import java.util.List;

import rms.common.Role;
import rms.dao.impl.UserDAO;
import rms.dao.impl.UserDAOImpl;
import rms.model.User;

public class UserServiceImpl implements UserService{
	
	private UserDAO userDao;
	
	public UserServiceImpl(){
		this.userDao = new UserDAOImpl();
	}

	@Override
	public Role login(String uid, String password) {
		User userInfo = this.userDao.find(uid);
		boolean state = userInfo.getPassword().equals(password);
		if(!state){
			return Role.Failure;
		} 
		else {
			return userInfo.getRole();
		}
	}

	@Override
	public boolean addUser(User user) {
		boolean state = this.userDao.add(user);
		return state;
	}

	@Override
	public boolean deleteUser(String uid) {
		boolean state = this.userDao.remove(uid);
		return state;
	}

	@Override
	public boolean modifyUser(User user) {
		boolean state = this.userDao.modify(user);
		return state;
	}

	@Override
	public List<User> retrieveUsers() {
		List<User> result = this.userDao.findAll();
		return result;
	}

}
