package newproject.model;

import java.io.Serializable;
import java.util.List;

public class RA implements Serializable{

	private String raId;
	
	private String projectId;
	
	private String setter;
	
	private String description;
	
	private List<StateItem> riskList;
	
	private List<RiskItem> riskItemList;

	public String getRaId() {
		return raId;
	}

	public void setRaId(String raId) {
		this.raId = raId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSetter() {
		return setter;
	}

	public void setSetter(String setter) {
		this.setter = setter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StateItem> getRiskList() {
		return riskList;
	}

	public void setRiskList(List<StateItem> riskList) {
		this.riskList = riskList;
	}

	public List<RiskItem> getRiskItemList() {
		return riskItemList;
	}

	public void setRiskItemList(List<RiskItem> riskItemList) {
		this.riskItemList = riskItemList;
	}
	
}
