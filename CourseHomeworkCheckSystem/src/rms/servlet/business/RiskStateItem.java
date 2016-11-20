package rms.servlet.business;

import java.io.Serializable;

import rms.common.RiskState;

public class RiskStateItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String comitter;
	
	private String time;

	private RiskState state;
	
	private String description;

	public String getComitter() {
		return comitter;
	}

	public void setComitter(String comitter) {
		this.comitter = comitter;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public RiskState getState() {
		return state;
	}

	public void setState(RiskState state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
