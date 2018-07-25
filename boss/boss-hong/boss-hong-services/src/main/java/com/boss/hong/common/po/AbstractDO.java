package com.boss.hong.common.po;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * DTO基类
 *
 */
public abstract class AbstractDO implements Serializable {


	private static final long serialVersionUID = 535L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
