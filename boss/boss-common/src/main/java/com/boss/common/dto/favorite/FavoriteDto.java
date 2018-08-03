package com.boss.common.dto.favorite;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.boss.common.dto.common.EntityDto;


public class FavoriteDto  extends EntityDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4162397327444568072L;

    /**
     * 微信绑定信息表主键
     */
	@DecimalMin("0")
    private Integer loginUserId;

    /**
     * 收藏类型，01-店铺，02-名片，03-大纲。
     */
	@NotBlank(message = "收藏类型不能为空")
	@Length(min = 2, max=2, message = "收藏类型不得超过2个字符")
    private String favoriteType;

    /**
     * 关联主键
     */
	@DecimalMin("0")
    private Integer relationId;

    /**
     * 是否投递给我的，01-投递给我的，02-我收藏的
     */
	@NotBlank(message = "是否投递给我的不能为空")
	@Length(min = 2, max=2, message = "是否投递给我的类型不得超过2个字符")
    private String deliveredToMe;
    
	/**
     * 投递人userID
     */
    private Integer deliverUser;
    
    /**
     * 是否是查询今天的新增量 1-是，0-否
     */
    private String isToday;
    
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
     * 收藏类型，01-店铺，02-名片，03-大纲。
     * @return favorite_type 收藏类型，01-店铺，02-名片，03-大纲。
     */
    public String getFavoriteType() {
        return favoriteType;
    }

    /**
     * 收藏类型，01-店铺，02-名片，03-大纲。
     * @param favoriteType 收藏类型，01-店铺，02-名片，03-大纲。
     */
    public void setFavoriteType(String favoriteType) {
        this.favoriteType = favoriteType == null ? null : favoriteType.trim();
    }

    /**
     * 关联主键
     * @return relation_id 关联主键
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * 关联主键
     * @param relationId 关联主键
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * 是否投递给我的，01-投递给我的，02-我收藏的
     * @return delivered_to_me 是否投递给我的，01-投递给我的，02-我收藏的
     */
    public String getDeliveredToMe() {
        return deliveredToMe;
    }

    /**
     * 是否投递给我的，01-投递给我的，02-我收藏的
     * @param deliveredToMe 是否投递给我的，01-投递给我的，02-我收藏的
     */
    public void setDeliveredToMe(String deliveredToMe) {
        this.deliveredToMe = deliveredToMe == null ? null : deliveredToMe.trim();
    }

	public Integer getDeliverUser() {
		return deliverUser;
	}

	public void setDeliverUser(Integer deliverUser) {
		this.deliverUser = deliverUser;
	}

	public String getIsToday() {
		return isToday;
	}

	public void setIsToday(String isToday) {
		this.isToday = isToday;
	}
}