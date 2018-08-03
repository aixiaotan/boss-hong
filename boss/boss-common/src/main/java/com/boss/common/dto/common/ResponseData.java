package com.boss.common.dto.common;

import java.io.Serializable;

/**
 * 
 * @author peiHongWei
 *
 * 2018年7月27日
 */
public class ResponseData<T> implements Serializable {

	private static final long serialVersionUID = -1174346566869803659L;

	private T data;//给出去的数据
	private String code;//code
	private String msg;//错误信息
	
	private String url;
	private String MDC;
	
	public ResponseData() {
		super();
	}
	
	public ResponseData(String code,String msg) {
		super();
		this.code=code;
		this.msg=msg;
	}
	
	public ResponseData(T data,String code,String msg) {
		super();
		this.data=data;
		this.code=code;
		this.msg=msg;
	}
	
	public ResponseData(T data) {
		super();
		this.data=data;
		this.code=ResponseCode.SUCCESS.getCode();
		this.msg=ResponseCode.SUCCESS.getMsg();
	}
	
	public void setCodeMsg(ResponseCode responseCode) {
		this.code=responseCode.getCode();
		this.msg=responseCode.getMsg();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMDC() {
		return MDC;
	}

	public void setMDC(String mDC) {
		MDC = mDC;
	}
	
}
