package com.boss.shar.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * @ProjectName: [idea-X]
 * @Author: [dujiayu]
 */
@EnableSwagger2
@Configuration
public class Swagger2 {
	
	//項目名
	@Value("${spring.application.name}")
	private String projectName;
	
	@Value("${swagger2.scan.package.path}")
	private String basePackage;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any()).build();
	}
	
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(projectName + "中使用Swagger搭建RESTful APIs")
				.description("idea-X 無限幻想").contact("idea-X").build();
	}
}
