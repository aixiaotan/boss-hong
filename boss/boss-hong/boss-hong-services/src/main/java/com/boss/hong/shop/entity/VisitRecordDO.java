package com.boss.hong.shop.entity;

import java.util.Date;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;

@Table(name = "tb_fdb_visit_record")
public class VisitRecordDO extends AbstractDO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6241651368718081848L;

    /**
     * 统计类型，01-店铺，02-大纲
     */
	@Column(name = "visit_type")
    private String visitType;

    /**
     * 关联店铺或者大纲的主键
     */
	@Column(name = "relation_id")
    private Integer relationId;

    /**
     * 查看该店铺或者大纲的用户微信号
     */
	@Column(name = "wechat_id")
    private String wechatId;

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
     * 统计类型，01-店铺，02-大纲
     * @return statistics_type 统计类型，01-店铺，02-大纲
     */
    public String getVisitType() {
        return visitType;
    }

    /**
     * 统计类型，01-店铺，02-大纲
     * @param statisticsType 统计类型，01-店铺，02-大纲
     */
    public void setVisitType(String visitType) {
        this.visitType = visitType == null ? null : visitType.trim();
    }

    /**
     * 关联店铺或者大纲的主键
     * @return relation_id 关联店铺或者大纲的主键
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * 关联店铺或者大纲的主键
     * @param relationId 关联店铺或者大纲的主键
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * 查看该店铺或者大纲的用户微信号
     * @return wechat_id 查看该店铺或者大纲的用户微信号
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 查看该店铺或者大纲的用户微信号
     * @param wechatId 查看该店铺或者大纲的用户微信号
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }
}