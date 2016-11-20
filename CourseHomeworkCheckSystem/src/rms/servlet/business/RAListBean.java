package rms.servlet.business;

import java.io.Serializable;
import java.util.List;

import newproject.model.RA;

public class RAListBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RA> raList;

	public List<RA> getRaList() {
		return raList;
	}

	public void setRaList(List<RA> raList) {
		this.raList = raList;
	}
	
	public RA getRA(int index){
		return raList.get(index);
	}
	
	public int getSize(){
		return raList.size();
	}

}
