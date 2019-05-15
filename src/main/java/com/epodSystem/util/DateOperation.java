package com.epodSystem.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateOperation {
	public static boolean checkDateWihToday(LocalDate date) {
		if (date.isAfter(LocalDate.now()))
			return true;
		return false;

	}

	/**
	 * convert string to localdate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate convertStringToLocalDate(String date) {
		return LocalDate.parse(date);

	}

	/**
	 * convert string to local date time
	 * 
	 * @param dateTimeString
	 * @return
	 */
	public static LocalDateTime convertStringLocalDateTime(String dateTimeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		return dateTime;
	}
	/**
	 * convert util date to local date time
	 * @param date
	 * @return
	 */
	public static LocalDateTime dateToLocalDate(Date date) {
		LocalDateTime localDateTime = null;
		if (null != date)
			localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return localDateTime;
	}

	/**
	 * sql timestamp to LocalDateTime
	 * 
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime sqltimestampToLocalDateTime(Timestamp timestamp) {
		if (null != timestamp)
			return timestamp.toLocalDateTime();
		return null;
	}
	
	public static Timestamp localDateTimeToSqlTimeStamp(LocalDateTime localDateTime) {
		if (null != localDateTime)
			return java.sql.Timestamp.valueOf(localDateTime);
		return null;
	}

}
