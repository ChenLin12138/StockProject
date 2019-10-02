package com.lin.stock.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Chen Lin
 * @date 2019-05-28
 */

public class DateUtil {
	
	private static int YEAR_BEGIN_POSITION = 0;
	private static int MONTH_BEGIN_POSITION = 4;
	private static int DATE_BEGIN_POSITION = 6;
	
	
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


	//获取前一天的日期
	public static String getPrevDate(int year, int month, int date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, date);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return parseCalendar2String(ca);
	}
	
	//获取前一天的日期
	public static String getPrevDate(String date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(getYearFromString(date), getMonthFromString(date), getDateFromString(date));
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return parseCalendar2String(ca);
	}

	public static String getNextDate(int year, int month, int date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, date);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		return parseCalendar2String(ca);
	}
	
	public static String getNextDate(String date) {
		
		Calendar ca = Calendar.getInstance();
		ca.set(getYearFromString(date), getMonthFromString(date), getDateFromString(date));
		ca.add(Calendar.DAY_OF_MONTH, 1);
		return parseCalendar2String(ca);
	}
	
	private static Integer getYearFromString(String date) {
		return Integer.parseInt(date.substring(YEAR_BEGIN_POSITION,MONTH_BEGIN_POSITION));
	}
	
	private static Integer getMonthFromString(String date) {
		return Integer.parseInt(date.substring(MONTH_BEGIN_POSITION, DATE_BEGIN_POSITION)) - 1;
	}
	
	private static Integer getDateFromString(String date) {
		return Integer.parseInt(date.substring(DATE_BEGIN_POSITION));
	}
	
	
	
}
