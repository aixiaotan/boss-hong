package com.boss.hong.webConfig;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartFileConfig {

	@Bean
	public MultipartConfigElement multipartConfigElement() {

		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		// 单个文件最大2M(KB,MB)
		factory.setMaxFileSize("2048KB");
		
		// 设置总上传数据总大小,20M
		factory.setMaxRequestSize("20480KB");
		
		return factory.createMultipartConfig();

	}
}
