package com.boss.hong.favorite.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boss.common.dto.favorite.FavoriteDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;
import com.boss.hong.favorite.entity.FavoriteDO;
@Mapper
public interface FavoriteMapper {

	/**
	 * 获取今日发送名片次数
	 * @author peiHongWei
	 * @date 2018年7月12日 下午6:20:28
	 * @param id
	 * @return
	 */
	int getTodaySendCardCount (Integer id);
	
	/**
	 * 获取指定收藏类型的数量
	 * @author peiHongWei
	 * @date 2018年7月12日 下午6:20:03
	 * @param favoriteDto
	 * @return
	 */
	int getFavCount (FavoriteDto favoriteDto);
	
    /**
     * 根据收藏信息查询产品大纲列表
     */
    List<OutlineDto> listOutlineByFavorite(FavoriteDO favoriteDO);
    /**
     * 根据收藏信息查询店铺或者名片
     */
    List<ShopDto> listFavoriteShopCard(FavoriteDO favoriteDO);
    /**
     * 根据店铺名字模糊查询店铺
     * @author peiHongWei
     * @date 2018年7月31日 上午10:09:08
     * @param wechatId
     * @param name
     * @return
     */
    List<ShopDto> listShopByName(@Param("name") String name,@Param("wechatId") String wechatId);
    /**
     * 根据店铺名字模糊查询收藏的大纲
     * @author peiHongWei
     * @date 2018年7月31日 上午10:08:42
     * @param wechatId
     * @param name
     * @return
     */
    List<OutlineDto> listOutlineByName(@Param("name") String name,@Param("wechatId") String wechatId);
}