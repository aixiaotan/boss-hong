package com.boss.common.util;

public class StringUtils {

	
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}
