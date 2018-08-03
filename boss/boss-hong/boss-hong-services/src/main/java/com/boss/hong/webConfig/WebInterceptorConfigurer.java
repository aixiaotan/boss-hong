package com.boss.hong.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//import cn.touna.crm.webapp.interceptor.AuthInterceptor;

@Configuration
public class WebInterceptorConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 添加登录拦截器
//		registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");

		super.addInterceptors(registry);
	}

}
