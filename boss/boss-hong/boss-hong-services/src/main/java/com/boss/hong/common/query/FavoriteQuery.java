package com.boss.hong.common.query;

import java.util.Date;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;


@Table(name = "tb_fdb_favorite")
public class FavoriteQuery extends AbstractDO  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5443416794219472221L;

    /**
     * 微信绑定信息表主键
     */
	@Column(name = "login_user_id")
    private Integer loginUserId;

    /**
     * 收藏类型，01-店铺，02-名片，03-大纲。
     */
	@Column(name = "favorite_type")
    private String favoriteType;

    /**
     * 关联主键
     */
	@Column(name = "relation_id")
    private Integer relationId;

    /**
     * 是否投递给我的，01-投递给我的，02-我收藏的
     */
	@Column(name = "delivered_to_me")
    private String deliveredToMe;

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
	 * 修改时间排列，默认降序
	 */
	private Boolean updatedTimeAsc;
	
	/**
     * 投递人userID
     */
	@Column(name = "deliver_user")
    private Integer deliverUser;

	public Integer getDeliverUser() {
		return deliverUser;
	}

	public void setDeliverUser(Integer deliverUser) {
		this.deliverUser = deliverUser;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getUpdatedTimeAsc() {
		return updatedTimeAsc;
	}

	public void setUpdatedTimeAsc(Boolean updatedTimeAsc) {
		this.updatedTimeAsc = updatedTimeAsc;
	}
}