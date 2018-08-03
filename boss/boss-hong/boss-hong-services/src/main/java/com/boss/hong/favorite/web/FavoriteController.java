package com.boss.hong.favorite.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.boss.common.beans.ApiResult;
import com.boss.common.beans.Page;
import com.boss.common.dto.favorite.FavoriteDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;
import com.boss.common.util.CookieHelper;
import com.boss.hong.favorite.service.FavoriteService;
import com.boss.hong.user.service.UserService;

/**
 * 收藏
 * @author peiHongWei
 *
 * 2018年7月10日
 */
@RestController
@RequestMapping(value = "/favorite")
public class FavoriteController {

	private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private UserService userService;
	
	/**
	 * 保存收藏关系,收藏名片，收藏店铺，收藏大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@PostMapping(value = "/insertFavorite")
	public ApiResult<Integer> insertFavorite(@RequestBody @Validated FavoriteDto favoriteDto){
		logger.info("FavoriteController insertFavorite param:{}",favoriteDto);
		ApiResult<Integer> apiResult = new ApiResult<Integer>();
		if("01".equals(favoriteDto.getDeliveredToMe())){
			return apiResult.error("传递类型错误。");
		}
		favoriteService.insertFavorite(favoriteDto);
		logger.info("FavoriteController insertFavorite result:{}" , JSONObject.toJSON(apiResult));
		return apiResult;
	}
	
	/**
	 * 保存收藏关系,投递名片
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@PostMapping(value = "/sendCard")
	public ApiResult<Integer> sendCard(@RequestBody @Validated FavoriteDto favoriteDto){
		logger.info("FavoriteController sendCard param:{}",favoriteDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		if("02".equals(favoriteDto.getDeliveredToMe()) || !"02".equals(favoriteDto.getFavoriteType())){
			return apiResult.error("传递类型错误。");
		}
		if(null == favoriteDto.getDeliverUser() || 0 == favoriteDto.getDeliverUser()) {
			return apiResult.error("投递人为空！");
		}
		favoriteService.sendCard(favoriteDto);
		logger.info("FavoriteController sendCard result:{}" , JSONObject.toJSON(apiResult));
		return apiResult;
	}
	
	/**
	 * 修改投递来的名片为自己收藏的
	 * @author peiHongWei
	 * @date 2018年7月11日 下午5:58:44
	 */
	@PostMapping(value = "/updateFavorite/id/{id}/deliveredToMe/{deliveredToMe}")
	public ApiResult<Integer> updateFavorite(@PathVariable Integer id,@PathVariable String deliveredToMe){
		logger.info("FavoriteController updateFavorite param:{}",id);
		FavoriteDto favoriteDto = new FavoriteDto();
		favoriteDto.setId(id);
		favoriteDto.setDeliveredToMe(deliveredToMe);
		Integer loginUserId = userService.getLoginUserIdByWechatId(CookieHelper.getOpenid());
		favoriteDto.setLoginUserId(loginUserId);
		ApiResult<Integer> apiResult = new ApiResult<>();
		favoriteService.updateFavorite(favoriteDto);
		logger.info("FavoriteController updateFavorite result:{}" , JSONObject.toJSON(apiResult));
		return apiResult;
	}
	
	
	/**
	 * 删除收藏关系
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@DeleteMapping(value = "/favorite/id/{id}")
	public ApiResult<Integer> deleteFavorite(@PathVariable Integer id){
		logger.info("FavoriteController deleteFavorite id:{}",id);
		ApiResult<Integer> apiResult = new ApiResult<>();
		FavoriteDto favoriteDto = new FavoriteDto();
		favoriteDto.setId(id);
		favoriteService.deleteFavorite(favoriteDto);
		logger.info("FavoriteController deleteFavorite result:{}" , JSONObject.toJSON(apiResult));
		return apiResult;
	}
	
	/**
	 * 删除收藏关系
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@DeleteMapping(value = "/favorite/favoriteType/{favoriteType}/relationId/{relationId}")
	public ApiResult<Integer> deleteFavoriteByParam(@PathVariable String favoriteType,@PathVariable Integer relationId){
		logger.info("FavoriteController deleteFavorite favoriteType:"+favoriteType+",relationId="+relationId);
		FavoriteDto favoriteDto = new FavoriteDto();
		favoriteDto.setLoginUserId(userService.getLoginUserIdByWechatId(CookieHelper.getOpenid()));
		favoriteDto.setFavoriteType(favoriteType);
		favoriteDto.setRelationId(relationId);
		favoriteDto.setDeliveredToMe("02");
		ApiResult<Integer> apiResult = new ApiResult<>();
		favoriteService.deleteFavorite(favoriteDto);
		logger.info("FavoriteController deleteFavorite result:{}" , JSONObject.toJSON(apiResult));
		return apiResult;
	}
	
	
	/**
	 * 查询收藏店铺关系
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@GetMapping(value = "/favoriteShop")
	public ApiResult<Page<ShopDto>> getFavoriteShop(@RequestParam Integer pageIndex,@RequestParam Integer pageSize){
		logger.info("FavoriteController getFavoriteShop .pageIndex="+pageIndex+",pageSize"+pageSize);
		if(null == pageIndex || 0 == pageIndex) {
			pageIndex = 1;
		}
		if(null == pageSize || 0 == pageSize) {
			pageSize = 10;
		}
		Page<ShopDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		ApiResult<Page<ShopDto>> apiResult = new ApiResult<>();
		page = favoriteService.listFavoriteShop(CookieHelper.getOpenid(),page);
		logger.info("FavoriteController getFavoriteShop result:{}" , JSONObject.toJSON(apiResult));
		return apiResult.ok(page);
	}
	
	/**
	 * 查询收藏店铺
	 * @author peiHongWei
	 * @date 2018年7月12日 下午2:13:48
	 * @param loginUserId 用户主键
	 * @param deliveredToMe 01-投递给我的，02-我收藏的
	 * @return
	 */
	@GetMapping(value = "/favoriteShopCard/deliveredToMe/{deliveredToMe}")
	public ApiResult<Page<ShopDto>> getFavoriteShopCard(@PathVariable String deliveredToMe,
			@RequestParam Integer pageIndex,@RequestParam Integer pageSize ){
		logger.info("FavoriteController getFavoriteShopCard param-deliveredToMe="+deliveredToMe+",pageIndex="+pageIndex+",pageSize="+pageSize);
		if(null == pageIndex || 0 == pageIndex) {
			pageIndex = 1;
		}
		if(null == pageSize || 0 == pageSize) {
			pageSize = 10;
		}
		Page<ShopDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		ApiResult<Page<ShopDto>> apiResult = new ApiResult<>();
		page = favoriteService.listFavoriteShopCard(CookieHelper.getOpenid(), deliveredToMe,page,"02");
		logger.info("FavoriteController getFavoriteShopCard result:"+JSONObject.toJSONString(apiResult));
		return apiResult.ok(page);
	}
	
	/**
	 * 根据姓名检索自己收藏的店铺
	 * @author peiHongWei
	 * @date 2018年7月27日 下午4:02:37
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value = "/getFavoriteShops/name/{name}")
	public ApiResult<Page<ShopDto>> getFavoriteShops(@PathVariable String name,
			@RequestParam Integer pageIndex,@RequestParam Integer pageSize ){
		logger.info("FavoriteController getFavoriteShopCard param-name="+name+",pageIndex="+pageIndex+",pageSize="+pageSize);
		if(null == pageIndex || 0 == pageIndex) {
			pageIndex = 1;
		}
		if(null == pageSize || 0 == pageSize) {
			pageSize = 10;
		}
		Page<ShopDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		ApiResult<Page<ShopDto>> apiResult = new ApiResult<>();
		page = favoriteService.listShopByName(page, CookieHelper.getOpenid(), name);
		logger.info("FavoriteController getFavoriteShops result:"+JSONObject.toJSONString(apiResult));
		return apiResult.ok(page);
	}
	
	/**
	 * 根据姓名检索自己收藏的店铺
	 * @author peiHongWei
	 * @date 2018年7月27日 下午4:02:37
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value = "/getFavoriteOutlines/name/{name}")
	public ApiResult<Page<OutlineDto>> getFavoriteOutlines(@PathVariable String name,
			@RequestParam Integer pageIndex,@RequestParam Integer pageSize ){
		logger.info("FavoriteController getFavoriteShopCard param-name="+name+",pageIndex="+pageIndex+",pageSize="+pageSize);
		if(null == pageIndex || 0 == pageIndex) {
			pageIndex = 1;
		}
		if(null == pageSize || 0 == pageSize) {
			pageSize = 10;
		}
		Page<OutlineDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		ApiResult<Page<OutlineDto>> apiResult = new ApiResult<>();
		page = favoriteService.listOutlineByName(page, CookieHelper.getOpenid(), name);
		logger.info("FavoriteController getFavoriteShops result:"+JSONObject.toJSONString(apiResult));
		return apiResult.ok(page);
	}
	
	/**
	 * 查询收藏大纲
	 * @author peiHongWei
	 * @date 2018年7月12日 下午2:16:37
	 * @param loginUserId
	 * @return
	 */
	@GetMapping(value = "/favoriteOutline")
	public ApiResult<Page<OutlineDto>> getFavoriteOutline(@RequestParam Integer pageIndex,@RequestParam Integer pageSize ){
		logger.info("FavoriteController getFavoriteOutline param-pageIndex:"+pageIndex+",pageSize"+pageSize);
		if(null == pageIndex || 0 == pageIndex) {
			pageIndex = 1;
		}
		if(null == pageSize || 0 == pageSize) {
			pageSize = 10;
		}
		Page<OutlineDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		ApiResult<Page<OutlineDto>> apiResult =  new ApiResult<>();
		page = favoriteService.listFavoriteOutline(CookieHelper.getOpenid(),page);
		logger.info("FavoriteController getFavoriteOutline result:"+JSONObject.toJSONString(apiResult));
		return apiResult.ok(page);
	}
	
	
}
