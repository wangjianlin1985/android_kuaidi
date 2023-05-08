package com.chengxusheji.test;

import java.util.Calendar;
import java.util.Date;

public class TestUtil {
	public static String getDateString() {
		Calendar c = Calendar.getInstance(); 
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH) + 1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 

		return year + "-" + month + "-" + date + " " +hour + ":" +minute + ":" + second; 

		
	}
}
