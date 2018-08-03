package com.boss.hong.favorite.service;

import com.boss.common.beans.Page;
import com.boss.common.dto.favorite.FavoriteDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;

/**
 * 收藏服务
 * @author peiHongWei
 *
 * 2018年7月10日
 */
public interface FavoriteService {

	/**
	 * 新增收藏信息
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:14:33
	 */
	public Integer insertFavorite(FavoriteDto favoriteDto);
	
	/**
	 * 新增收藏信息,投递名片
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:14:33
	 */
	public Integer sendCard(FavoriteDto favoriteDto);
	
	/**
	 * 新增收藏信息
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:14:33
	 */
	public Integer updateFavorite(FavoriteDto favoriteDto);
	
	/**
	 * 删除收藏关系
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:25:06
	 */
	public Integer deleteFavorite(FavoriteDto favoriteDto);
	
	/**
	 * 获取收藏关系
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:25:06
	 */
//	public List<FavoriteDto>> listFavorite(FavoriteDto favoriteDto);
	/**
	 * 获取收藏店铺
	 * @author peiHongWei
	 * @date 2018年7月10日 下午6:25:06
	 */
	public Page<ShopDto> listFavoriteShop(String wechatId,Page<ShopDto> page);
	
	/**
	 * 获取收藏名片
	 * @author peiHongWei
	 * @date 2018年7月12日 上午10:26:19
	 */
	public Page<ShopDto> listFavoriteShopCard(String wechatId,String deliveredToMe,Page<ShopDto> page,String favoriteType);
	
	/**
	 * 获取收藏大纲
	 * @author peiHongWei
	 * @date 2018年7月12日 上午9:55:57
	 */
	public Page<OutlineDto> listFavoriteOutline(String wechatId,Page<OutlineDto> page);
	
	/**
	 * 获取指定收藏类型的数量
	 * @author peiHongWei
	 * @date 2018年7月13日 上午10:07:10
	 * @param loginUserId 登录信息ID
	 * @param favoriteType 收藏类型
	 * @param deliveredToMe 是否主动收藏
	 * @return
	 */
	public Integer countFav(Integer loginUserId, String favoriteType, String isToday) ;
	
	/**
	 * 获取指定收藏类型的数量
	 * @author peiHongWei
	 * @date 2018年7月13日 上午10:07:10
	 * @param loginUserId 登录信息ID
	 * @param favoriteType 收藏类型
	 * @param deliveredToMe 是否主动收藏
	 * @return
	 */
	public Integer getFavoriteRelation(Integer loginUserId, String favoriteType, Integer relationId) ;
	
	/**
	 * 检索用户收藏的店铺
	 * @author peiHongWei
	 * @date 2018年7月27日 下午3:44:59
	 * @param loginUserId
	 * @param param
	 * @return
	 */
	public Page<ShopDto> listShopByName(Page<ShopDto> page ,String wechatId,String name);
	
	/**
	 * 检索用户收藏的大纲
	 * @author peiHongWei
	 * @date 2018年7月30日 下午7:26:11
	 * @param page
	 * @param wechatId
	 * @param name
	 * @return
	 */
	public Page<OutlineDto> listOutlineByName(Page<OutlineDto> page ,String wechatId, String name);
	
}
