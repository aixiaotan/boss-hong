package com.boss.hong.base.service;

import java.util.List;
import java.util.Map;

import com.boss.common.dto.common.SysDistDto;

public interface BusinessService {

	/**
	 * 获取基础数据接口
	 * @author peiHongWei
	 * @date 2018年7月19日 下午3:29:50
	 * @param dataTypeList
	 * @return
	 */
	public Map<String, List<SysDistDto>> listSysDist(List<String> dataTypeList);
	
	/**
	 * 
	 * @author peiHongWei
	 * @date 2018年7月20日 上午10:15:20
	 * @param key 根据redis-key值获取缓存数据
	 * @return
	 */
	public String getRedisValue(String key);
	
	/**
	 * 
	 * @author peiHongWei
	 * @date 2018年7月20日 上午10:15:20
	 * @param key 根据redis-key值删除缓存数据
	 * @return
	 */
	public void deleteRedisValue(String key);
	
	/**
	 * 判断Redis中是否有key值。
	 * @author peiHongWei
	 * @date 2018年8月1日 上午11:50:52
	 * @param key
	 * @return
	 */
	public Boolean exists(String key);
	
	/**
	 * 获取指定key值得对象
	 * @author peiHongWei
	 * @date 2018年8月1日 上午11:52:18
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> T  getObject(String key,Class clazz);
	/**
	 * 刷新key值时间
	 * @author peiHongWei
	 * @date 2018年8月1日 下午12:12:20
	 * @param key
	 * @param time
	 */
	public void expire(String key,int time);

	/**
	 * 
	 * @author peiHongWei
	 * @date 2018年7月20日 上午10:15:27
	 * @param key 设置redis-key值
	 * @param value 设置redis-value值
	 * @param expired 设置过期时间 单位：秒
	 * @return
	 */
	public void setRedisValue(String key, String value, int expired);

	/**
	 * 该方法会修改value值，并不会改变过期时间
	 * @author peiHongWei
	 * @date 2018年8月2日 下午3:43:34
	 * @param key 设置redis-key值
	 * @param value 设置redis-value值
	 * @param expired 设置过期时间 单位：秒
	 * @return
	 */
	public void ttlValue(String key, String value, int expired) ;
	/**
	 * 过期时间为Redis默认值
	 * @author peiHongWei
	 * @date 2018年7月20日 上午10:15:30
	 * @param key 设置redis-key值
	 * @param value 设置redis-value值
	 * @return
	 */
	public void setRedisValue(String key, String value);
	
	/**
	 * 根据cityCode获取cityName
	 * @author peiHongWei
	 * @date 2018年7月30日 下午2:27:01
	 * @param citycode
	 * @return
	 */
	public String cityCodeToName(String citycode);
	/**
	 * 缓存生成的图片验证码到Redis
	 * @author peiHongWei
	 * @date 2018年8月2日 下午3:52:12
	 * @param citycode
	 * @return
	 */
	public void cacheImageCode(String code,String key);
}
