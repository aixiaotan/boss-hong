package com.boss.common.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class WechatApiPropertiesUtil {

	@Autowired
	private static Environment env;
	
	private WechatApiPropertiesUtil() {
		
	}
	
	public static String getConfig(String key) {
		return env.getProperty(key);
	}
	
//	private static final Map<String, String> WECHAT_API_CONF = PropertiesUtils.load("wechat-api"); 
//	
//	public static String getConfig(String key) {
//		return WECHAT_API_CONF.get(key);
//	}
}
