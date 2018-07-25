package com.boss.hong.shop.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boss.common.dto.shop.UserInfoDTO;
import com.boss.hong.shop.entity.UserInfoDO;
import com.boss.hong.shop.mapper.UserInfoMapper;
import com.boss.hong.shop.service.UserService;

/**
 * 用户服务
 * @author peiHongWei
 *
 * 2018年7月25日
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfoDTO getUserInfoDto(Integer id) {
		UserInfoDO userInfoDO = userInfoMapper.getUserInfo(id);
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		if(null != userInfoDO) {
			BeanUtils.copyProperties(userInfoDO, userInfoDTO);
			return userInfoDTO;
		}
		return null;
	}
	
	
}
