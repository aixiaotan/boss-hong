package com.boss.common.constant;

/** 
 * 返回的提示信息
 * @author Yao 
 * @create 2018年7月11日  
 */
public class ReturnMsg {

	public static final String SMS_SEND_FAIL = "短信发送失败，请稍后重试";
	public static final String SMS_SEND_FAIL_EXIST = "验证码已发送，请勿重复获取";
	public static final String SMS_SEND_FAIL_MORE = "该手机号24小时以内获取验证码次数过多。";
	
	public static final String PICTURE_FAIL_MORE = "图片验证码错误";
	public static final String SMS_CHECK_FAIL_WRONG = "手机验证码错误";
	public static final String SMS_CHECK_FAIL_EXPIRE = "验证码已过期，请重新获取";
	
	public static final String SAVE_OK = "保存成功";
	public static final String SAVE_ERROR = "保存失败";
	
	public static final String UPDATE_OK = "修改成功";
	public static final String UPDATE_ERROR = "修改失败";
	
	public static final String SELECT_OK = "查询成功";
	public static final String SELECT_ERROR = "查询失败";
	
	public static final String DELETE_OK = "删除成功";
	public static final String DELETE_ERROR = "删除失败";
	
//	public static final String TRANSPOND_OK = "转发成功";
	public static final String TRANSPOND_ERROR = "转发失败";
	
	public static final String SEND_CARD_MORE_20 = "今天投递次数已经超过20次。";
	
	public static final String FAV_HAS_SAME = "已经存在相同收藏！";
	public static final String FAV_HAS = "TA已经收藏了您的名片！";
	public static final String FAV_SEND_CARD = "您已经投递过名片给TA";
	
	public static final String USER_INFO_NULL = "没有查到用户信息！";
}
