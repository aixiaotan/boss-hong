package com.boss.common.dto.shop;

import com.boss.common.dto.common.EntityDto;

public class LabelDto  extends EntityDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 514049471064268092L;

    /**
     * 店铺主键
     */
    private Integer shopId;

    /**
     * 店铺标签
     */
    private String shopLabel;
    
    /**
     * 店铺标签
     */
    private String shopLabelName;

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

	/**
	 * @return the shopLabelName
	 */
	public String getShopLabelName() {
		return shopLabelName;
	}

	/**
	 * @param shopLabelName the shopLabelName to set
	 */
	public void setShopLabelName(String shopLabelName) {
		this.shopLabelName = shopLabelName;
	}
}