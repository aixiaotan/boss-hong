package com.boss.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义拦截器注解类，对于
 * @author peiHongWei
 *
 * 2018年7月9日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindPhone {

	/**
	 * 是否需要绑定手机号
	 * @author peiHongWei
	 * @date 2018年7月9日 下午5:18:28
	 */
	public boolean mustBind() default true;

}
