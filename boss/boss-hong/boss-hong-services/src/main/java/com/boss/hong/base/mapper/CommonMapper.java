package com.boss.hong.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {
	
	/**
	 * 查询bean列表
	 * @return
	 */
	List<Map<String,Object>> findAll(@Param("table") String table,@Param("condition") String condition);
	
	/**
	 * 更新单个表的某些字段
	 * @return
	 */
	int updateObjFields(@Param("table") String table,@Param("fields") String fields,@Param("condition") String condition);
	
	/**
	 * 查询单个表的某些字段
	 * @return
	 */
	List<Map<String,Object>> queryObjFields(@Param("table") String table,@Param("fields") String fields,@Param("condition") String condition);
	
	/**
	 * 插入一条记录
	 * @return
	 */
	int insert(@Param("table") String table,@Param("columns") String columns,@Param("values") String values);


	/**
	 * 删除一条记录
	 * @return
	 */
	int delete(@Param("table") String table,@Param("condition") String condition);
	
	/**
	 * 统计数量
	 * @return
	 */
	int count(@Param("table") String table,@Param("condition") String condition);
	
}
