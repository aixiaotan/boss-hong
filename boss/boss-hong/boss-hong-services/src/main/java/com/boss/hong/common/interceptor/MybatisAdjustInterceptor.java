package com.boss.hong.common.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * mybatis拦截器
 * @author peiHongWei
 *
 * 2018年7月9日
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }))
@Component
public class MybatisAdjustInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(MybatisAdjustInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			// 方法映射的参数不管，且只看insert或者update的方法
			if (arg instanceof MappedStatement) {
				MappedStatement ms = (MappedStatement) arg;
				SqlCommandType sqlCommandType = ms.getSqlCommandType();
				if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE) {
					continue;
				} else {
					break;
				}
			} else {
				validate(args[i]);
			}
		}
		return invocation.proceed();
	}
	
	private void validate(Object obj) {
		// 无论入参是什么类型，只要涉及到两个时间字段的，一律修改
		// 当入参类型不是map时，判断是不是以DO结尾的
		if (!(obj instanceof Map)) {
			if (obj.getClass().getName().toUpperCase().endsWith("DO")) {
				logger.info("the type of input argument is " + obj.getClass().getName());
				setProperty(obj);
			}
		} else {// 当入参类型是map时，直接加上属性,注意带@Param参数也会转为map类型
			logger.info("the type of input argument is Map");
			setMapProperty(obj);
		}
	}

	/**
	 * 当入参类型是Entity时，将其中相应属性赋值
	 * @author peiHongWei
	 * @date 2018年7月9日 上午11:37:20
	 */
	private void setProperty(Object obj) {
		try {
			PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(obj.getClass());
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String name = propertyDescriptor.getName();
				Method writedMethod = propertyDescriptor.getWriteMethod();
				if ("createdTime".equalsIgnoreCase(name)) {
						writedMethod.invoke(obj, new Date());
				}
				if ("updatedTime".equalsIgnoreCase(name)) {
					writedMethod.invoke(obj, new Date());
				}
			}
		} catch (IllegalAccessException e) {
			logger.error("setProperty updatedTime createdTime IllegalAccessException",e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("setProperty updatedTime createdTime InvocationTargetException",e);
			e.printStackTrace();
		}
	}

	/**
	 * put map key 
	 * @author peiHongWei
	 * @date 2018年7月9日 上午11:37:06
	 */
	@SuppressWarnings("unchecked")
	private void setMapProperty(Object obj) {
		logger.info("the type of input argument is Map");
		Map<String, Object> map = (Map<String, Object>) obj;
		try {
			if(map.get("list") != null &&( map.get("list")  instanceof Collection)) {
				List<Object> list = (List<Object>) map.get("list");
				for (Object object : list) {
					validate(object);
				}
				return ;
			}
		} catch (Exception e) {}
		
		// 只有当session中取的到值得时候，才进行设置
		Set<String> keySet = map.keySet();
		if (keySet.contains("updatedTime")) {
			if (null == map.get("updatedTime")) {
				map.put("updatedTime", new Date());
			}
		} else {
			map.put("updatedTime", new Date());
		}
		if (keySet.contains("createdTime")) {
			if (null == map.get("createdTime")) {
				map.put("createdTime", new Date());
			}
		} else {
			map.put("createdTime", new Date());
		}

	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
