package com.boss.common.dto.shop;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.boss.common.beans.Page;
import com.boss.common.dto.common.EntityDto;


public class ShopDto  extends EntityDto  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6727675555212321168L;

    /**
     * 微信绑定信息表主键
     */
    private Integer loginUserId;
    
    /**
     * 收藏信息表主键
     */
    private Integer favoriteId;

    /**
     * 店铺名称
     */
    @NotBlank(message = "店铺名称不能为空")
    @Length(min=1, max=40, message = "店铺名称为1-40个字符")
    private String shopName;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min=1, max=40, message = "姓名为1-40个字符")
    private String userName;

    /**
     * 所在城市(国标码)
     */
    @NotBlank(message = "所在城市(国标码)不能为空")
    @Length(min=2, max=6, message = "所在城市(国标码)为2-6个字符")
    private String cityCode;

    /**
     * 所在城市(名称)
     */
    private String cityName;
    
    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    @Length(min=1, max=200, message = "公司名称为1-200个字符")
    private String companyName;

    /**
     * 别人联系您的微信账号
     */
    @Length(min=1, max=50, message = "别人联系您的微信账号为1-50个字符")
    private String wechatId;
    
    /**
     * 访问者微信信息
     */
    @Length(min=1, max=50, message = "访问者微信信息为1-50个字符")
    private String visitWechatId;

    /**
     * 别人联系您的手机号码
     */
    @Length(min=11, max=11, message = "别人联系您的手机号码为11-11个字符")
    @Pattern(regexp = "^[1]\\d{10}$" , message = "别人联系您的手机号码为11个字符")
    private String phoneNumber;

    /**
     * 是否公开微信账号和手机号码，1-公开，0-不公开。
     */
    @NotBlank(message = "是否公开微信账号和手机号码不能为空")
    @Length(min=1, max=1, message = "姓名为1-200个字符")
    private String isPublicity;

    /**
     * 店铺转发次数
     */
    private Integer transpondTimes;
    
    /**
     * 店铺转发次数
     */
    private Integer outlineAmount;
    
    /**
     * 当前登录人是否收藏该店铺 0-没有，1-有
     */
    private Integer isFavShop;
    
    /**
     * 当前登录人是否收藏该名片 0-没有，1-有
     */
    private Integer isFavCard;
    
    /**
     * 微信头像照片路径
     */
    private String avatarUrl;
    
    /**
     * 店铺标签
     */
    private List<String> shopLabelList;
    /**
     * 店铺标签
     */
    private List<LabelDto> labelDtos;
    
    private Page<OutlineDto> outlineDtoPage;
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
        this.loginUserId = loginUserId ;
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


	public List<String> getShopLabelList() {
		return shopLabelList;
	}

	public void setShopLabelList(List<String> shopLabelList) {
		this.shopLabelList = shopLabelList;
	}

	public Integer getOutlineAmount() {
		return outlineAmount;
	}

	public void setOutlineAmount(Integer outlineAmount) {
		this.outlineAmount = outlineAmount;
	}

	public String getVisitWechatId() {
		return visitWechatId;
	}

	public void setVisitWechatId(String visitWechatId) {
		this.visitWechatId = visitWechatId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Page<OutlineDto> getOutlineDtoPage() {
		return outlineDtoPage;
	}

	public void setOutlineDtoPage(Page<OutlineDto> outlineDtoPage) {
		this.outlineDtoPage = outlineDtoPage;
	}

	/**
	 * @return the isFavShop
	 */
	public Integer getIsFavShop() {
		return isFavShop;
	}

	/**
	 * @param isFavShop the isFavShop to set
	 */
	public void setIsFavShop(Integer isFavShop) {
		this.isFavShop = isFavShop;
	}

	/**
	 * @return the isFavCard
	 */
	public Integer getIsFavCard() {
		return isFavCard;
	}

	/**
	 * @param isFavCard the isFavCard to set
	 */
	public void setIsFavCard(Integer isFavCard) {
		this.isFavCard = isFavCard;
	}

	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the labelDtos
	 */
	public List<LabelDto> getLabelDtos() {
		return labelDtos;
	}

	/**
	 * @param labelDtos the labelDtos to set
	 */
	public void setLabelDtos(List<LabelDto> labelDtos) {
		this.labelDtos = labelDtos;
	}


}