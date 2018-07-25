package com.boss.hong.shop.web;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boss.common.dto.shop.UserInfoDTO;
import com.boss.hong.shop.apis.UserAcitonApi;
import com.boss.hong.shop.service.UserService;
import com.boss.hong.shop.vo.UserInfoVO;


@RestController
@RequestMapping("/login")
public class UserAction implements UserAcitonApi {

	@Autowired
	private UserService userService;
	
	@Override
	public String test(String param) {
		// TODO Auto-generated method stub
		return "hello" + param;
	}

	@Override
	public UserInfoVO getUserInfoDto(@PathVariable Integer id) {
		UserInfoDTO userInfoDTO = userService.getUserInfoDto(id);
		UserInfoVO infoVO = new UserInfoVO();
		if(userInfoDTO != null) {
			BeanUtils.copyProperties(userInfoDTO, infoVO);
			return infoVO;
		}
		return null;
	}
	
}
