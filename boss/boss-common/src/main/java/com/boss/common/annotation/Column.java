package com.boss.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE })
public @interface Column {
	public abstract String name();

	public abstract String comment() default "";
}