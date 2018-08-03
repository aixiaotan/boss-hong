package com.boss.hong.favorite.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boss.common.beans.Page;
import com.boss.common.constant.Constants;
import com.boss.common.constant.ReturnMsg;
import com.boss.common.dto.document.DocumentInfoDto;
import com.boss.common.dto.favorite.FavoriteDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;
import com.boss.hong.base.service.BusinessService;
import com.boss.hong.base.service.CommonService;
import com.boss.hong.document.service.DocumentInfoService;
import com.boss.hong.favorite.entity.FavoriteDO;
import com.boss.hong.favorite.mapper.FavoriteMapper;
import com.boss.hong.favorite.service.FavoriteService;
import com.boss.hong.shop.service.ShopService;
import com.boss.hong.user.service.UserService;
import com.github.pagehelper.PageHelper;

/**
 * 收藏服务实现
 * @author peiHongWei
 *
 * 2018年7月10日
 */
@Service
public class FavoriteServiceImpl implements FavoriteService{
	
	private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);
			
	@Autowired
	private CommonService commonService;
	@Autowired
	private DocumentInfoService documentInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private FavoriteMapper favoriteMapper;

	@Override
	public Integer insertFavorite(FavoriteDto favoriteDto) {
		logger.info("FavoriteServiceImpl insertFavorite param:"+favoriteDto);
		//判断是否发送给名片或者收藏过该名片。
		FavoriteDO favoriteForQuery = new FavoriteDO();
		BeanUtils.copyProperties(favoriteDto, favoriteForQuery);
		favoriteForQuery.setDeliveredToMe(null);
		favoriteForQuery.setDeliverUser(null);
		List<FavoriteDO> queryResult = commonService.dynamicQuery(favoriteForQuery);
		if(CollectionUtils.isNotEmpty(queryResult)) {
			String errorMsg = "";
			if(Constants.DELIVERED_TO_ME_02.equals(favoriteDto.getDeliveredToMe())) {
				errorMsg = ReturnMsg.FAV_HAS_SAME;
			}else if(Constants.DELIVERED_TO_ME_01.equals(favoriteDto.getDeliveredToMe()) && 
					(favoriteDto.getDeliverUser() != null || favoriteDto.getDeliverUser()!=0)){
				errorMsg = ReturnMsg.FAV_HAS;
			}else {
				errorMsg = ReturnMsg.FAV_SEND_CARD;
			}
			logger.info("FavoriteServiceImpl insertFavorite 已经存在相同数据:"+JSONObject.toJSONString(""));
			return 0;
		}
		FavoriteDO favoriteDo = new FavoriteDO();
		BeanUtils.copyProperties(favoriteDto, favoriteDo);
		favoriteDo.setUpdatedTime(new Date());
		favoriteDo.setCreatedTime(new Date());
		Integer count = commonService.insert(favoriteDo);
//		logger.info("FavoriteServiceImpl insertFavorite result:"+JSONObject.toJSONString(result));
		return count;
	}
	
	@Override
	public Integer deleteFavorite(FavoriteDto favoriteDto) {
		logger.info("FavoriteServiceImpl deleteFavorite param:"+favoriteDto);
		FavoriteDO favoriteDo = new FavoriteDO();
		BeanUtils.copyProperties(favoriteDto, favoriteDo);
		Integer count = commonService.delete(favoriteDo);
//		logger.info("FavoriteServiceImpl deleteFavorite result:"+JSONObject.toJSONString(result));
		return count;
	}
	
	@Override
	public Integer updateFavorite(FavoriteDto favoriteDto) {
		logger.info("FavoriteServiceImpl updateFavorite param:" + favoriteDto);
		FavoriteDO favoriteDo = new FavoriteDO();
		BeanUtils.copyProperties(favoriteDto, favoriteDo);
		favoriteDo.setUpdatedTime(new Date());
		Integer count = commonService.updateObjFields(favoriteDo, "deliveredToMe,updatedTime",
				"id=? and loginUserId = ?", favoriteDto.getId(),favoriteDto.getLoginUserId());
		logger.info("FavoriteServiceImpl updateFavorite result:" + count);
		return count;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page<OutlineDto> listFavoriteOutline(String wechatId,Page<OutlineDto> page) {
		logger.info("FavoriteServiceImpl listFavoriteOutline param-wechatId:" ,wechatId );
		FavoriteDO favoriteDO = new FavoriteDO();
		favoriteDO.setFavoriteType(Constants.FAVORITE_TYPE_OUTLINE);
		favoriteDO.setLoginUserId(userService.getLoginUserIdByWechatId(wechatId));
		favoriteDO.setDeliveredToMe(Constants.DELIVERED_TO_ME_02);
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<OutlineDto> outlineDtos = favoriteMapper.listOutlineByFavorite(favoriteDO);
		page.setTotalSize(pagehelper.getTotal());
		if(CollectionUtils.isNotEmpty(outlineDtos)) {
			List<OutlineDto> dtos = new ArrayList<OutlineDto>();
			OutlineDto outlineDto = null;
			for (OutlineDto outlineDo : outlineDtos) {
				outlineDto = new OutlineDto();
				BeanUtils.copyProperties(outlineDo, outlineDto);
				DocumentInfoDto documentInfoDto = new DocumentInfoDto();
				documentInfoDto.setIdForeignKey(outlineDo.getId());
				List<DocumentInfoDto> documentInfoDtos = documentInfoService.listDocumentInfo(documentInfoDto);
				outlineDto.setDocumentInfoDtos(documentInfoDtos);
				dtos.add(outlineDto);
			}
			page.setList(dtos);
		}
//		logger.info("FavoriteServiceImpl listFavoriteOutline result:" +JSONObject.toJSONString(result) );
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<ShopDto> listFavoriteShopCard(String wechatId,String deliveredToMe,Page<ShopDto> page,String favoriteType) {
		logger.info("FavoriteServiceImpl listFavoriteShopCard param-wechatId:" +wechatId+",deliveredToMe="+deliveredToMe );
		FavoriteDO favoriteDO = new FavoriteDO();
		favoriteDO.setLoginUserId(userService.getLoginUserIdByWechatId(wechatId));
		favoriteDO.setDeliveredToMe(deliveredToMe);
		favoriteDO.setFavoriteType(favoriteType);
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<ShopDto> shopDtos = favoriteMapper.listFavoriteShopCard(favoriteDO);
		page.setTotalSize(pagehelper.getTotal());
		if(CollectionUtils.isNotEmpty(shopDtos)) {
			List<ShopDto> returnShopDtos = new ArrayList<>();
			ShopDto shopDto = null;
			for (ShopDto shopQueryResult : shopDtos) {
				shopDto = new ShopDto();
				BeanUtils.copyProperties(shopQueryResult, shopDto);
				shopDto.setCityName(businessService.cityCodeToName(shopDto.getCityCode()));
				returnShopDtos.add(shopDto);
			}
			page.setList(returnShopDtos);
		}
//		logger.info("FavoriteServiceImpl listFavoriteShopCard result:" +JSONObject.toJSONString(result) );
		return page;
	}
	
	
	@Override
	public Page<ShopDto> listFavoriteShop(String wechatId,Page<ShopDto> page) {
		logger.info("FavoriteServiceImpl listFavoriteShop param-wechatId:" +wechatId);
		Page<ShopDto> page2 = this.listFavoriteShopCard(wechatId,Constants.DELIVERED_TO_ME_02,page,Constants.FAVORITE_TYPE_SHOP);
		if(null == page2) {
//			result.error(ReturnMsg.SELECT_ERROR);
			return page2;
		}
		List<ShopDto> shopDtos = page2.getList();
		if(CollectionUtils.isNotEmpty(shopDtos)) {
			//获取大纲信息
			for (ShopDto shopDto : shopDtos) {
				shopService.translateShopDto(shopDto);
				shopService.setOutlineAndAvatarUrlForShop(shopDto, wechatId);
			}
			page.setList(shopDtos);
		}
//		logger.info("FavoriteServiceImpl listFavoriteShop result:" +JSONObject.toJSONString(result) );
		return page2;
	}
	
	

	@Override
	public Integer sendCard(FavoriteDto favoriteDto) {
		logger.info("FavoriteServiceImpl sendCard param-favoriteDto:" +favoriteDto);
		//查询当天投递次数
		Integer count = favoriteMapper.getTodaySendCardCount(favoriteDto.getDeliverUser());
		logger.info("FavoriteServiceImpl sendCard count:" + count );
		if(count > 20) {
//			return ReturnMsg.SEND_CARD_MORE_20; //TODO 
		}
		int result = this.insertFavorite(favoriteDto);
		logger.info("FavoriteServiceImpl sendCard result:" +JSONObject.toJSONString(result));
		return result;
	}
	
	@Override
	public Integer countFav(Integer loginUserId, String favoriteType, String isToday) {
		logger.info("FavoriteServiceImpl countFav param-loginUserId=" + loginUserId + ",favoriteType=" + favoriteType + ",isToday=" + isToday);
		FavoriteDto favoriteDto = new FavoriteDto();
		favoriteDto.setLoginUserId(loginUserId);
		favoriteDto.setFavoriteType(favoriteType);
		favoriteDto.setDeliveredToMe("02");
		favoriteDto.setIsToday(isToday);
		Integer count = favoriteMapper.getFavCount(favoriteDto);
		logger.info("FavoriteServiceImpl countFav count:" +count);
		return count;
	}

	@Override
	public Integer getFavoriteRelation(Integer loginUserId, String favoriteType, Integer relationId) {
		logger.info("FavoriteServiceImpl getFavoriteRelation param-loginUserId=" + loginUserId + ",favoriteType=" + favoriteType + ",relationId=" + relationId);
		FavoriteDto favoriteDto = new FavoriteDto();
		favoriteDto.setDeliveredToMe("02");
		favoriteDto.setFavoriteType(favoriteType);
		favoriteDto.setLoginUserId(loginUserId);
		favoriteDto.setRelationId(relationId);
		Integer count = favoriteMapper.getFavCount(favoriteDto);
		logger.info("FavoriteServiceImpl getFavoriteRelation count:" +count);
		return count;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<ShopDto> listShopByName(Page<ShopDto> page ,String wechatId,String name) {
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<ShopDto> shopDtos = favoriteMapper.listShopByName(name,wechatId);
		for (ShopDto shopDto : shopDtos) {
			shopService.translateShopDto(shopDto);
			shopService.setOutlineAndAvatarUrlForShop(shopDto, wechatId);
		}
		page.setTotalSize(pagehelper.getTotal());
		page.setList(shopDtos);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<OutlineDto> listOutlineByName(Page<OutlineDto> page, String wechatId,String name) {
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<OutlineDto> outlineDtos = favoriteMapper.listOutlineByName(name,wechatId);
		page.setTotalSize(pagehelper.getTotal());
		page.setList(outlineDtos);
		return page;
	}

}
