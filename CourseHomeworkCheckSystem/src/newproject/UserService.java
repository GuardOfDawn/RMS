package newproject;

import java.util.List;

import newproject.model.User;

public interface UserService {
	
	public boolean login(String userId,String password);
	
	public User getUserInfo(String userId);
	
	public boolean addUser(User user);
	
	public boolean deleteUser(String uid);
	
	public boolean modifyUser(User user);
	
	public List<User> retrieveUsers();

}
