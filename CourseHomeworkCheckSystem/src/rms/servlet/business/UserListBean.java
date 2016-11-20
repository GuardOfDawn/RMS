package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

public class UserListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SimpleUser> userList;

	public List<SimpleUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SimpleUser> userList) {
		this.userList = userList;
	}
	
	public int getSize(){
		return userList.size();
	}
	
	public SimpleUser getUser(int index){
		return userList.get(index);
	}

}
