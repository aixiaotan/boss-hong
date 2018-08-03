package com.boss.common.dto.common;


public class UserInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7830196037312102416L;

	// 真实姓名
	private String realName;

	// 身份证号
	private String idCard;

	// 手机号
	private String phone;

	// 实名认证状态
	private String realStatus;

	// uid
	private int uid;
	
	private int contactId;
	
	//微信openId
	private String openId;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(String realStatus) {
		this.realStatus = realStatus;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
