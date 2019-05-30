package com.lin.stock.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Chen Lin
 * @date 2019-05-28
 */

public class DateUtil {
	
	public static String getLastDateOfMonth(int year, int month) {
		Calendar ca = getFirstDateOfMonth(year, month);
		return returnLastDateOfMonth(ca);
	}
	
	private static String parseCalendar2String(Calendar ca) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(ca.getTime());
	}
	
	public static Calendar getFirstDateOfMonth(int year, int month) {
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, 1);
		return ca;
	}
	
	private static String returnLastDateOfMonth(Calendar calendar) {

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return parseCalendar2String(calendar);
	}


	public static String getPrevDate(int year, int month, int date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, date);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return parseCalendar2String(ca);
	}

	public static String getNextDate(int year, int month, int date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, date);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		return parseCalendar2String(ca);
	}
	
}
