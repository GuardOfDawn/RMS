package newproject.service.dao;

import java.util.List;

import newproject.model.User;

public interface UserDao {
	
	public boolean insert(User user);
	
	public boolean remove(String id);
	
	public boolean modify(User user);
	
	public User find(String id);
	
	public List<User> retrieveUsers();
}
