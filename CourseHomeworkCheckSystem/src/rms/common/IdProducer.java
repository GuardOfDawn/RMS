package rms.common;

import java.util.Calendar;

public class IdProducer {
	
	public static String produceRiskId(){
		StringBuffer riskId = new StringBuffer("r");
		Calendar c = Calendar.getInstance();
		riskId.append(c.get(Calendar.YEAR));
		int month = c.get(Calendar.MONTH) + 1;
		if(month<10){
			riskId.append("0");
		}
		riskId.append(month);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(day<10){
			riskId.append("0");
		}
		riskId.append(day);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if(hour<10){
			riskId.append("0");
		}
		riskId.append(hour);
		int minute = c.get(Calendar.MINUTE);
		if(minute<10){
			riskId.append("0");
		}
		riskId.append(minute);
		int second = c.get(Calendar.SECOND);
		if(second<10){
			riskId.append("0");
		}
		riskId.append(second);
		return riskId.toString();
	}
	
	public static String produceStateId(){
		StringBuffer riskId = new StringBuffer("s");
		Calendar c = Calendar.getInstance();
		riskId.append(c.get(Calendar.YEAR));
		int month = c.get(Calendar.MONTH) + 1;
		if(month<10){
			riskId.append("0");
		}
		riskId.append(month);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(day<10){
			riskId.append("0");
		}
		riskId.append(day);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if(hour<10){
			riskId.append("0");
		}
		riskId.append(hour);
		int minute = c.get(Calendar.MINUTE);
		if(minute<10){
			riskId.append("0");
		}
		riskId.append(minute);
		int second = c.get(Calendar.SECOND);
		if(second<10){
			riskId.append("0");
		}
		riskId.append(second);
		return riskId.toString();
	}
	
	public static String produceRAId(){
		StringBuffer riskId = new StringBuffer("ra");
		Calendar c = Calendar.getInstance();
		riskId.append(c.get(Calendar.YEAR));
		int month = c.get(Calendar.MONTH) + 1;
		if(month<10){
			riskId.append("0");
		}
		riskId.append(month);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if(day<10){
			riskId.append("0");
		}
		riskId.append(day);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if(hour<10){
			riskId.append("0");
		}
		riskId.append(hour);
		int minute = c.get(Calendar.MINUTE);
		if(minute<10){
			riskId.append("0");
		}
		riskId.append(minute);
		int second = c.get(Calendar.SECOND);
		if(second<10){
			riskId.append("0");
		}
		riskId.append(second);
		return riskId.toString();
	}
	
	public static String produceUserId(){
		
		return "";
	}
	
	public static void main(String[] args) {
		produceRiskId();
	}

}
