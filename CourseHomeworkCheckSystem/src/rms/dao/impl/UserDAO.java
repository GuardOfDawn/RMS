package rms.dao.impl;

import java.util.List;

import rms.model.User;

public interface UserDAO {
	
	public boolean add(User user);
	
	public boolean remove(String userID);
	
	public boolean modify(User user);
	
	public User find(String userID);
	
	public List<User> findAll();
	
}
