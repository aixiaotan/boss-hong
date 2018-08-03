package com.boss.hong.shop.vo;

import java.util.Date;

import com.boss.common.dto.common.AbstractDTO;

public class UserInfoVO extends AbstractDTO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5601963660987481814L;

	/**
     * 登陆微信账号
     */
    private String wechatId;

    /**
     * 微信用户信息
     */
	private String wechatUserInfo;

    /**
     * 登录手机号
     */
    private String loginPhoneNumber;

	/**
	 * 主键
	 */
	private Integer id;
	/**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(String wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
    /**
     * 登陆微信账号
     * @return wechat_id 登陆微信账号
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 登陆微信账号
     * @param wechatId 登陆微信账号
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * 登录手机号
     * @return login_phone_number 登录手机号
     */
    public String getLoginPhoneNumber() {
        return loginPhoneNumber;
    }

    /**
     * 登录手机号
     * @param loginPhoneNumber 登录手机号
     */
    public void setLoginPhoneNumber(String loginPhoneNumber) {
        this.loginPhoneNumber = loginPhoneNumber == null ? null : loginPhoneNumber.trim();
    }

}