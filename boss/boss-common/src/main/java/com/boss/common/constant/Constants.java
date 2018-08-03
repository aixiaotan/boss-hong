package com.boss.common.constant;
/**
 * 常量类
 * @author peihongwei
 *
 * 2018年7月6日
 */
public class Constants {

	private Constants() {
		
	}
	
	/**
	 * 访问计数类型，01-店铺，02-大纲
	 */
	public static final String VISIT_TYPE_SHOP = "01";
	public static final String VISIT_TYPE_OUTLINE = "02";
	/**
	 *  '收藏类型，01-店铺，02-名片，03-大纲。',
	 */
	public static final String FAVORITE_TYPE_SHOP = "01";
	public static final String FAVORITE_TYPE_CARD = "02";
	public static final String FAVORITE_TYPE_OUTLINE = "03";
	/**
	 * 是否投递给我的，01-投递给我的，02-我收藏的
	 */
	public static final String DELIVERED_TO_ME_01 = "01";
	public static final String DELIVERED_TO_ME_02 = "02";
	
	// 登录失败时，缓存失败记录的失效时间，单位：秒
	public static final int EXPIRETIME_ONE_HOUR = 1 * 60 * 60;
	
	public static final int EXPIRETIME_ONE_MINUTE = 1 * 60;
	
	public static final int EXPIRETIME_ONE_DAY = 24 * 60 * 60;
	
	/**获取获取微信小程序 session_key 和 openid的默认参数*/
	public static final String AUTHORIZATION_CODE = "authorization_code";
	
	public static final String ACCESSTOKEN_KEY = "accessToken";
	
}
