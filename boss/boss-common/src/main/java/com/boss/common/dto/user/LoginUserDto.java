package com.boss.common.dto.user;

import com.boss.common.dto.common.EntityDto;

public class LoginUserDto extends EntityDto{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2575622231624685545L;

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
     * 是否有店铺
     */
    private Integer shopId;
    
    /**
     * 店铺访问量
     */
    private Integer shopVisitCount;
    
    /**
     * 收藏名片数量
     */
    private Integer favCardCount;
    
    /**
     * 收藏店铺数量
     */
    private Integer favShopCount;
    
    /**
     * 收藏产品数
     */
    private Integer favCount;
    
    /**
     * Today收藏名片数量
     */
    private Integer favTodayCardCount;
    
    /**
     * Today收藏店铺数量
     */
    private Integer favTodayShopCount;
    
    /**
     * Today收藏产品数
     */
    private Integer favTodayCount;

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

    public Integer getFavTodayCardCount() {
		return favTodayCardCount;
	}

	public void setFavTodayCardCount(Integer favTodayCardCount) {
		this.favTodayCardCount = favTodayCardCount;
	}

	public Integer getFavTodayShopCount() {
		return favTodayShopCount;
	}

	public void setFavTodayShopCount(Integer favTodayShopCount) {
		this.favTodayShopCount = favTodayShopCount;
	}

	public Integer getFavTodayCount() {
		return favTodayCount;
	}

	public void setFavTodayCount(Integer favTodayCount) {
		this.favTodayCount = favTodayCount;
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

	public Integer getShopVisitCount() {
		return shopVisitCount;
	}

	public void setShopVisitCount(Integer shopVisitCount) {
		this.shopVisitCount = shopVisitCount;
	}

	public Integer getFavCardCount() {
		return favCardCount;
	}

	public void setFavCardCount(Integer favCardCount) {
		this.favCardCount = favCardCount;
	}

	public Integer getFavShopCount() {
		return favShopCount;
	}

	public void setFavShopCount(Integer favShopCount) {
		this.favShopCount = favShopCount;
	}

	public Integer getFavCount() {
		return favCount;
	}

	public void setFavCount(Integer favCount) {
		this.favCount = favCount;
	}

	public String getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(String wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	/**
	 * @return the shopId
	 */
	public Integer getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


}