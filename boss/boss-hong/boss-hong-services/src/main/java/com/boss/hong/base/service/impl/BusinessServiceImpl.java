package com.boss.hong.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boss.common.constant.Constants;
import com.boss.common.constant.RedisConst;
import com.boss.common.dto.common.SysDistDto;
import com.boss.common.enums.DistTypeEnum;
import com.boss.common.util.StringUtils;
import com.boss.hong.base.entity.SysDistDo;
import com.boss.hong.base.service.BusinessService;
import com.boss.hong.base.service.CommonService;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessServiceImpl implements BusinessService {

	private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
	
//	@Autowired
//	private BusinessMapper businessMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public Map<String, List<SysDistDto>> listSysDist(List<String> dataTypeList) {
		Map<String, List<SysDistDto>> result = new HashMap<>();
		SysDistDo distDo = new SysDistDo();
		for (String datatype:dataTypeList) {
			// 前端只能查询枚举类DistTypeEnum中定义的数据字典值
			if(!DistTypeEnum.containDatatype(datatype)){
				continue ;
			}
			String key = RedisConst.SYS_DIST_DATA + datatype;
			String data = this.getRedisValue(key);
			if(StringUtils.isNotEmpty(data)) {
				result.put(datatype,JSON.parseArray(data, SysDistDto.class));
				continue ;
			}
			distDo.setDatatype(datatype);
			List<SysDistDo> distDoList = commonService.dynamicQuery(distDo);
			result.put(datatype, JSON.parseArray(JSON.toJSONString(distDoList), SysDistDto.class));
			this.setRedisValue(key, JSON.toJSONString(distDoList));
		}
		return result;
	}
	
	
	@Override
	public String getRedisValue(String key) {
		String result = new String();
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		result = redisProxy.get(key);
		return result;
	}

	@Override
	public void setRedisValue(String key, String value, int expired) {
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		redisProxy.setex(key, expired, value);
	}
	
	@Override
	public void ttlValue(String key, String value, int expired) {
		/*RedisProxy redisProxy = RedisAccessor.getDefaultClient();
		long expiredSeconds = redisProxy.ttl(key);
		logger.info("redis key=" + key + " 生命周期还剩下："+expiredSeconds+"S.");
		*//***
		 * 如果该key不存在，返回-2
		 * 如果该key未设置存活时间，返回-1
		 * 如果设置过存活时间，则返回剩余的存活秒数
		 *//*
		if(expiredSeconds != -1 && expiredSeconds != -2) {
			expired = (int)expiredSeconds;
		}
		redisProxy.setex(key, expired, value);*/
	}
	
	@Override
	public void setRedisValue(String key, String value) {
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		redisProxy.set(key, value);
	}


	@Override
	public void deleteRedisValue(String key) {
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		redisProxy.del(key);
	}
	
	@Override
	public String cityCodeToName(String citycode){
		//获取城市名称(以00结尾的国标码，大数据无法正常识别,改为01再调接口)
		if(citycode.endsWith("00")){
			citycode=(citycode.substring(0,4) + "01");
		}
		String key = RedisConst.CITY_CODE + citycode;
		String cityName = this.getRedisValue(key);
		if(!StringUtils.isBlank(cityName)) {
			return cityName;
		}
//		String cityNameResult = BigDataAPI.getIdCardBelong(citycode);
//		logger.info("get city name result :"+JSONObject.toJSONString(cityNameResult));
		/*if(cityNameResult.isOK()){
			cityName = cityNameResult;
			this.setRedisValue(key, cityName);
			return cityName;
		}*/
		return cityName;
	}


	@Override
	public Boolean exists(String key) {
		return null;
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		boolean exists = redisProxy.exists(key);
//		return exists;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T  getObject(String key,Class clazz){
		T t= (T) JSONObject.parseObject(this.getRedisValue(key),clazz);
		return t;
	}


	@Override
	public void expire(String key, int time) {
//		RedisProxy redisProxy = RedisAccessor.getDefaultClient();
//		redisProxy.expire(key, Constants.EXPIRETIME_ONE_HOUR);
	}


	@Override
	public void cacheImageCode(String code,String key) {
		this.setRedisValue(RedisConst.PHONE_AUTH_CODE + key, code, Constants.EXPIRETIME_ONE_MINUTE);
	}

}
