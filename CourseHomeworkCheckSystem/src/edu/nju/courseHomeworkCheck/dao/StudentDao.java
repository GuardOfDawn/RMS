package edu.nju.courseHomeworkCheck.dao;

import java.util.List;

public interface StudentDao {
	
	public boolean checkLogin(String student,String password);
	
	public List findById(String studentid);
	
}
