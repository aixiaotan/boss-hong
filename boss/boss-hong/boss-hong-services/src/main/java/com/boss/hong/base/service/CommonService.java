package com.boss.hong.base.service;

import java.util.List;

public interface CommonService {
	
	/**
	 * 查询字段
	 * @return
	 */
	public <E> List<E> queryObjFields(Class<?> klass,String fields,String params, Object... paramValues);
	
	/**
	 * 查询某个bean
	 * @return
	 */
	public <T> T  findOne(Object obj);
	
	/**
	 * 查询bean列表
	 * @return
	 */
	public <T> List<T>  findAll(Class<T> klass, String params, Object... paramValues);
	
	/**
	 * 更新字段
	 * @return
	 */
	public int updateObjFields(Object obj,String fields,String params, Object... paramValues);
	
	public int updateFieldsNotNull(Object obj, String params, Object... paramValues);
	
	/**
	 * 插入一条记录
	 * @return
	 */
	public int insert(Object obj);

	/**
	 * 删除一条记录
	 * @return
	 */
	public int delete(Object obj);

	/**
	 * 动态查询
	 * @return
	 */
	public <T> List<T> dynamicQuery(Object obj);
	
	/**
	 * 统计数量
	 * @return
	 */
	public int count(Object obj);
}
