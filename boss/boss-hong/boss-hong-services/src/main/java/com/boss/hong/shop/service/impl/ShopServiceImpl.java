package com.boss.hong.shop.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.boss.common.beans.Page;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boss.common.constant.Constants;
import com.boss.common.dto.document.DocumentInfoDto;
import com.boss.common.dto.shop.LabelDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.common.dto.shop.ShopDto;
import com.boss.common.dto.user.LoginUserDto;
import com.boss.common.util.StringUtils;
import com.boss.hong.base.service.BusinessService;
import com.boss.hong.base.service.CommonService;
import com.boss.hong.common.query.OutlineQuery;
import com.boss.hong.document.service.DocumentInfoService;
import com.boss.hong.favorite.service.FavoriteService;
import com.boss.hong.shop.entity.LabelDO;
import com.boss.hong.shop.entity.OutlineDO;
import com.boss.hong.shop.entity.ShopDO;
import com.boss.hong.shop.entity.VisitRecordDO;
import com.boss.hong.shop.mapper.ShopMapper;
import com.boss.hong.shop.service.ShopService;
import com.boss.hong.user.service.UserService;
import com.github.pagehelper.PageHelper;

/**
 * 店铺相关接口实现
 * @author peihongwei
 *
 * 2018年7月6日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopServiceImpl implements ShopService{

	private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private DocumentInfoService documentInfoService;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private UserService userService;
	@Autowired
	private BusinessService businessService;
			

	@Override
	public ShopDto getShop(ShopDto shopDto,String wechatId) {
		logger.info("ShopServiceImpl getShop param:" + shopDto);
		ShopDO shopDoForQuery = new ShopDO();
		BeanUtils.copyProperties(shopDto, shopDoForQuery);
		List<ShopDO> shopList = commonService.dynamicQuery(shopDoForQuery);
		ShopDto returnShopDto = new ShopDto();
		if(CollectionUtils.isNotEmpty(shopList)) {
			BeanUtils.copyProperties(shopList.get(0), returnShopDto);
			this.translateShopDto(returnShopDto);
			returnShopDto = this.getFavRelation(returnShopDto, wechatId);
			logger.info("ShopServiceImpl getShop returnShopDto:{}" + returnShopDto);
		}
//		logger.info("ShopServiceImpl getShop result:{}" + result);
		return returnShopDto;
	}
	
	
	@Override
	public void insertVisitRecord(Integer relationId,String visitWechatId,String visitType) {
		logger.info("ShopServiceImpl insertVisitRecord relationId=" +relationId+",visitWechatId="+visitWechatId+",visitType="+visitType);
		VisitRecordDO visitRecordDo = new VisitRecordDO();
		visitRecordDo.setCreatedTime(new Date());
		visitRecordDo.setRelationId(relationId);
		visitRecordDo.setUpdatedTime(new Date());
		visitRecordDo.setVisitType(visitType);
		visitRecordDo.setWechatId(visitWechatId);
		commonService.insert(visitRecordDo);
		logger.info("ShopServiceImpl insertVisitRecord end.");
	}
	
	
	@Override
	public void translateShopDto(ShopDto shopDto) {
		logger.info("ShopServiceImpl translateShopDto start,param:"+JSONObject.toJSONString(shopDto,SerializerFeature.DisableCircularReferenceDetect));
		LabelDO labelDo = new LabelDO();
		labelDo.setShopId(shopDto.getId());
		List<LabelDto> labelDtos = shopMapper.listLabelByShopId(shopDto.getId());
		shopDto.setLabelDtos(labelDtos);
		shopDto.setCityName(businessService.cityCodeToName(shopDto.getCityCode()));
		
	}
	
	
	@Override
	public ShopDto getFavRelation(ShopDto shopDto,String wechatId) {
		////获取微信头像
		shopDto.setAvatarUrl(this.getAvataUrl(shopDto.getId()));
		//获取该店铺跟当前登录人的收藏关系
		Integer loginUserId = userService.getLoginUserIdByWechatId(wechatId);
		Integer isFavShop = favoriteService.getFavoriteRelation(loginUserId, "01", shopDto.getId());
		Integer isFavCard = favoriteService.getFavoriteRelation(loginUserId, "02", shopDto.getId());
		shopDto.setIsFavCard(isFavCard);
		shopDto.setIsFavShop(isFavShop);
		return shopDto;
	}


	@Override
	public Integer deleteShop(ShopDto shopDto) {
		logger.info("ShopServiceImpl deleteShop param:" + shopDto);
		ShopDO shopDo = new ShopDO();
		BeanUtils.copyProperties(shopDto, shopDo);
		Integer count = commonService.delete(shopDo);
		LabelDO labelDO = new LabelDO();
		labelDO.setShopId(shopDto.getId());
		commonService.delete(labelDO);
		OutlineDO outlineDo = new OutlineDO();
		outlineDo.setShopId(shopDto.getId());
		commonService.delete(outlineDo);
		logger.info("ShopServiceImpl deleteShop result:{}" ,count);
		return count;
	}

	@Override
	public Integer updatedShopAllFields(ShopDto shopDto,String wechatId) {
		logger.info("ShopServiceImpl updatedShopById param:" + shopDto);
		this.insertShopLabel(shopDto.getShopLabelList(),shopDto.getId(),false);
		ShopDO shopDO = new ShopDO();
		BeanUtils.copyProperties(shopDto, shopDO);
		shopDO.setUpdatedTime(new Date());
		int count = commonService.updateObjFields(shopDO, "updatedTime,loginUserId,shopName,"
				+ "userName,cityCode,companyName,wechatId,phoneNumber,isPublicity","id = ?",shopDto.getId());
		logger.info("ShopServiceImpl updatedShopById result:{}" , count);
		return count;
	}
	

	@Override
	public Integer insertShop(ShopDto shopDto) {
		logger.info("ShopServiceImpl insertShop param:"+shopDto);
		ShopDO shopDo = new ShopDO();
		BeanUtils.copyProperties(shopDto, shopDo);
		//查询是否有店铺，如果有，则不允许新增店铺
		Integer loginUserId = shopDto.getLoginUserId();
		ShopDO shopDoForQuery = new ShopDO();
		shopDoForQuery.setLoginUserId(loginUserId);
		List<ShopDO> shopDOs = commonService.dynamicQuery(shopDoForQuery);
		if(CollectionUtils.isNotEmpty(shopDOs)) {
//			return result.error("该用户已经存在店铺，不允许再新增店铺。");
		}
		Integer count = shopMapper.insertShopAndReturnId(shopDo);
		this.insertShopLabel(shopDto.getShopLabelList(),shopDo.getId(),true);
		logger.info("ShopServiceImpl insertShop result:{}" , count);
		return count;
	}

	private void insertShopLabel(List<String> shopLabel,Integer shopId,boolean isInsert) {
		if(!isInsert) {
			LabelDO labelDo = new LabelDO();
			labelDo.setShopId(shopId);
			commonService.delete(labelDo);
		}
		List<LabelDO> labelDoList = new ArrayList<LabelDO>();
		LabelDO shoplabelDo = null;
		for (String string : shopLabel) {
			shoplabelDo = new LabelDO();
			shoplabelDo.setShopId(shopId);
			shoplabelDo.setShopLabel(string);
			labelDoList.add(shoplabelDo);
		}
		shopMapper.insertShopLabelBatch(labelDoList);
	}

	@Override
	public Integer deleteShopOutline(OutlineDto outlineDto) {
		logger.info("ShopServiceImpl deleteOutline param:" + outlineDto);
		OutlineDO outlineDo = new OutlineDO();
		BeanUtils.copyProperties(outlineDto, outlineDo);
		Integer count = commonService.delete(outlineDo);
		logger.info("ShopServiceImpl deleteOutline result:" + count);
		return count;
	}

	@Override
	public Integer inserOutline(OutlineDto outlineDto) {
		logger.info("ShopServiceImpl inserOutline param:" + outlineDto);
		OutlineDO outlineDo = new OutlineDO();
		BeanUtils.copyProperties(outlineDto, outlineDo);
		//插入店铺产品大纲后需要拿到主键，关联update到附件表
		Integer count = shopMapper.insertOutlineAndReturnId(outlineDo);
		Integer outlineId = outlineDo.getId();
		logger.info("ShopServiceImpl inserOutline insertOutlineAndReturnId id:" , outlineId);
		List<DocumentInfoDto> documentInfoDtos = outlineDto.getDocumentInfoDtos();
		for (DocumentInfoDto documentInfoDto : documentInfoDtos) {
			documentInfoDto.setIdForeignKey(outlineId);
		}
		documentInfoService.updateBatchDocumentInfo(documentInfoDtos);
		logger.info("ShopServiceImpl inserOutline result:" + count);
		return count;
	}
	
	@Override
	public Object transpondShop(Integer shopId) {
		logger.info("ShopServiceImpl transpondShop param-shopId:{}" , shopId);
		ShopDO shopDoForQuery = new ShopDO();
		shopDoForQuery.setId(shopId);
		List<ShopDO> shopList = commonService.dynamicQuery(shopDoForQuery);
		if(CollectionUtils.isNotEmpty(shopList)) {
			Integer transpond = shopList.get(0).getTranspondTimes();
			shopDoForQuery.setTranspondTimes(++transpond);
			shopDoForQuery.setUpdatedTime(new Date());
			int count = commonService.updateObjFields(shopDoForQuery, "updatedTime,transpondTimes","id = ?",shopId);
			logger.info("ShopServiceImpl transpondShop result:{}" , count);
//			if(count == 0) {
//				return result.error(ReturnMsg.TRANSPOND_ERROR);
//			}
		}/*else {
			return result.error(ReturnMsg.TRANSPOND_ERROR);
		}*/
		return 0;
	}
	

	@Override
	public Integer updateShopOutline(OutlineDto outlineDto) {
		logger.info("ShopServiceImpl updateOutline param:" + outlineDto);
		OutlineDO outlineDo = new OutlineDO();
		BeanUtils.copyProperties(outlineDto, outlineDo);
		outlineDo.setUpdatedTime(new Date());
		Integer count = commonService.updateObjFields(outlineDo, "shopId,outlineContent", "id=?", outlineDo.getId());
		logger.info("ShopServiceImpl updateOutline result:" + count);
		return count;
	}


	@Override
	public Integer countOutlineByShopId(Integer shopId) {
		logger.info("ShopServiceImpl countOutlineByShopId param-shopId:{}",shopId);
		Integer count  = shopMapper.countOutlineByShopId(shopId);
		logger.info("ShopServiceImpl countOutlineByShopId result:{}",count);
		return count;
	}

	@Override
	public Integer countShopVisit(Integer loginUserId) {
		logger.info("ShopServiceImpl countShopVisit param-loginUserId:{}",loginUserId);
		ShopDO shopDoForQuery = new ShopDO();
		shopDoForQuery.setLoginUserId(loginUserId);
		List<ShopDO> shopList = commonService.dynamicQuery(shopDoForQuery);
		if(CollectionUtils.isNotEmpty(shopList)) {
			VisitRecordDO visitRecordDo = new VisitRecordDO();
			visitRecordDo.setVisitType(Constants.VISIT_TYPE_SHOP);
			visitRecordDo.setRelationId(shopList.get(0).getId());
			Integer count = shopMapper.getShopVisitCount(visitRecordDo);
		}else {
//			result.error("该用户还没有店铺！");
		}
//		logger.info("ShopServiceImpl countShopVisit result:{}",result);
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<OutlineDto> listOutlineByShopId(Integer shopId,Page<OutlineDto> page,String wechatId) {
		OutlineQuery outline = new OutlineQuery();
		outline.setShopId(shopId);
		outline.setIdAsc(false);
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<OutlineQuery> outlineQueries = commonService.dynamicQuery(outline);
		page.setTotalSize(pagehelper.getTotal());
		List<OutlineDto> outlineDtoList = JSON.parseArray(JSON.toJSONString(outlineQueries), OutlineDto.class);
		page.setList(transOutlineDto(outlineDtoList, wechatId));
		return page;
	}
	
	
	private List<OutlineDto> transOutlineDto (List<OutlineDto> outlineDtoList,String wechatId){
		DocumentInfoDto documentInfoDto = new DocumentInfoDto();
		for (OutlineDto dto:outlineDtoList) {
			//获取跟当前登录人的收藏关系
			Integer isFavorite = favoriteService.getFavoriteRelation(userService.getLoginUserIdByWechatId(wechatId), "03", dto.getId());
			dto.setIsFavorite(isFavorite);
			documentInfoDto.setIdForeignKey(dto.getId());
			List<DocumentInfoDto> documentInfoDtoList = documentInfoService.listDocumentInfo(documentInfoDto);
			logger.info("documentInfoDtoList:{}",JSON.toJSONString(documentInfoDtoList));
			if(null == documentInfoDtoList || documentInfoDtoList.isEmpty()){
				continue ;
			}
			/*for (DocumentInfoDto documentDto:documentInfoDtoList) {
				// 附件地址加上文件服务器前缀
				if(org.apache.commons.lang.StringUtils.isNotBlank(documentDto.getFilePath())){
					documentDto.setFilePath(ConfigUtils.getValue(ConfigConst.ATTACHMENT_DOMAIN) + documentDto.getFilePath());
				}
			}*/
			dto.setDocumentInfoDtos(documentInfoDtoList);
		}
		return outlineDtoList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<ShopDto> listOutlineByCity(String city, Page<ShopDto> page,String wechatId) {
		ShopDO shop = new ShopDO();
		shop.setCityCode(city);
		
		// 根据发布时间倒序显示某个城市的产品大纲
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<OutlineDto> outlineDtoList = shopMapper.listOutlineByShop(shop);
		outlineDtoList = transOutlineDto(outlineDtoList, wechatId);
		
		// 查询产品大纲归属的店铺信息
		List<ShopDto> shopDtoList = new ArrayList<>(outlineDtoList.size());
		for (OutlineDto outlineDto:outlineDtoList) {
			ShopDto shopDto = new ShopDto();
			shopDto.setId(outlineDto.getShopId());
			shopDto = this.getShop(shopDto, wechatId);
			Page<OutlineDto> outlineDtoPage = new Page<>();
			outlineDtoPage.setPageIndex(page.getPageIndex());
			outlineDtoPage.setPageSize(page.getPageSize());
			outlineDtoPage.setTotalSize(pagehelper.getTotal());
			outlineDtoPage.setList(Arrays.asList(outlineDto));
			shopDto.setOutlineDtoPage(outlineDtoPage);
			shopDtoList.add(shopDto);
		}
		page.setList(shopDtoList);
		return page;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page<ShopDto> listShopByCity(String city, Page<ShopDto> page,String wechatId) {
		logger.info("ShopServiceImpl listShopByCity"+city+",wechatId="+wechatId);
		ShopDO shop = new ShopDO();
		shop.setCityCode(city);
		com.github.pagehelper.Page pagehelper = PageHelper.startPage(page.getPageIndex(),page.getPageSize());
		List<ShopDO> outlineDoList = commonService.dynamicQuery(shop);
		List<ShopDto> shopDtoList = new ArrayList<>();
		for (ShopDO shopDO : outlineDoList) {
			ShopDto shopDto = new ShopDto();
			BeanUtils.copyProperties(shopDO, shopDto);
			this.translateShopDto(shopDto);
			shopDto = this.getFavRelation(shopDto, wechatId);
			this.setOutlineAndAvatarUrlForShop(shopDto, wechatId);
		}
		page.setTotalSize(pagehelper.getTotal());
		page.setList(shopDtoList);
		return page;
	}
	
	@Override
	public void setOutlineAndAvatarUrlForShop(ShopDto shopDto,String wechatId) {
		Page<OutlineDto> outLinePage = new Page<>();
		outLinePage.setPageIndex(1);
		outLinePage.setPageSize(10);
		Page<OutlineDto> OutlineDtoPage = this.listOutlineByShopId(shopDto.getId(), outLinePage, wechatId);
//		//查询大纲条数；
		shopDto.setOutlineDtoPage(OutlineDtoPage);
		//获取微信头像
		shopDto.setAvatarUrl(this.getAvataUrl(shopDto.getId()));
	}
	
	@SuppressWarnings("unchecked")
	private String getAvataUrl(Integer shopId) {
		ShopDto dto = this.getShopByID(shopId);
		LoginUserDto userDto = userService.getLoginUserById(dto.getLoginUserId());
		if(userDto != null && StringUtils.isNotEmpty(userDto.getWechatUserInfo())) {
			Map<String,Object> wechatUserInfoMap = JSONObject.parseObject(userDto.getWechatUserInfo(), Map.class);
			return String.valueOf(wechatUserInfoMap.get("avatarUrl"));
		}
		return null;
	}


	@Override
	public ShopDto getShop(String wechatId) {
		ShopDO shopDoForQuery = new ShopDO();
		shopDoForQuery.setLoginUserId(userService.getLoginUserIdByWechatId(wechatId));
		List<ShopDO> shopList = commonService.dynamicQuery(shopDoForQuery);
		ShopDto shopDto = new ShopDto();
		if(CollectionUtils.isNotEmpty(shopList)) {
			BeanUtils.copyProperties(shopList.get(0), shopDto);
			return shopDto;
		}
		return null;
	}
	
	
	private ShopDto getShopByID(Integer shopID) {
		ShopDO shopDoForQuery = new ShopDO();
		shopDoForQuery.setId(shopID);
		List<ShopDO> shopList = commonService.dynamicQuery(shopDoForQuery);
		ShopDto shopDto = new ShopDto();
		if(CollectionUtils.isNotEmpty(shopList)) {
			BeanUtils.copyProperties(shopList.get(0), shopDto);
		}
		return shopDto;
	}

}
