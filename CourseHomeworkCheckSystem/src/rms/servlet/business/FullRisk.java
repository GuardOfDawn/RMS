package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import rms.common.RiskState;

public class FullRisk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String riskId;
	
	private String projectId;
	
	private String raId;
	
	private String description;
	
	private RiskState state;
	
	private String possibility;
	
	private String effectlevel;
	
	private String threshold;
	
	private List<RiskStateItem> riskStateList;

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RiskState getState() {
		return state;
	}

	public void setState(RiskState state) {
		this.state = state;
	}

	public String getPossibility() {
		return possibility;
	}

	public void setPossibility(String possibility) {
		this.possibility = possibility;
	}

	public String getEffectlevel() {
		return effectlevel;
	}

	public void setEffectlevel(String effectlevel) {
		this.effectlevel = effectlevel;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public List<RiskStateItem> getRiskStateList() {
		return riskStateList;
	}

	public void setRiskStateList(List<RiskStateItem> riskStateList) {
		this.riskStateList = riskStateList;
	}

	public String getRaId() {
		return raId;
	}

	public void setRaId(String raId) {
		this.raId = raId;
	}
	
}
