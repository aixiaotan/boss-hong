package com.boss.common.dto.common;

/**
 * 
 * @author peiHongWei
 *
 * 2018年7月27日
 */
public enum ResponseCode {
	
	SUCCESS("888","成功"),
	BUSSINESS_ERROE("601","业务异常"),
	UNKNOW_ERROR("600","系统异常"),
	SESSION_EXPIRE("602","会话已过期,请重新登录");
	
	private String code;
	private String msg;
	
	private ResponseCode(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
}
