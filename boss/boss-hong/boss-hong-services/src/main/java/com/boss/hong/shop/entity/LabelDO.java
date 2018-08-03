package com.boss.hong.shop.entity;


import java.util.Date;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;

@Table(name = "tb_fdb_label")
public class LabelDO extends AbstractDO  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7945119652449051375L;

    /**
     * 店铺主键
     */
	@Column(name = "shop_id")
    private Integer shopId;

    /**
     * 店铺标签
     */
	@Column(name = "shop_label")
    private String shopLabel;

	/**
	 * 主键
	 */
//	@Column(name = "id")
	private Integer id;
	/**
     * 创建时间
     */
//	@Column(name = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
//	@Column(name = "updated_time")
    private Date updatedTime;

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
     * 店铺主键
     * @return shop_id 店铺主键
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 店铺主键
     * @param shopId 店铺主键
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 店铺标签
     * @return shop_label 店铺标签
     */
    public String getShopLabel() {
        return shopLabel;
    }

    /**
     * 店铺标签
     * @param shopLabel 店铺标签
     */
    public void setShopLabel(String shopLabel) {
        this.shopLabel = shopLabel == null ? null : shopLabel.trim();
    }
}