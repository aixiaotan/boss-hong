package com.boss.hong.base.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.boss.common.annotation.BindPhone;
import com.boss.common.beans.ApiResult;
import com.boss.common.dto.common.SysDistDto;
import com.boss.hong.base.service.BusinessService;

/**
 * 用户
 * @author peihongwei
 *
 * 2018年7月6日
 */
@RestController
@RequestMapping(value = "/business")
public class BusinessController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);
	
	@Autowired
	private BusinessService businessService;
	
	/**
	 * 查询数据字典列表
	 * @param dataTypes 数据字典列表,查询多种类型时以逗号分隔,如 "type1,type2"
	 * @return map key为数据类型,value为字典列表(list)
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/listSysDist")
	@BindPhone(mustBind = false)
	public ApiResult<Map<String, List<SysDistDto>>> listSysDist(String dataTypes) {
		logger.info("ShopController listSysDist,dataTypes:{}",dataTypes);
		ApiResult<Map<String, List<SysDistDto>>> apiResult = new ApiResult<Map<String, List<SysDistDto>>>();
		if(StringUtils.isBlank(dataTypes)){
			return apiResult.error("参数不能为空");
		}
		Map<String, List<SysDistDto>> a = businessService.listSysDist(Arrays.asList(dataTypes.split(",")));
		logger.info("ShopController listSysDist result:" +JSONObject.toJSONString(apiResult));
		return apiResult.ok(a);
	}
}
