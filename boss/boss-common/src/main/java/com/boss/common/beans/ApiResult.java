package com.boss.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApiResult<T> implements Serializable {

	@SuppressWarnings("rawtypes")
	public ApiResult() {
		status = 200;
		attach = new HashMap();
	}

	public Object getResult() {
		return result;
	}

	/**
	 * @deprecated Method setResult is deprecated
	 */

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ApiResult error(String message) {
		status = 500;
		this.message = message;
		return this;
	}

	public ApiResult ok() {
		status = 200;
		return this;
	}

	public ApiResult ok(Object t) {
		result = t;
		status = 200;
		return this;
	}

	public boolean isOK() {
		return status == 200;
	}

	public int getStatus() {
		return status;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addAttach(Object k, Object v) {
		if (attach == null)
			attach = new HashMap();
			attach.put(k, v);
	}

	public Object getAttach(Object k) {
		return attach != null ? attach.get(k) : null;
	}

	public String toString() {
		return (new StringBuilder()).append("ApiResult [status=").append(status).append(", result=").append(result)
				.append(", message=").append(message).append(", code=").append(code).append(", attach=").append(attach)
				.append("]").toString();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @deprecated Method getAttach is deprecated
	 */

	@SuppressWarnings("rawtypes")
	public Map getAttach() {
		return attach;
	}

	/**
	 * @deprecated Method setAttach is deprecated
	 */

	@SuppressWarnings("rawtypes")
	public void setAttach(Map attach) {
		this.attach = attach;
	}

	private static final long serialVersionUID = 4563841876874219154L;
	private int status;
	private Object result;
	private String message;
	private int code;
	@SuppressWarnings("rawtypes")
	private Map attach;
}
