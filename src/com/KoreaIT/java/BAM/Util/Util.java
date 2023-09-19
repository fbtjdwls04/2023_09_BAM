package com.KoreaIT.java.BAM.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	/** 현재 날짜의 시간 리턴 */
	public static String getNow() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
		return date;
	}
}
