package com.boss.common.beans;

public class SmsRecord implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String phone;
	
	private String type;
	
	private String content;
	
	private String addTime;
	
	public SmsRecord() {}
	
	public SmsRecord(String phone, String type, String content, String addTime) {
		this.phone = phone;
		this.type = type;
		this.content = content;
		this.addTime = addTime;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}
