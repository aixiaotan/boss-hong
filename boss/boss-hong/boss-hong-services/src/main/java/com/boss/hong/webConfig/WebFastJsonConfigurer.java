package com.boss.hong.webConfig;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebFastJsonConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverterEx());
	    super.configureMessageConverters(converters);
	}
	
	
	@Bean
	public FastJsonHttpMessageConverterEx fastJsonHttpMessageConverterEx() {
	    return new FastJsonHttpMessageConverterEx();
	 }

}
