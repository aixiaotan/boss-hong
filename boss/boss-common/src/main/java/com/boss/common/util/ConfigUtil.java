package com.boss.common.util;

import java.util.Map;

import org.mission.common.utils.PropertiesUtils;

public class ConfigUtil {

	private static Map<String, String> config = PropertiesUtils.load("config");;

	public static String getValue(String key) {
		if (config != null) {
			return config.get(key);
		}
		return null;
	}

	/**
	 * 是否测试模式
	 */
	public static boolean isTest() {
		return "1".equals(getValue("istest"));
	}

	/**
	 * 附件服务器保存地址
	 */
	public static String getUploadFileAddr() {
		return getValue("upload_file_addr");
	}

}
