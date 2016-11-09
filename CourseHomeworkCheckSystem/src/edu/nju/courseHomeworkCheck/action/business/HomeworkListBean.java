package edu.nju.courseHomeworkCheck.action.business;

import java.io.Serializable;
import java.util.List;

import edu.nju.courseHomeworkCheck.models.Homework;

public class HomeworkListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	private List homeworkList;

	@SuppressWarnings("rawtypes")
	public List getHomeworkList() {
		return homeworkList;
	}

	@SuppressWarnings("rawtypes")
	public void setHomeworkList(List homeworkList) {
		this.homeworkList = homeworkList;
	}
	
	public Homework getHomeworkList(int index){
		return (Homework) homeworkList.get(index);
	}
	
}
