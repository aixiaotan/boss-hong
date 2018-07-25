package com.boss.common.dto;

public enum ResponseCode {
	
	SUCCESS("888","成功"),
	BUSSINESS_ERROE("601","业务异常"),
	UNKNOW_ERROR("600","系统异常");
	
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
