package org.nico.mocker.utils;

import java.sql.Date;

public class DateUtils {

	public final static long ONE_DAY = 1000 * 60 * 60 * 24L;
	
	public static Date randomDate(long time) {
		return new Date(System.currentTimeMillis() + time);
	}
}
