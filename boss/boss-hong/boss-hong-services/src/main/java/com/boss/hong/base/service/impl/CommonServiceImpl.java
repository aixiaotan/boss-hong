package com.boss.hong.base.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.common.util.ReflectUtil;
import com.boss.hong.base.mapper.CommonMapper;
import com.boss.hong.base.service.CommonService;


@Service
@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonMapper commonMapper;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> queryObjFields(Class<?> klass, String fields,String params, Object... paramValues) {
		for (Field f:klass.getDeclaredFields()) {
			if(fields.contains(f.getName())){
				fields = fields.replace(f.getName(), f.getAnnotation(Column.class).name() + " as "+f.getName());
			}
		}
		String condition = handleParams(klass,params,paramValues);
		if(fields.contains(",")){
			return (List<E>) commonMapper.queryObjFields(getTableName(klass), fields, condition);
		}
		List<E> result = new ArrayList<E>();
		for (Map<String, Object> map:commonMapper.queryObjFields(getTableName(klass), fields, condition)) {
			result.add((E) map.get(map.keySet().iterator().next()));
		}
		return result;
	}
	
	@Override
	public int updateObjFields(Object obj,String fields,String params, Object... paramValues){
		String condition = handleParams(obj.getClass(),params,paramValues);
		String[] fieldArray = fields.split(",");
		try {
			for (int i = 0; i < fieldArray.length; i++) {
				Field f = obj.getClass().getDeclaredField(fieldArray[i]);
				f.setAccessible(true);
				if(f.getType() == String.class && null != f.get(obj)){
					fieldArray[i] = f.getAnnotation(Column.class).name() + "='" + f.get(obj)+"'";
				}else if(f.getType() == Date.class){
					fieldArray[i] = f.getAnnotation(Column.class).name() + "='" + sdf.format(f.get(obj))+"'";
				}else{
					fieldArray[i] = f.getAnnotation(Column.class).name() + "=" + f.get(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		fields = StringUtils.join(fieldArray,",");
		return commonMapper.updateObjFields(getTableName(obj.getClass()), fields, condition);
	}
	
	@Override
	public int updateFieldsNotNull(Object obj,String params, Object... paramValues){
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> changedFieldsList = new ArrayList<String>();
		for (Field field:fields) {
			if(!field.isAnnotationPresent(Column.class)){
				continue ; 
			}
			if(ReflectUtil.getFieldValue(obj, field.getName()) == null){
				continue ; 
			}
			changedFieldsList.add(field.getName());
		}
		return updateObjFields(obj, StringUtils.join(changedFieldsList,","), params, paramValues);
	}
	
	@Override
	public <T> T findOne(Object obj){
		List<T> list = dynamicQuery(obj);
		return list.size() < 1 ? null : list.get(0);
	}
	
	@Override
	public <T> List<T> findAll(Class<T> klass,String params, Object... paramValues){
		String condition = handleParams(klass,params,paramValues);
		List<Map<String, Object>> list = commonMapper.findAll(getTableName(klass),condition);
		if(list.size() < 1){
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<T>();
		try {
			Field[] fields = klass.getDeclaredFields();
			for (Map<String, Object> map:list) {
				T t1 = klass.newInstance();
				result.add(map2Bean(t1, fields, map));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	@Override
	public int insert(Object obj){
		List<String> columnName = new ArrayList<>();
		List<String> columnValue = new ArrayList<>();
		try {
			for (Field f:obj.getClass().getDeclaredFields()) {
				if(!f.isAnnotationPresent(Column.class)){
					continue ; 
				}
				f.setAccessible(true);
				columnName.add(f.getAnnotation(Column.class).name());
				if(null == f.get(obj)){
					// 若do中的某个column在线上库中没有，新增时会报错。改为：新增的对象某个字段为空，则插入sql不设该字段。
					columnName.remove(f.getAnnotation(Column.class).name());
				}else if(f.getType() == Date.class){
					columnValue.add("'" + sdf.format(f.get(obj)) + "'");
				}else if(f.getType() == Boolean.class){
					if((Boolean) f.get(obj)){
						columnValue.add("1");
					}else {
						columnValue.add("0");
					}
				}else{
					columnValue.add("'" + f.get(obj) + "'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String columns = StringUtils.join(columnName, ",");
		String values = StringUtils.join(columnValue, ",");
		return commonMapper.insert(getTableName(obj.getClass()), columns, values);
	}

	@Override
	public int delete(Object obj){
		StringBuilder sb = new StringBuilder();
		try{
			for(Field f:obj.getClass().getDeclaredFields()){
				if(!f.isAnnotationPresent(Column.class)){
					continue ;
				}
				f.setAccessible(true);
				if(null != f.get(obj)){
					if(f.getType() == Date.class){
						sb.append(f.getAnnotation(Column.class).name() + "=").append("'" + sdf.format(f.get(obj)) + "' AND ");
					}else {
						sb.append(f.getAnnotation(Column.class).name() + "=").append("'" + f.get(obj) + "' AND ");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		String condition = sb.toString();

		return commonMapper.delete(getTableName(obj.getClass()), condition.substring(0,condition.length()-4));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> List<T> dynamicQuery(Object obj) {
		StringBuilder params = new StringBuilder();
		List<Object> paramValues = new ArrayList<Object>();
		List<Field> fieldlList = new ArrayList<Field>(Arrays.asList(obj.getClass().getDeclaredFields()));
		Class domainObjectClass = obj.getClass();
		if(!obj.getClass().isAnnotationPresent(Table.class)){
			domainObjectClass = obj.getClass().getSuperclass();
			// 传入的对象是queryVO，不是DO的时候，要加上父类(DO)的Fields
			fieldlList.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
		}
		
		StringBuilder orderBy = new StringBuilder();;
		for (Field field:fieldlList) {
			if(ReflectUtil.getFieldValue(obj, field.getName()) == null || "serialVersionUID".equals(field.getName())){
				continue ;
			}
			if(field.getName().endsWith("IsNull")){
				Boolean canNull = (Boolean) ReflectUtil.getFieldValue(obj, field.getName());
				params.append(" and " + field.getName().substring(0, field.getName().length()-6) + (canNull?" is null ":" is not null "));
				continue ;
			}
			if(field.getName().endsWith("Asc")){
				// order by的处理
				Boolean asc = (Boolean) ReflectUtil.getFieldValue(obj, field.getName());
				if(orderBy.toString().contains("order by")){
					orderBy.append(" , " + field.getName().substring(0, field.getName().length()-3) + (asc ? " asc ":" desc "));
				}else{
					orderBy.append(" order by " + field.getName().substring(0, field.getName().length()-3) + (asc ? " asc ":" desc "));
				}
				continue ;
			}
			if(field.getName().endsWith("List")){
				params.append(" and " + field.getName().substring(0, field.getName().length()-4) +" in(?) ");
			}else if(field.getName().endsWith("Max")){
				params.append(" and " + field.getName().substring(0, field.getName().length()-3) +" < ? ");
			}else if(field.getName().endsWith("Min")){
				params.append(" and " + field.getName().substring(0, field.getName().length()-3) +" > ? ");
			}else{
				params.append(" and " + field.getName() +" = ? ");
			}
			paramValues.add(ReflectUtil.getFieldValue(obj, field.getName()));
		}
		List result = findAll(domainObjectClass, params.append(orderBy).toString().replaceFirst("and", " "), paramValues.toArray());
		return result;
	}
	
	@Override
	public int count(Object obj) {
		/** XXX dynamicQuery方法将map转成对象会消耗时间,
		 * 后续可优化为调用commonMapper中的count方法,只返回数量不涉及map转对象
		 */
		return dynamicQuery(obj).size();
	}

	private <T> String getTableName(Class<T> klass){
		return klass.getAnnotation(Table.class).name();
	}
	
	@SuppressWarnings("rawtypes")
	private <T> String handleParams(Class<T> klass,String params, Object... paramValues){
		if(StringUtils.isEmpty(params)){
			return params;
		}
		if(null == paramValues){
			return " AND " + params;
		}
		
		params = " AND " + params;
		for (Field f:klass.getDeclaredFields()) {
			if(!params.contains(f.getName())){
				continue ;
			}
			// 实体类字段名转换到db表字段名，有column注解就取注解，没有的话按照驼峰转下划线规则
			if(f.isAnnotationPresent(Column.class)){
				params = params.replace(f.getName(), f.getAnnotation(Column.class).name());
			}else{
				params = params.replace(f.getName(), camel2Underline(f.getName()));
			}
		}
		
		for (int i = 0; i < paramValues.length; i++) {
			Object val = paramValues[i];
			if(val instanceof Date){
				paramValues[i] = sdf.format(val);
			}
			if(val instanceof Collection){
				// sql中有in(?)的处理，入参可以是list，不支持数组
				paramValues[i] = StringUtils.join((Collection) val, "','");
			}
		}
		return String.format(params.replace("?", "'%s'"), paramValues);
	}
	
	private <T> T map2Bean(T t1,Field[] fields,Map<String, Object> map) throws Exception{
		for (Field field:fields) {
			if("serialVersionUID".equals(field.getName())){
				continue ;
			}
			String column = camel2Underline(field.getName());
			if(field.isAnnotationPresent(Column.class)){
				column = field.getAnnotation(Column.class).name();
			}
			field.setAccessible(true);
			if(int.class == field.getType() || Integer.class == field.getType()){
				if(null == map.get(column) && int.class == field.getType()){
					field.set(t1, 0);
				}else if(null == map.get(column) && Integer.class == field.getType()){
					field.set(t1, null);
				}else {
					Long val = Long.parseLong(String.valueOf(map.get(column)));
					field.set(t1, val.intValue());
				}
			} else{
				field.set(t1, map.get(column));
			}
		} 
		return t1;
	}
	
	/**
	 * 将驼峰格式转为下划线格式,去掉实体类中的column注解,
	 * 若某字段无column注解,根据默认规则将实体类中的成员变量名转化为数据库中的表字段名
	 */
	private static String camel2Underline(String camelStr){
		if (StringUtils.isBlank(camelStr)){
			return "";
		}
		StringBuilder sb = new StringBuilder(camelStr.length());
		for (char c:camelStr.toCharArray()) {
			sb.append(Character.isUpperCase(c) ? "_"+c : c);
		}
		return sb.toString().toLowerCase();
	}

}
