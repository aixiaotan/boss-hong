package com.boss.hong.base.web;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boss.common.annotation.BindPhone;
import com.boss.common.constant.Constants;
import com.boss.common.constant.RedisConst;
import com.boss.hong.base.service.BusinessService;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/checkCode")
public class PictureCheckCodeController {
	private static Logger logger = LoggerFactory.getLogger(PictureCheckCodeController.class);
	
	@Autowired
	private BusinessService businessService;
	
	@Resource(name = "captchaProducer")
	private DefaultKaptcha captchaProducer;

	@RequestMapping("/getCode")
	@BindPhone(mustBind = false)
	public void getKaptchaImage(@RequestParam String phone){
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		//获取模板
		String capText = captchaProducer.createText();
		logger.info("此次手机号码和验证码为：phone=" + phone + ",capText= " + capText);
		ServletOutputStream out = null;
		BufferedImage bi = null;
		try {
			businessService.setRedisValue(RedisConst.PHONE_AUTH_CODE + phone, capText, Constants.EXPIRETIME_ONE_MINUTE);
			bi = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception e) {
			logger.error("生成验证码失败！",e);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
	}

	
	
}