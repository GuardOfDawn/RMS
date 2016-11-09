package rms.model;

import java.util.List;

import rms.common.EffectLevel;
import rms.common.Possibility;

public class RiskItem {
	
	private String riskId;
	
	private Possibility possibility;
	
	private EffectLevel effect;
	
	private String trigger;
	
	private String commiterId;
	
	private String followerId;
	
	private List<StateItem> list;

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public Possibility getPossibility() {
		return possibility;
	}

	public void setPossibility(Possibility possibility) {
		this.possibility = possibility;
	}

	public EffectLevel getEffect() {
		return effect;
	}

	public void setEffect(EffectLevel effect) {
		this.effect = effect;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getCommiterId() {
		return commiterId;
	}

	public void setCommiterId(String commiterId) {
		this.commiterId = commiterId;
	}

	public String getFollowerId() {
		return followerId;
	}

	public void setFollowerId(String followerId) {
		this.followerId = followerId;
	}

	public List<StateItem> getList() {
		return list;
	}

	public void setList(List<StateItem> list) {
		this.list = list;
	}

}
