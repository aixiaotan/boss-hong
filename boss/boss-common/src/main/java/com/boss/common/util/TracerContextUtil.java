//package com.boss.common.util;
//
//import com.alibaba.dubbo.common.utils.NetUtils;
//import org.apache.logging.log4j.ThreadContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//
//public class TracerContextUtil {
//	private static final Logger logger = LoggerFactory.getLogger(TracerContextUtil.class);
//
//	private static boolean isLog4j2 = false;
//	static String localIp;
//
//	public static void attachTraceId(String traceId) {
//		if (traceId == null) {
//			logger.error("traceId can not be null");
//			return;
//		}
//
//		mdcPut("trace_id", traceId);
//		mdcPut("local_ip", localIp);
//	}
//
//	private static void mdcPut(String key, String val) {
//		if (isLog4j2)
//			ThreadContext.put(key, val);
//		else
//			MDC.put(key, val);
//	}
//
//	private static void mdcRemove(String key) {
//		if (isLog4j2)
//			ThreadContext.remove(key);
//		else
//			MDC.remove(key);
//	}
//
//	private static String mdcGet(String key) {
//		if (isLog4j2) {
//			return ThreadContext.get(key);
//		}
//		return MDC.get(key);
//	}
//
//	public static void removeTraceId() {
//		mdcRemove("trace_id");
//	}
//
//	public static String getTraceId() {
//		return mdcGet("trace_id");
//	}
//
//	public static void main(String[] args) {
//		attachTraceId("xx");
//	}
//
//	static {
//		String loggingFw = System.getProperty("logging.framework");
//		if ((loggingFw != null) && ("log4j2".equalsIgnoreCase(loggingFw.trim()))) {
//			isLog4j2 = true;
//		}
//
//		localIp = "";
//		try {
//			localIp = NetUtils.getLocalHost();
//		} catch (Exception e) {
//			logger.error("fetch local ip error", e);
//		}
//	}
//
//}