package newproject.model;

import java.io.Serializable;

public class RiskCondition implements Serializable{
	
	private RiskItem risk;
	
	private int recognizedCount;
	
	private int problemTransformedCount;

	public RiskItem getRisk() {
		return risk;
	}

	public void setRisk(RiskItem risk) {
		this.risk = risk;
	}

	public int getRecognizedCount() {
		return recognizedCount;
	}

	public void setRecognizedCount(int recognizedCount) {
		this.recognizedCount = recognizedCount;
	}

	public int getProblemTransformedCount() {
		return problemTransformedCount;
	}

	public void setProblemTransformedCount(int problemTransformedCount) {
		this.problemTransformedCount = problemTransformedCount;
	}

}
