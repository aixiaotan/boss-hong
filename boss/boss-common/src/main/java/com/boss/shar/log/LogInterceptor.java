package com.boss.shar.log;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class LogInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	private static final String defPointcutExpression = "@within(org.springframework.web.bind.annotation.RestController)";
	
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	
	@Pointcut(defPointcutExpression)
	public void pointcutLog() {
	}
	
	@Before("pointcutLog()")
	@Order(5)
	public void excuteMethodBefore(JoinPoint joinPoint) {
		startTime.set(System.currentTimeMillis());//記錄開始時間
		String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
		Object[] paramValue = joinPoint.getArgs();
		int paramValueLength = paramValue.length;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(int i=0;i<paramNames.length;i++) {
			paramMap.put(paramNames[i], i<=paramValueLength?paramNames[i]:"");
		}
		logger.info("執行的類及方法:"+joinPoint.getTarget().getClass().getSimpleName()+"."+joinPoint.getSignature().getName()+","
		+"入參:"+JSON.toJSONString(paramMap));
	}
	
	@Order(5)
	@AfterReturning(returning = "retVal",pointcut="pointcutLog()")
	public void excuteMethodAfterReturning(JoinPoint joinPoint,Object retVal) {
		startTime.set(System.currentTimeMillis());//記錄開始時間
		String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
		Object[] paramValue = joinPoint.getArgs();
		int paramValueLength = paramValue.length;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(int i=0;i<paramNames.length;i++) {
			paramMap.put(paramNames[i], i<=paramValueLength?paramNames[i]:"");
		}
		//不打印密碼
		if(JSON.toJSONString(retVal).indexOf("pwd")>0) {
			return;
		}
		logger.info("完成的類及方法:"+joinPoint.getTarget().getClass().getSimpleName()+"."+joinPoint.getSignature().getName()+"完成,"
		+"耗時:"+(System.currentTimeMillis() - startTime.get())+"ms");
	}
}
