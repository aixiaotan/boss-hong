package com.boss.hong.shop.apis;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boss.hong.shop.vo.UserInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@FeignClient(name="boss-hong", path="/login")
@Api(value = "用户登陆API",description = "用户登陆API")
public interface UserAcitonApi {

	
	@ApiOperation(value="用户登陆",notes="用户登陆")
	@ApiImplicitParam(name="param", value="用户登陆参数", paramType="query", dataType="String")
	@PostMapping("/test")
	public String test(@RequestParam String param);
	
	@ApiOperation(value="获取用户信息",notes="获取用户信息")
	@ApiImplicitParam(name="id", value="用户登陆参数", paramType="path", dataType="Integer")
	@GetMapping("/userInfo/id/{id}")
	public UserInfoVO getUserInfoDto(@PathVariable Integer id) ;
}
