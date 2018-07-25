package com.boss.hong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author peihongwei
 *
 * 2018年7月3日
 */
@Configuration 
@EnableSwagger2 
public class Swagger2 {

	@Bean
	public Docket alipayApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("小程序API接口文档")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.boss.hong"))
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("飞单宝API")
				.description("飞单宝相关各种API")
				.termsOfServiceUrl("https://www.touna.cn/")
				.contact(new Contact("投哪网", "https://www.touna.cn/", "www@qq.com"))
				.version("1.0").build();

	}

}
