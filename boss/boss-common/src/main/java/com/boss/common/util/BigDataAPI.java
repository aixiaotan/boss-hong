package com.boss.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.mission.common.utils.PropertiesUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.touna.common.utils.ConfigUtils;

public class BigDataAPI {

//	private static final Logger logger = LoggerFactory.getLogger(BigDataAPI.class);

	private static Map<String, String> config = PropertiesUtils.load("config.properties");

	private static String verificationCode = config.get("bigdate.appkey");

	/**
	 * json GET
	 * @param url 
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 */
	public static String doJsonPostHttpWithHeader(String url, Map<String, Object> params) {
		long startTime = System.currentTimeMillis();
		Map<String, String> headers = new HashMap<>();
		headers.put("content-timestamp", String.valueOf(startTime));
		headers.put("content-md5", BigDataUtil.getSign(JSON.toJSONString(params), String.valueOf(startTime), verificationCode));
		
		// 添加系统标识、产品类型、关联ID
    	headers.put("sysName", ConfigUtils.getApplicationId());
    	headers.put("productType", ConfigUtils.getProductType());
    	headers.put("trace-id", getTraceId());
		return HttpClientUtil.doJsonPostHttpWithHeader(url, params, headers);
	}
	
	protected static String getTraceId() {
		/*String uuid = TracerContextUtil.getTraceId();
		if (uuid == null) {
			uuid = System.currentTimeMillis() + "";
		}*///TODO 
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
}
