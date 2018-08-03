package com.boss.hong.base.web;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boss.common.annotation.BindPhone;
import com.boss.common.beans.ApiResult;
import com.boss.common.constant.Constants;
import com.boss.common.dto.user.LoginUserDto;
import com.boss.common.util.AesUtil;
import com.boss.common.util.CookieHelper;
import com.boss.common.util.UrlUtil;
import com.boss.common.util.WechatApiPropertiesUtil;
import com.boss.hong.base.service.BusinessService;
import com.boss.hong.user.service.UserService;
import com.boss.hong.wechat.web.Base64;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 微信小程序信息获取
 * 
 * @author zhi.chen
 */
@RestController
@RequestMapping(value = { "/wxAppletUserInfo" })
public class WXAppletUserInfoController {
	private static Logger log = LoggerFactory.getLogger(WXAppletUserInfoController.class);
	public static final String OPPENID_KEY = "wxa:openid:";
	@Autowired
	private UserService userService;
	@Autowired
	private BusinessService businessService;

	/**
	 * 获取微信小程序 session_key 和 openid
	 * 
	 * @author zhi.chen
	 * @param code
	 *            调用微信登陆返回的Code
	 * @return
	 */
	@ApiOperation(value = "获取微信小程序 session_key 和 openid")
	@ApiImplicitParam(name = "code", value = "小程序调用wx.login返回的code", required = false,paramType="query", dataType = "String")
	@BindPhone(mustBind = false)
	@PostMapping(value = "/getSessionKeyOropenid")
	public ApiResult<String> getSessionKeyOropenid(@RequestParam String code) {
		ApiResult<String> result = new ApiResult<>();
		log.info("get getSessionKeyOropenid code:{}", code);
		Map<String, Object> requestUrlParam = new HashMap<String, Object>();
		String requestUrl = WechatApiPropertiesUtil.getConfig("wechat.url"); // 请求地址 https://api.weixin.qq.com/sns/jscode2session
		requestUrlParam.put("appid", WechatApiPropertiesUtil.getConfig("wechat.appId")); // 开发者设置中的appId
		requestUrlParam.put("secret", WechatApiPropertiesUtil.getConfig("wechat.appSecret")); // 开发者设置中的appSecret
		requestUrlParam.put("js_code", code); // 小程序调用wx.login返回的code
		requestUrlParam.put("grant_type", Constants.AUTHORIZATION_CODE); // 默认参数

		// 发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
		JSONObject res = JSON.parseObject(UrlUtil.sendPost(requestUrl, requestUrlParam));
		log.info("get openid:{}", res);
		// 生成token ,并设置有效时间,存储在redis ，并返回给小程序客户端。
		if (res != null && !res.containsKey("errcode")) {
//			获取到openID，就保存到数据库中。
			LoginUserDto loginUserDto = new LoginUserDto();
			String openId = res.getString("openid");
			loginUserDto.setWechatId(openId);
			log.info("getSessionKeyOropenid get wxa user info:"+ loginUserDto);
			userService.insertLoginUser(loginUserDto);
			log.info("getSessionKeyOropenid userService insert LoginUser result:"+ JSONObject.toJSONString(result));
			String key = OPPENID_KEY + openId;
			businessService.setRedisValue(key, res.toJSONString(), 7200);// 2个小时
			// XXX 加密解密统一放在接入层或者服务层
			result.ok(AesUtil.encode(openId));
		} else if (res != null) {
			result.error(res.getString("errcode") + ":" + res.getString("errmsg"));
		}
		
		return result;
	}

	/**
	 * 
	 * @author zhi.chen
	 * @param sessionKey
	 *            数据进行加密签名的密钥
	 * @param encryptedData
	 *            包括敏感数据在内的完整用户信息的加密数据
	 * @param iv
	 *            加密算法的初始向量 * 解密用户敏感数据获取用户信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取微信小程序 session_key 和 openid")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据", required = false,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "openid", value = "数据进行加密签名的密钥", required = false,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "iv", value = "加密算法的初始向量 * 解密用户敏感数据获取用户信息", required = false,paramType="query", dataType = "String")
	})
	@BindPhone(mustBind = false)
	@PostMapping(value = "/getUserInfoId")
	public ApiResult<Void> getUserInfo(@RequestParam String encryptedData, @RequestParam String iv) {
		ApiResult<Void> result = new ApiResult<>();
		log.info("get user info:encryptedData:{}, iv:{}",encryptedData, iv);
		String openId = CookieHelper.getOpenid();
		log.info("get user info,openId:{}", openId);
		String oppenIdInfo = businessService.getRedisValue(OPPENID_KEY + openId);
		log.info("get user info --> oppenIdInfo:{}", oppenIdInfo);
		if(oppenIdInfo == null) {
			result.error("fdb-会话已过期,请重新登录");
			return result;
		}
		JSONObject session = JSON.parseObject(oppenIdInfo);
		String sessionKey = session.getString("session_key");
		
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				log.info("get wxa user info:{}", userInfo);
				//处理微信用户信息
				LoginUserDto loginUserDto = new LoginUserDto();
				loginUserDto.setWechatId(openId);
				loginUserDto.setWechatUserInfo(userInfo);
				log.info("get wxa loginUserDto:"+ loginUserDto);
				userService.insertLoginUser(loginUserDto);
				log.info("userService insert LoginUser result:"+ JSONObject.toJSONString(result));
				return result;
			}
		} catch (Exception e) {
			log.error("getUserInfo Exception:"+e.getMessage(), e);
		}
		return result;
	}
	

}