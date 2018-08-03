package com.boss.hong.common.query;


import java.util.Date;
import java.util.List;

import com.boss.hong.shop.entity.LabelDO;

public class LabelQuery extends LabelDO  {

	private static final long serialVersionUID = 4798967379981981802L;
	
    /**
     * id升序排列
     */
    private Boolean idAsc;
    
    /**
     * 店铺id多选
     */
    private List<Integer> shopIdList;
    
    /**
     * 更新时间最大值
     */
    private Date updatedTimeMax;
    
    /**
     * 更新时间最小值
     */
    private Date updatedTimeMin;

	public Boolean getIdAsc() {
		return idAsc;
	}

	public void setIdAsc(Boolean idAsc) {
		this.idAsc = idAsc;
	}

	public List<Integer> getShopIdList() {
		return shopIdList;
	}

	public void setShopIdList(List<Integer> shopIdList) {
		this.shopIdList = shopIdList;
	}

	public Date getUpdatedTimeMax() {
		return updatedTimeMax;
	}

	public void setUpdatedTimeMax(Date updatedTimeMax) {
		this.updatedTimeMax = updatedTimeMax;
	}

	public Date getUpdatedTimeMin() {
		return updatedTimeMin;
	}

	public void setUpdatedTimeMin(Date updatedTimeMin) {
		this.updatedTimeMin = updatedTimeMin;
	}
	
}