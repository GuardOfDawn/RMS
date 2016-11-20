package newproject.service.impl;

import java.util.List;

import newproject.UserService;
import newproject.model.User;
import newproject.service.dao.UserDao;
import newproject.service.dao.impl.UserDaoImpl;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	public UserServiceImpl(){
		this.userDao = new UserDaoImpl();
	}

	@Override
	public boolean login(String userId, String password) {
		User user = this.userDao.find(userId);
		if(user == null)
			return false;
		String pw = user.getPassword();
		if(pw == null || !pw.equals(password))
			return false;
		else
			return true;
	}

	@Override
	public User getUserInfo(String userId) {
		User user = this.userDao.find(userId);
		return user;
	}

	@Override
	public boolean addUser(User user) {
		boolean flag = this.userDao.insert(user);
		return flag;
	}

	@Override
	public boolean deleteUser(String uid) {
		boolean flag = this.userDao.remove(uid);
		return flag;
	}

	@Override
	public boolean modifyUser(User user) {
		boolean flag = this.userDao.modify(user);
		return flag;
	}

	@Override
	public List<User> retrieveUsers() {
		List<User> list = this.userDao.retrieveUsers();
		return list;
	}

}
