package edu.nju.courseHomeworkCheck.dao;

import java.util.List;

import edu.nju.courseHomeworkCheck.models.Course;

public interface CourseDao {

	public void save(Course course);
	
	public List find(String column,String value);
	
	public List find(String name);
	
	public void updateById(Course course);
	
	public List find();
	
	public List findType();
}
