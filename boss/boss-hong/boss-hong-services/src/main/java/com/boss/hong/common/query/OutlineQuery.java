package com.boss.hong.common.query;

import java.util.Date;
import java.util.List;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;

@Table(name = "tb_fdb_outline")
public class OutlineQuery extends AbstractDO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8706907978816639148L;

    /**
     * 店铺表主键
     */
	@Column(name = "shop_id")
    private Integer shopId;

    /**
     * 大纲内容
     */
	@Column(name = "outline_content")
    private String outlineContent;

	/**
	 * 主键
	 */
	@Column(name = "id")
	private Integer id;
	
	private List<Integer> idList;
	
	/**
	 * 主键
	 */
//	@Column(name = "id")
	private Boolean idAsc ;
	
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
     * 店铺表主键
     * @return shop_id 店铺表主键
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 店铺表主键
     * @param shopId 店铺表主键
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 大纲内容
     * @return outline_content 大纲内容
     */
    public String getOutlineContent() {
        return outlineContent;
    }

    /**
     * 大纲内容
     * @param outlineContent 大纲内容
     */
    public void setOutlineContent(String outlineContent) {
        this.outlineContent = outlineContent == null ? null : outlineContent.trim();
    }

	public Boolean getIdAsc() {
		return idAsc;
	}

	public void setIdAsc(Boolean idAsc) {
		this.idAsc = idAsc;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
}