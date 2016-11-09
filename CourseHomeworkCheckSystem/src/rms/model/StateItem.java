package rms.model;

import java.util.Calendar;

import rms.common.RiskState;

public class StateItem {

	private String stateId;
	
	private String riskId;
	
	private RiskState state;
	
	private String description;
	
	private Calendar start;
	
	private Calendar end;
	
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

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}
	
}
