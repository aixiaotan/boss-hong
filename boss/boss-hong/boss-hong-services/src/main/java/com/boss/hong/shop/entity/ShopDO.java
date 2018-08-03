package com.boss.hong.shop.entity;

import java.util.Date;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;

@Table(name = "tb_fdb_shop")
public class ShopDO extends AbstractDO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7029374022438383492L;

	/**
	 * 主键
	 */
	@Column(name = "id")
	private Integer id;
	/**
     * 创建时间
     */
	@Column(name = "created_time")
    private Date createdTime;

	/**
     * 修改时间
     */
	@Column(name = "updated_time")
    private Date updatedTime;
	
    /**
     * 微信绑定信息表主键
     */
	@Column(name = "login_user_id")
    private Integer loginUserId;

    /**
     * 店铺名称
     */
	@Column(name = "shop_name")
    private String shopName;

    /**
     * 姓名
     */
	@Column(name = "user_name")
    private String userName;

    /**
     * 所在城市
     */
	@Column(name = "city_code")
    private String cityCode;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 别人联系您的微信账号
     */
    @Column(name = "wechat_id")
    private String wechatId;

    /**
     * 别人联系您的手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 是否公开微信账号和手机号码，1-公开，0-不公开。
     */
    @Column(name = "is_publicity")
    private String isPublicity;

    /**
     * 店铺转发次数
     */
    @Column(name = "transpond_times")
    private Integer transpondTimes;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
    /**
     * 微信绑定信息表主键
     * @return login_user_id 微信绑定信息表主键
     */
    public Integer getLoginUserId() {
        return loginUserId;
    }

    /**
     * 微信绑定信息表主键
     * @param loginUserId 微信绑定信息表主键
     */
    public void setLoginUserId(Integer loginUserId) {
        this.loginUserId = loginUserId;
    }

    /**
     * 店铺名称
     * @return shop_name 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 店铺名称
     * @param shopName 店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    /**
     * 姓名
     * @return user_name 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 姓名
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 所在城市
     * @return shop_address 所在城市
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 所在城市
     * @param cityCode 所在城市
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * 公司名称
     * @return company_name 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 公司名称
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 别人联系您的微信账号
     * @return wechat_id 别人联系您的微信账号
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 别人联系您的微信账号
     * @param wechatId 别人联系您的微信账号
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * 别人联系您的手机号码
     * @return phone_number 别人联系您的手机号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 别人联系您的手机号码
     * @param phoneNumber 别人联系您的手机号码
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * 是否公开微信账号和手机号码，1-公开，0-不公开。
     * @return is_publicity 是否公开微信账号和手机号码，1-公开，0-不公开。
     */
    public String getIsPublicity() {
        return isPublicity;
    }

    /**
     * 是否公开微信账号和手机号码，1-公开，0-不公开。
     * @param isPublicity 是否公开微信账号和手机号码，1-公开，0-不公开。
     */
    public void setIsPublicity(String isPublicity) {
        this.isPublicity = isPublicity == null ? null : isPublicity.trim();
    }

    /**
     * 店铺转发次数
     * @return transpond_times 店铺转发次数
     */
    public Integer getTranspondTimes() {
        return transpondTimes;
    }

    /**
     * 店铺转发次数
     * @param transpondTimes 店铺转发次数
     */
    public void setTranspondTimes(Integer transpondTimes) {
        this.transpondTimes = transpondTimes;
    }
}