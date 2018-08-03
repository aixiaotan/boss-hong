package com.boss.common.constant;

/** 
 * redis前缀 
 * @author Yao 
 * @create 2018年7月11日  
 */
public class RedisConst {

	//分隔符
	public static final String SEPARATOR = ":";
	
	//绑定手机短信验证码
	public static final String BIND_PHONE = "yyd:bindphone:";
	/**手机号发送验证码次数*/
	public static final String PHONE_SMS_COUNT = "wxa:phone:smsCount:";
	
	public static final String WECHAT_AUTH_COUNT = "wxa:phone:authCount:";
	
	public static final String PHONE_AUTH_CODE = "wxa:phone:authCode:";
	
	public static final String OPENID_KEY = "wxa:openId:";
	/**城市编码**/
	public static final String CITY_CODE = "wxa:city:code:";
	
	public static final String SYS_DIST_DATA = "wxa:sys:dist:";
	
}
