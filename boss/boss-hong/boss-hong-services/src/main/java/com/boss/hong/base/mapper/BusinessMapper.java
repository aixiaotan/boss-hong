package com.boss.hong.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boss.common.dto.common.CityDto;
import com.boss.common.dto.common.SysDistDto;

@Mapper
public interface BusinessMapper {

	/**
	 * 获取城市列表
	 */
	List<CityDto> listCity();
	
	List<SysDistDto> listSysDist(List<String> list);
}
