package com.boss.hong.user.service;

import com.boss.common.dto.user.LoginUserDto;

/**
 * 用户相关接口服务
 * @author peihongwei
 *
 * 2018年7月6日
 */
public interface UserService {
	
	/**
	 * 根据微信ID获取用户主页页面信息
	 * @author peiHongWei
	 * @date 2018年7月12日 下午6:03:16
	 * @param wechatId
	 * @return
	 */
	public LoginUserDto getLoginUserWechatId (String wechatId);
	
	/**
	 * 根据微信ID获取用户主页页面信息
	 * @author peiHongWei
	 * @date 2018年7月12日 下午6:03:16
	 * @param wechatId
	 * @return
	 */
//	public LoginUserDto getLoginUserByWechatId (String wechatId);
	
	/**
	 * 保存访问小程序的用户微信信息
	 * @author peiHongWei
	 * @date 2018年7月17日 上午11:06:53
	 * @param loginUserDto
	 * @return
	 */
	public void insertLoginUser (LoginUserDto loginUserDto);

	/**
	 * 发送短信验证码
	 * @return 
	 */
	public String sendBindPhoneSms(String phone,String authCode,String wechatId);
	
	/**
	 * 根据主键获取用户信息
	 * @author peiHongWei
	 * @date 2018年7月27日 下午7:17:49
	 * @param id
	 * @return
	 */
	public LoginUserDto getLoginUserById(Integer id) ;
	
	/**
	 * 绑定手机号码
	 * @return 
	 */
	public String bindPhone(String openid,String phone, String smsCode);
	
	/***
	 * 根据WeChatID获取主键
	 * @author peiHongWei
	 * @date 2018年7月20日 下午6:19:55
	 * @param wechatId
	 * @return
	 */
	public Integer getLoginUserIdByWechatId(String wechatId);
	
	/***
	 * 根据accessToken获取用户信息缓存
	 * @param accessToken
	 * @return LoginUserDto 用户信息
	 */
	public LoginUserDto getUserCache(String openId);
	/***
	 * 根据openId获取用户信息缓存
	 * @param accessToken
	 * @return LoginUserDto 用户信息
	 */
//	public LoginUserDto getUserCacheByOpenId(String openId) ;
	
	/***
	 * 根据accessToken设置用户信息缓存
	 * @param accessToken
	 * @return 
	 */
	public void setUserCache(String openId);
	
	/***
	 * 刷新用户信息缓存，用户更新信息(比如绑定手机号)之后，可能需要立即刷新缓存中的内容
	 * @param loginUser
	 * @return 
	 */
	public void refreshUserCacheByWechatId(LoginUserDto loginUser);
	
	/***
	 * 刷新用户信息缓存，用户更新信息(比如绑定手机号)之后，可能需要立即刷新缓存中的内容
	 * @param loginUser
	 * @return 
	 */
	public void refreshUserCacheById(LoginUserDto loginUser);
	
}
