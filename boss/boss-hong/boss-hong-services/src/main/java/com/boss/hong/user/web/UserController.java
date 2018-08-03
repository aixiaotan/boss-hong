package com.boss.hong.user.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.boss.common.annotation.BindPhone;
import com.boss.common.beans.ApiResult;
import com.boss.common.dto.user.LoginUserDto;
import com.boss.common.util.CookieHelper;
import com.boss.common.util.StringUtils;
import com.boss.hong.user.service.UserService;

/**
 * 用户
 * @author peihongwei
 *
 * 2018年7月6日
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService ;
	
	/**
	 * 查询店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@GetMapping(value = "/getLoginUser")
	@BindPhone(mustBind = false)
	public ApiResult<LoginUserDto> getLoginUserWechatId() {
		logger.info("UserController getShopById .");
		ApiResult<LoginUserDto> apiResult = new ApiResult<>();
		LoginUserDto dto = userService.getLoginUserWechatId(CookieHelper.getOpenid());
		logger.info("UserController getShopById result:" +JSONObject.toJSONString(apiResult));
		return apiResult.ok(dto);
	}
	
	
	/**
	 * 发送短信验证码
	 * @author Yao
	 * @create 2018年7月11日
	 */
	@BindPhone(mustBind = false)
	@GetMapping(value = "/sendSms")
	public ApiResult<String> sendBindPhoneSms(@RequestParam(required = true) String phone,
			@RequestParam(required = false) String authCode){
		ApiResult<String> result = new ApiResult<>();
		/*if(!PhoneValidator.checkPhone(phone)){//TODO 
			return new ApiResult<String>().error("手机号码格式错误");
		}*/
		if(StringUtils.isNotEmpty(authCode) && authCode.length() != 4){
			return new ApiResult<String>().error("验证码错误。");
		}
		String wechatId = CookieHelper.getOpenid();
		return result.ok(userService.sendBindPhoneSms(phone,authCode,wechatId));
	}
	
	/**
	 * 绑定手机号码
	 * @author Yao
	 * @create 2018年7月11日
	 */
	@BindPhone(mustBind = false)
	@PostMapping(value = "/bindPhone")
	public ApiResult<String> bindPhone(@RequestParam(required = true) String phone,
			@RequestParam(required = true) String smsCode){
		ApiResult<String> result = new ApiResult<>();
		return result.ok(userService.bindPhone(CookieHelper.getOpenid(),phone, smsCode));
	}
}
