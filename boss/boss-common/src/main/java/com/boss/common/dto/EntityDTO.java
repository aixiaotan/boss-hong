package com.boss.common.dto;

import java.util.Date;

/**
 * 实体DTO基类
 *
 */
public class EntityDTO extends AbstractDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1587591529228255705L;
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
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
	
	
}
