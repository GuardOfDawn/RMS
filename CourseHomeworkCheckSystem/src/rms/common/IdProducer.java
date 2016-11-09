package rms.common;

import java.util.Calendar;

public class IdProducer {
	
	public static String produceRiskId(){
		StringBuffer riskId = new StringBuffer("r");
		Calendar c = Calendar.getInstance();
		riskId.append(c.get(Calendar.YEAR));
		int month = c.get(Calendar.MONTH) + 1;
		if(month<10){
			riskId.append(0);
		}
		riskId.append(month);
		riskId.append(c.get(Calendar.DAY_OF_MONTH));
		riskId.append(c.get(Calendar.HOUR_OF_DAY));
		riskId.append(c.get(Calendar.MINUTE));
		riskId.append(c.get(Calendar.SECOND));
		return riskId.toString();
	}
	
	public static String produceUserId(){
		
		return "";
	}

}
