package com.boss.hong.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
//import org.mission.common.beans.ApiResult;
//import org.mission.common.utils.TimeUtils;
//import org.mission.mongodb.proxy.MongoProxy;
//import org.mission.mongodb.proxy.MongoProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boss.common.constant.Constants;
import com.boss.common.constant.RedisConst;
import com.boss.common.constant.ReturnMsg;
import com.boss.common.dto.shop.ShopDto;
import com.boss.common.dto.user.LoginUserDto;
import com.boss.common.util.StringUtils;
import com.boss.hong.base.service.BusinessService;
import com.boss.hong.base.service.CommonService;
import com.boss.hong.favorite.service.FavoriteService;
import com.boss.hong.shop.service.ShopService;
import com.boss.hong.user.entity.LoginUserDO;
import com.boss.hong.user.service.UserService;

/**
 * 用户相关接口实现
 * @author peihongwei
 *
 * 2018年7月6日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private BusinessService businessService;
	
	@Override
	public LoginUserDto getLoginUserWechatId(String wechatId) {
		logger.info("UserInfoServiceImpl getLoginUserWechatId param:{}",wechatId);
		LoginUserDto loginUserDto = this.getUserCache(wechatId);
		if(loginUserDto == null) {
			logger.info("UserInfoServiceImpl getLoginUserWechatId loginUserDto:{}",loginUserDto);
			return null;
		}
		Integer loginUserId = loginUserDto.getId();
		//查询是否有店铺
		ShopDto queryShopDto = shopService.getShop(wechatId);
		if(queryShopDto != null) {
			//查询店铺访问量
		    Integer shopVisitCount = shopService.countShopVisit(loginUserId);
		    /**
		     * 收藏类型，01-店铺，02-名片，03-大纲。
		     */
		    Integer favShopCount = favoriteService.countFav(loginUserId, "01", null);
		    Integer favCardCount  = favoriteService.countFav(loginUserId, "02", null);
		    Integer favCount = favoriteService.countFav(loginUserId, "03", null);
		    
		    Integer favTodayShopCount = favoriteService.countFav(loginUserId, "01", "1");
		    Integer favTodayCardCount  = favoriteService.countFav(loginUserId, "02", "1");
		    Integer favTodayCount = favoriteService.countFav(loginUserId, "03", "1");
		    
		    loginUserDto.setFavCardCount(favCardCount);
		    loginUserDto.setFavShopCount(favShopCount);
		    loginUserDto.setFavCount(favCount);
		    loginUserDto.setShopVisitCount(shopVisitCount);
		    loginUserDto.setFavTodayCardCount(favTodayCardCount);
		    loginUserDto.setFavTodayCount(favTodayCount);
		    loginUserDto.setFavTodayShopCount(favTodayShopCount);
		    loginUserDto.setShopId(queryShopDto.getId());
		}
		logger.info("UserInfoServiceImpl getLoginUserWechatId loginUserDto:"+JSONObject.toJSONString(loginUserDto));
		return loginUserDto;
	}
	
	/*@Override
	public LoginUserDto getLoginUserByWechatId(String wechatId) {
		return this.getUserCache(wechatId);
	}*/
	
	private LoginUserDto getLoginUser(String wechatId) {
		LoginUserDO loginUserDo = new LoginUserDO();
		loginUserDo.setWechatId(wechatId);
		loginUserDo = commonService.findOne(loginUserDo);
		if(loginUserDo != null) {
			LoginUserDto loginUserDto = new LoginUserDto();
			BeanUtils.copyProperties(loginUserDo, loginUserDto);
			refreshUserCacheByWechatId(loginUserDto);
			return loginUserDto;
		}
		return null;
	}
	
	@Override
	public LoginUserDto getLoginUserById(Integer id) {
		String userCacheKey = RedisConst.OPENID_KEY + id;
		LoginUserDto loginUserDto = new LoginUserDto();
		if(businessService.exists(userCacheKey)){
			businessService.expire(userCacheKey, Constants.EXPIRETIME_ONE_HOUR);
			loginUserDto = businessService.getObject(userCacheKey, LoginUserDto.class);
			return loginUserDto;
		}
		
		LoginUserDO loginUserDo = new LoginUserDO();
		loginUserDo.setId(id);
		loginUserDo = commonService.findOne(loginUserDo);
		if(loginUserDo != null) {
			BeanUtils.copyProperties(loginUserDo, loginUserDto);
			businessService.setRedisValue(userCacheKey, JSONObject.toJSONString(loginUserDto),Constants.EXPIRETIME_ONE_HOUR);
			return loginUserDto;
		}
		return null;
	}
	
	@Override
	public String sendBindPhoneSms(String phone,String authCode,String wechatId) {
		/*
		 * 1.首先判断该用户今天是否第一次发送短信验证码
		 * 		如果是，则不校验authCode，直接发送短信验证码，并将短信验证码放在Redis，时间1分钟
		 * 		如果不是第一次，则判断authCode是否为空，如果为空，则返回前端提示，获取图片验证码
		 * 									如果不为空，根据手机号码取出Redis中的authCode，并进行校验，如果通过，则发送验证码，并将短信验证码放在Redis，时间1分钟
		 * 																				如果不通过，则返回前端重新获取图片验证码
		 * 以上每一次，只要给该手机发送了短信验证码，都需要记录一次发送记录，每个手机号码每天最多发送10次。
		 */
		//判断今天该微信号是否发送过验证码
		String wechatAuthCount = RedisConst.WECHAT_AUTH_COUNT + wechatId;
		if(businessService.exists(wechatAuthCount)){
			if(StringUtils.isEmpty(authCode)) {
//				return apiResult.ok("1");//图片验证码为空
			}else {
				String phoneAuthCode = RedisConst.PHONE_AUTH_CODE + phone;
				String redisAuthCode = businessService.getRedisValue(phoneAuthCode);
				if(!authCode.equalsIgnoreCase(redisAuthCode)) {
//					apiResult.setStatus(401);//需要图片验证码
//					return apiResult.error(ReturnMsg.PICTURE_FAIL_MORE);//图片验证码未通过
				}
				//图片验证码校验通过，清除该手机号码对应的图片验证码。
				businessService.deleteRedisValue(phoneAuthCode);
			}
		}else {
			businessService.setRedisValue(wechatAuthCount, wechatId, Constants.EXPIRETIME_ONE_DAY );
		}
		// 校验短信验证码是否已发送
		if(businessService.exists(RedisConst.BIND_PHONE + phone)){
//			return apiResult.error(ReturnMsg.SMS_SEND_FAIL_EXIST);
		}
		// 校验发送条数，比如一个手机号一天最多发送10条
		String phoneKey = RedisConst.PHONE_SMS_COUNT + phone;
		String countStr = businessService.getRedisValue(phoneKey);
		Integer count = 0;
		if(StringUtils.isNotEmpty(countStr)) {
			count = Integer.parseInt(countStr);
			if(count > 9) {
//				return apiResult.error(ReturnMsg.SMS_SEND_FAIL_MORE);
			}
		}
		businessService.ttlValue(phoneKey, String.valueOf(++count), Constants.EXPIRETIME_ONE_DAY );

		String smsType = "bindPhone";
		String smsCode = RandomStringUtils.randomNumeric(6);
//        String content = SmsAPI.sendMsg(smsType, phone, smsCode);//TODO 发送手机验证码接口
//        logger.info("bindphone sms content:{}", content);
//        if (content == null) {
//        	return apiResult.error(ReturnMsg.SMS_SEND_FAIL);
//        }
        
        businessService.setRedisValue(RedisConst.BIND_PHONE + phone, smsCode, 60);
        //TODO 保存短信信息
//        MongoProxy mongo = MongoProxyFactory.getProxy();
//        mongo.insert(new SmsRecord(phone, smsType, content, TimeUtils.yyyyMMddHHmmss(new Date())));
//		return apiResult.ok();
        return null;
	}
	
	@Override
	public String bindPhone(String openid,String phone, String smsCode) {
		logger.info("userServiceImpl bindPhone openid:"+openid+",phone:"+phone+",smsCode"+smsCode);
		String apiResult = new String();
        String smsKey = RedisConst.BIND_PHONE + phone;
        if (!businessService.exists(smsKey)) {
//            return apiResult.error(ReturnMsg.SMS_CHECK_FAIL_EXPIRE);
        }
        String redisSmsCode = businessService.getRedisValue(smsKey);
        logger.info("userServiceImpl bindPhone redisSmsCode:"+redisSmsCode+",smsCode"+smsCode);
        if (!redisSmsCode.equals(smsCode)) {
//            return apiResult.error(ReturnMsg.SMS_CHECK_FAIL_WRONG);
        }
        
        //验证码已用，及时清除
        businessService.deleteRedisValue(smsKey);
        String userCacheKey = RedisConst.OPENID_KEY + openid;
        logger.info("绑定手机号之后，清除用户Redis缓存信息，key" + userCacheKey);
        businessService.deleteRedisValue(userCacheKey);
        
        LoginUserDO loginUser = new LoginUserDO();
        loginUser.setLoginPhoneNumber(phone);
        loginUser.setUpdatedTime(new Date());
        commonService.updateObjFields(loginUser, "loginPhoneNumber,updatedTime", "wechatId=?", openid);
        return null;
	}
	
	@Override
	public void insertLoginUser(LoginUserDto loginUserDto) {
		logger.info("UserInfoServiceImpl insertLoginUser param:{}",loginUserDto);
		LoginUserDO loginUserDOForQuery = new LoginUserDO();
		loginUserDOForQuery.setWechatId(loginUserDto.getWechatId());
		List<LoginUserDO> loginUserDOs = commonService.dynamicQuery(loginUserDOForQuery);
		logger.info("查询userInfo结果为："+loginUserDOs);
		LoginUserDO loginUserDO = null;
		if(CollectionUtils.isNotEmpty(loginUserDOs)) {//如果存在用户信息，直接将该用户信息返回。前段判断其有没有绑定手机号码
			loginUserDO = loginUserDOs.get(0);
			loginUserDO.setWechatUserInfo(loginUserDto.getWechatUserInfo());
			loginUserDO.setUpdatedTime(new Date());
			commonService.updateObjFields(loginUserDO, "wechatUserInfo,updatedTime", "wechatId = ?", loginUserDto.getWechatId());
			loginUserDto.setId(loginUserDO.getId());
			/*覆盖原对象*/
			BeanUtils.copyProperties(loginUserDO, loginUserDto);
			refreshUserCacheById(loginUserDto);
			refreshUserCacheByWechatId(loginUserDto);
			return ;
		}
		loginUserDO = new LoginUserDO();
		BeanUtils.copyProperties(loginUserDto, loginUserDO);
		loginUserDO.setCreatedTime(new Date());
		loginUserDO.setUpdatedTime(new Date());
		Integer count = commonService.insert(loginUserDO);
		logger.info("UserInfoServiceImpl insertLoginUser count:{}",count);
		if(count == 0) {
//			ReturnMsg.SAVE_ERROR;
		}else {
//			result.ok();
		}
		refreshUserCacheByWechatId(loginUserDto);
//		return result;
	}
	
	@Override
	public Integer getLoginUserIdByWechatId(String wechatId) {
		LoginUserDto loginUserDto = this.getUserCache(wechatId);
		if(null != loginUserDto) {
			return loginUserDto.getId();
		}
		return 0;
	}

	@Override
	public LoginUserDto getUserCache(String openid) {
		logger.info("getUserCache openid:{}",openid);
		String userCacheKey = RedisConst.OPENID_KEY + openid;
		if(businessService.exists(userCacheKey)){
			businessService.expire(userCacheKey, Constants.EXPIRETIME_ONE_HOUR);
			LoginUserDto loginUserDto = businessService.getObject(userCacheKey,LoginUserDto.class);
//			return apiResult.ok(loginUserDto);
		}
		
		LoginUserDto loginUser = getLoginUser(openid);
		if(null == loginUser){
//			return apiResult.error(ReturnMsg.USER_INFO_NULL);
		}
//		return apiResult.ok(loginUser);
		return loginUser;
	}

	@Override
	public void setUserCache(String openid) {
		String userCacheKey = RedisConst.OPENID_KEY + openid;
		if(businessService.exists(userCacheKey)){
			businessService.expire(userCacheKey, Constants.EXPIRETIME_ONE_HOUR);
			return ;
		}
		
		LoginUserDto loginUser = getLoginUser(openid);
		if(null == loginUser){
			//TODO 
		}
	}

	@Override
	public void refreshUserCacheByWechatId(LoginUserDto loginUser) {
		String userCacheKey = RedisConst.OPENID_KEY + loginUser.getWechatId();
		loginUser.setWechatId("");
		loginUser.setWechatUserInfo("");
		businessService.setRedisValue(userCacheKey,JSON.toJSONString(loginUser), Constants.EXPIRETIME_ONE_HOUR);
	}

	@Override
	public void refreshUserCacheById(LoginUserDto loginUser) {
		String userCacheKey = RedisConst.OPENID_KEY + loginUser.getId();
		businessService.setRedisValue(userCacheKey,JSON.toJSONString(loginUser), Constants.EXPIRETIME_ONE_HOUR);
	}

}
