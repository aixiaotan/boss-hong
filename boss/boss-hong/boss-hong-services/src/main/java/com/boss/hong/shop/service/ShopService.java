package com.boss.hong.shop.service;

import com.boss.common.beans.Page;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;

/**
 * 店铺相关接口服务
 * @author peihongwei
 *
 * 2018年7月6日
 */
public interface ShopService {
	
	/**
	 * 获取店铺访问量
	 * @author peiHongWei
	 * @date 2018年7月12日 下午6:36:22
	 * @param shopId
	 * @return
	 */
	public Integer countShopVisit(Integer loginUserId);
	/**
	 * 转发店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	public Object transpondShop(Integer shopId);
	
	/**
	 * 删除产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	public Integer deleteShopOutline(OutlineDto shopOutlineDto);
	/**
	 * 新增产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	public Integer inserOutline(OutlineDto outlineDto);
	/**
	 * 修改产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	public Integer updateShopOutline(OutlineDto outlineDto);

	/**
	 * 查询店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public ShopDto getShop(ShopDto shopDto,String wechatId);
	
	/**
	 * 访问量统计
	 * @author peiHongWei
	 * @date 2018年8月1日 上午9:35:06
	 * @param relationId 访问的数据的主键
	 * @param visitWechatId 访问者wechatId
	 * @param visitType 访问类型
	 */
	public void insertVisitRecord(Integer relationId,String visitWechatId,String visitType);
	
	/**
	 * 查询店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public ShopDto getShop(String wechatId);
	
	/**
	 * 转化shopDTO
	 * @author peiHongWei
	 * @date 2018年7月12日 上午10:50:27
	 */
	public void translateShopDto(ShopDto shopDto ) ;
	
	/**
	 * 获取收藏关系
	 * @author peiHongWei
	 * @date 2018年7月27日 下午6:53:56
	 * @param shopDto
	 * @param wechatId
	 * @return
	 */
	public ShopDto getFavRelation(ShopDto shopDto,String wechatId);
	
	/**
	 * 删除店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public Integer deleteShop (ShopDto shopDto);
	/**
	 * 修改店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public Integer updatedShopAllFields(ShopDto shopDto,String wechatId) ;
	/**
	 * 保存店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public Integer insertShop(ShopDto shopDto) ;
	/**
	 * 获取店铺下大纲数量
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	public Integer countOutlineByShopId(Integer shopId) ;
	
	/**
	 * 根据店铺id查询某个店铺的产品大纲
	 */
	public Page<OutlineDto> listOutlineByShopId(Integer shopId,Page<OutlineDto> page,String wechatId);

	/**
	 * 跑渠道:根据国标码查询某个城市的产品大纲
	 * @param city 城市国标码
	 * @param page 分页查询结果
	 * @param wechatId 微信openid
	 */
	public Page<ShopDto> listOutlineByCity(String city, Page<ShopDto> page,String wechatId);
	/**
	 * 根据国标码查询某个城市的店铺
	 */
	public Page<ShopDto> listShopByCity(String city, Page<ShopDto> page,String wechatId);
	
	/**
	 * 为店铺获取大纲和店铺头像
	 * @author peiHongWei
	 * @date 2018年7月30日 下午8:06:52
	 * @param shopDto
	 * @param wechatId
	 */
	public void setOutlineAndAvatarUrlForShop(ShopDto shopDto,String wechatId);

}
