package newproject.model;

import java.io.Serializable;

public class RiskItem implements Serializable{
	
	private String riskId;
	
	private String title;
	
	private String description;
	

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
