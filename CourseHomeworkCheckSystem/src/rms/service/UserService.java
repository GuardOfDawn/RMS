package rms.service;

import java.util.List;

import rms.common.Role;
import rms.model.User;

public interface UserService {

	public Role login(String uid,String password);
	
	public boolean addUser(User user);
	
	public boolean deleteUser(String uid);
	
	public boolean modifyUser(User user);
	
	public List<User> retrieveUsers();
	
}
