package rms.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
	
	public static Calendar stringToCalendar(String format,String dateString){
		SimpleDateFormat sdf= new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}
