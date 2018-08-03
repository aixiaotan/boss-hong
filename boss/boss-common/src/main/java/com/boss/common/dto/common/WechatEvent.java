package com.boss.common.dto.common;

public class WechatEvent implements java.io.Serializable {

	private static final long serialVersionUID = -2877837607059775070L;

	private String fromUserName;
	
	private String toUserName;
	
	private String msgType;
	
	private String eventType;
	
	private String eventKey;
	
	private String ticket;
	
	private String createTime;

	public String getcreateTime() {
		return createTime;
	}

	public void setcreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
