package edu.nju.courseHomeworkCheck.factory;

import edu.nju.courseHomeworkCheck.dao.HomeworkDao;
import edu.nju.courseHomeworkCheck.dao.StudentDao;
import edu.nju.courseHomeworkCheck.dao.impl.HomeworkDaoImpl;
import edu.nju.courseHomeworkCheck.dao.impl.StudentDaoImpl;

public class DaoFactory {
	
	public static HomeworkDao getHomeworkDao(){
		return HomeworkDaoImpl.getInstance();
	}
	
	public static StudentDao getStudentDao(){
		return StudentDaoImpl.getInstance();
	}
	
}
