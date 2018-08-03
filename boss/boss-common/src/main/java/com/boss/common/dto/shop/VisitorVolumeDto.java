package com.boss.common.dto.shop;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.boss.common.dto.common.EntityDto;

public class VisitorVolumeDto extends EntityDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4989330953768227081L;

    /**
     * 统计类型，01-店铺，02-大纲
     */
	@NotBlank(message = "统计类型不能为空")
    @Length(min=2, max=2, message = "统计类型为2个字符")
    private String statisticsType;

    /**
     * 关联店铺或者大纲的主键
     */
	@NotBlank(message = "统计类型不能为空")
	@DecimalMin("0")
    private Integer relationId;

    /**
     * 查看该店铺或者大纲的用户微信号
     */
	@NotBlank(message = "查看该店铺或者大纲的用户微信号不能为空")
	@Length(min=0, max=50, message = "查看该店铺或者大纲的用户微信号为0-50个字符")
    private String wechatId;

    /**
     * 统计类型，01-店铺，02-大纲
     * @return statistics_type 统计类型，01-店铺，02-大纲
     */
    public String getStatisticsType() {
        return statisticsType;
    }

    /**
     * 统计类型，01-店铺，02-大纲
     * @param statisticsType 统计类型，01-店铺，02-大纲
     */
    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType == null ? null : statisticsType.trim();
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