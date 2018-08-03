package com.boss.hong.shop.web;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boss.common.annotation.BindPhone;
import com.boss.common.beans.ApiResult;
import com.boss.common.beans.Page;
import com.boss.common.constant.Constants;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;
import com.boss.common.util.CookieHelper;
import com.boss.hong.shop.service.ShopService;
import com.boss.hong.user.service.UserService;

/**
 * 店铺
 * @author peihongwei
 *
 * 2018年7月6日
 */
@RestController
@RequestMapping(value = "/shop")
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
	@Autowired
	private ShopService shopService;
	@Autowired
	private UserService userService;
	
	/**
	 * 查询店铺 
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@GetMapping(value = "/getShop/shopId/{shopId}")
	@BindPhone(mustBind = false)
	public ApiResult<ShopDto> getShopById(@PathVariable Integer shopId) {
		logger.info("ShopController getShopById");
		ApiResult<ShopDto> apiResult = new ApiResult<>();
		ShopDto dto = new ShopDto();
		dto.setId(shopId);
		String wechatId = CookieHelper.getOpenid();
		shopService.getShop(dto,wechatId);
		//别人访问店铺时，增加访问次数
		shopService.insertVisitRecord(shopId, wechatId, Constants.VISIT_TYPE_SHOP);
		logger.info("ShopController getShopById result:" +JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	
	
	/**
	 * 根据登录微信信息查询该微信账号下店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
    @GetMapping(value = "/getShop")
	@BindPhone(mustBind = false)
	public ApiResult<ShopDto> getShop(){
		logger.info("ShopController getShop .");
		ApiResult<ShopDto> apiResult = new ApiResult<>();
		ShopDto dto = new ShopDto();
		String wechatId = CookieHelper.getOpenid();
		Integer loginUserId = userService.getLoginUserIdByWechatId(wechatId);
		dto.setLoginUserId(loginUserId);
		shopService.getShop(dto,wechatId);
		logger.info("ShopController getShop result:{}" + JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	/**
	 * 删除店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@DeleteMapping(value = "/deleteShop")
	public ApiResult<Integer> deleteShopById(){
		logger.info("ShopController deleteShopById");
		ApiResult<Integer> apiResult = new ApiResult<>();
		ShopDto shopDto = new ShopDto();
		shopDto.setId(this.getShopId());
		shopService.deleteShop(shopDto);
		logger.info("ShopController deleteShopById result:{}" + JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	/**
	 * 修改店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@PostMapping(value = "/updateShopById")
	public ApiResult<Integer> updatedShopById(@RequestBody @Validated ShopDto shopDto){
		logger.info("ShopController updatedShopById param:{}",shopDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		if(CollectionUtils.isEmpty(shopDto.getShopLabelList())) {
			return apiResult.error("店铺标签必选。");
		}
		if(shopDto.getShopLabelList().size() > 3) {
			return apiResult.error("店铺标签不得超过三条。");
		}
		shopDto.setId(this.getShopId());
		shopService.updatedShopAllFields(shopDto,CookieHelper.getOpenid());
		logger.info("ShopController updatedShopById result:" + JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	
	/**
	 * 获取shopID
	 * @author peiHongWei
	 * @date 2018年7月26日 下午3:43:25
	 * @return
	 */
	private Integer getShopId() {
		ShopDto shopDto = shopService.getShop(CookieHelper.getOpenid());
		if(null != shopDto) {
			Integer shopId = shopDto.getId();
			return shopId;
		}
		return 0;
	}
	/**
	 * 保存店铺
	 * @author peiHongWei
	 * @date 2018年7月9日 下午3:23:46
	 */
	@PostMapping(value = "/insertShop")
	public ApiResult<Integer> insertShop(@RequestBody @Validated ShopDto shopDto){
		logger.info("ShopController insertShop param:{}",shopDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		if(CollectionUtils.isEmpty(shopDto.getShopLabelList())) {
			return apiResult.error("店铺标签必选。");
		}
		if(shopDto.getShopLabelList().size() > 3) {
			return apiResult.error("店铺标签不得超过三条。");
		}
		String wechatId = CookieHelper.getOpenid();
		Integer loginUserId = userService.getLoginUserIdByWechatId(wechatId);
		shopDto.setLoginUserId(loginUserId);
		shopService.insertShop(shopDto);
		logger.info("ShopController insertShop result:{}" , apiResult);
		return apiResult;
	}
	
	
	/**
	 * 删除产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	@DeleteMapping(value = "/shopOutline/byId/{id}")
	public ApiResult<Integer> deleteShopOutline(@PathVariable Integer id){
		logger.info("ShopController deleteShopOutline param:{}",id);
		ApiResult<Integer> apiResult = new ApiResult<>();
		OutlineDto outlineDto = new OutlineDto();
		outlineDto.setId(id);
		shopService.deleteShopOutline(outlineDto);
		logger.info("ShopController deleteShopOutline result:{}" + JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	
	/**
	 * 店铺转发，需要添加店铺转发次数以及修改转发时间
	 * @author peiHongWei
	 * @date 2018年7月10日 下午5:58:23
	 */ 
	@PostMapping(value = "/transpondShop")
	public ApiResult<Object> transpondShop(){
		logger.info("ShopController transpondShop");
		ApiResult<Object> apiResult = new ApiResult<>();
		shopService.transpondShop(this.getShopId());
		logger.info("ShopController transpondShop result:{}" , apiResult);
		return apiResult;
	}
	
	
	
	/**
	 * 新增产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	@PostMapping(value = "/insertOutline")
	public ApiResult<Integer> inserShopOutline(@RequestBody OutlineDto outlineDto){
		logger.info("ShopController inserShopOutline param:{}",outlineDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		outlineDto.setShopId(this.getShopId());
		shopService.inserOutline(outlineDto);
		logger.info("ShopController inserShopOutline result:{}" , apiResult);
		return apiResult;
	}
	/**
	 * 修改产品大纲
	 * @author peiHongWei
	 * @date 2018年7月9日 下午6:36:55
	 */
	@PostMapping(value = "/updateOutline")
	public ApiResult<Integer> updateOutlineById(@RequestBody OutlineDto outlineDto){
		logger.info("ShopController updateOutline param:{}",outlineDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		outlineDto.setShopId(this.getShopId());
		shopService.updateShopOutline(outlineDto);
		logger.info("ShopController updateOutline result:" + JSONObject.toJSONString(apiResult));
		return apiResult;
	}
	
	
	/**
	 * 根据店铺id查询某个店铺的产品大纲
	 * @param shopId 店铺id
	 * @param pageIndex 当前页码
	 * @param pageSize 每页条数
	 */
	@BindPhone(mustBind = false)
	@GetMapping(value = "/listOutline")
	public ApiResult<Page<OutlineDto>> listOutline(Integer pageIndex,Integer pageSize){
		logger.info("ShopController listOutlineByShopId :pageIndex="+pageIndex+",pageSize="+pageSize);
		ApiResult<Page<OutlineDto>> re = new ApiResult<>();
		String openid = CookieHelper.getOpenid();
		logger.info("openid:{}",openid);
		Page<OutlineDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		Page<OutlineDto> result = shopService.listOutlineByShopId(this.getShopId(),page,openid);
		return re.ok(result);
	}
	
	/**
	 * 根据店铺id查询某个店铺的产品大纲
	 * @param shopId 店铺id
	 * @param pageIndex 当前页码
	 * @param pageSize 每页条数
	 */
	@BindPhone(mustBind = false)
	@GetMapping(value = "/listOutline/shopId/{shopId}")
	public ApiResult<Page<OutlineDto>> listOutlineByShopId(@PathVariable Integer shopId,Integer pageIndex,Integer pageSize){
		logger.info("ShopController listOutlineByShopId :shopId:"+shopId+",pageIndex="+pageIndex+",pageSize="+pageSize);
		ApiResult<Page<OutlineDto>> result = new ApiResult<>();
		String openid = CookieHelper.getOpenid();
		logger.info("openid:{}",openid);
		Page<OutlineDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		Page<OutlineDto> resultPage = shopService.listOutlineByShopId(shopId,page,openid);
		return result.ok(resultPage);
	}
	
	/**
	 * 根据国标码查询某个城市的产品大纲
	 * @param city 城市国标码
	 * @param pageIndex 当前页码
	 * @param pageSize 每页条数
	 */
	@BindPhone(mustBind = false)
	@GetMapping(value = "/listOutline/city/{city}")
	public ApiResult<Page<ShopDto>> listOutlineByCity(@PathVariable String city,
			Integer pageIndex,Integer pageSize){
		logger.info("ShopController listOutlineByCity city:"+city+",pageIndex="+pageIndex+",pageSize="+pageSize);
		ApiResult<Page<ShopDto>> result = new ApiResult<>();
		Page<ShopDto> page = new Page<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		Page<ShopDto> resultPage = shopService.listOutlineByCity(city,page,CookieHelper.getOpenid());
		logger.info("ShopController listOutlineByCity result:"+JSONObject.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect));
		return result.ok(resultPage);
	}
	
	/**
	 * 根据国标码查询某个城市的产品大纲
	 * @param city 城市国标码
	 * @param pageIndex 当前页码
	 * @param pageSize 每页条数
	 */
	@BindPhone(mustBind = false)
	@GetMapping(value = "/listShopByCity/city/{city}")
	public ApiResult<Page<ShopDto>> listShopByCity(@PathVariable String city,
			Integer pageIndex,Integer pageSize){
		logger.info("ShopController listShopByCity city:"+city+",pageIndex="+pageIndex+",pageSize="+pageSize);
		ApiResult<Page<ShopDto>> result = new ApiResult<>();
		Page<ShopDto> page = new Page<ShopDto>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		Page<ShopDto> resultPage = shopService.listShopByCity(city,page,CookieHelper.getOpenid());
		return result.ok(resultPage);
	}
	
}
