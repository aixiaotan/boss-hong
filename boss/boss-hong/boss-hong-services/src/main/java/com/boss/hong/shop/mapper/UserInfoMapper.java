package com.boss.hong.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boss.hong.shop.entity.UserInfoDO;

@Mapper
public interface UserInfoMapper {
	
	/**
	 * 获取userInfo数据
	 * @author peiHongWei
	 * @date 2018年7月25日 下午4:19:04
	 * @param id
	 * @return
	 */
	UserInfoDO getUserInfo(Integer id);
} 
