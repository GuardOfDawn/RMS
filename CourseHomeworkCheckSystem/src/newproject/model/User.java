package newproject.model;

import java.io.Serializable;

import rms.common.Role;

public class User implements Serializable{
	
	private String userID;
	
	private String password;
	
	private Role role;
	
	private String userName;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
