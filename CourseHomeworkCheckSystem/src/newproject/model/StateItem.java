package newproject.model;

import java.io.Serializable;
import java.util.Calendar;

import rms.common.RiskState;

public class StateItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String stateId;
	
	private String riskId;
	
	private String projectId;
	
	private String description;
	
	private RiskState state;
	
	private String possibility;
	
	private String effectlevel;
	
	private String threshold;
	
	private String comitter;
	
	private String follower;
	
	private Calendar time;

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
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

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}
	
}
