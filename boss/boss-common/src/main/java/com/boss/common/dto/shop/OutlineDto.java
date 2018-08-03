package com.boss.common.dto.shop;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.boss.common.dto.common.EntityDto;
import com.boss.common.dto.document.DocumentInfoDto;


public class OutlineDto extends EntityDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8392559660074211929L;

    /**
     * 店铺表主键
     */
	@NotBlank(message = "店铺表主键不能为空")
	@DecimalMin("0")
    private Integer shopId;
	
	/**
	 * 收藏主键
	 */
	private Integer favoriteId;
	/**
	 * 收藏时间
	 */
	@JSONField (format = "yyyy-MM-dd HH:mm:ss")
	private Date favTime;
	
	/**
	 * 当前登录人是否收藏该条大纲  0-没有，1-有
	 */
	private Integer isFavorite;

    /**
     * 大纲内容
     */
	@NotBlank(message = "大纲内容不能为空")
	@Length(max=250,message = "大纲内容不得超过250个字符。")
    private String outlineContent;
    
    /**
     * 大纲附件信息
     */
    private List<DocumentInfoDto> documentInfoDtos;

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

	public List<DocumentInfoDto> getDocumentInfoDtos() {
		return documentInfoDtos;
	}

	public void setDocumentInfoDtos(List<DocumentInfoDto> documentInfoDtos) {
		this.documentInfoDtos = documentInfoDtos;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	/**
	 * @return the isFavorite
	 */
	public Integer getIsFavorite() {
		return isFavorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * @return the favTime
	 */
	public Date getFavTime() {
		return favTime;
	}

	/**
	 * @param favTime the favTime to set
	 */
	public void setFavTime(Date favTime) {
		this.favTime = favTime;
	}

}