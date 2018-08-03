package com.boss.hong.base.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boss.common.beans.ApiResult;
import com.boss.common.dto.common.CarThreeValueEntity;
import com.boss.common.util.BASE64Utils;
import com.boss.common.util.BigDataAPI;
import com.boss.common.util.CookieHelper;
import com.boss.common.util.OcrApiUtils;
import com.boss.common.util.WechatApiPropertiesUtil;
import com.boss.hong.wechat.web.Base64;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**  
* <p>Title: CarThreeValue</p>  
* <p>Description:车300 </p>  
*/  
@RestController
@RequestMapping(value = "/carThree")
public class CarThreeController {

	private static Logger logger = LoggerFactory.getLogger(CarThreeController.class);  
	
	/**   
	 * <p>Title: getCarByVin</p>   
	 * <p>Description:大数据提供新接口:根据车架号获取车型列表 </p>    
	 * @param vin
	 * @return      
	 * @return: JSONObject      
	 * @throws   
	 */
	@ApiOperation(value = "大数据提供新接口:根据车架号获取车型列表")
	@ApiImplicitParam(name = "vin", value = "vin", required = true, paramType="path",dataType = "String")
	@PostMapping(value = "/getCarByVin/{vin}")
	public ApiResult<Object> getCarByVin(@PathVariable String vin) {
		ApiResult<Object> rs = new ApiResult<>();
		logger.info("CarThreeController getCarByVin vin:{}",vin);
	    String carthree_url = WechatApiPropertiesUtil.getConfig("carthreeType.url");  //车300价值评估接口地址
	    String bizType = WechatApiPropertiesUtil.getConfig("carthreeType.bizType");  //业务类型
	    String method = WechatApiPropertiesUtil.getConfig("carthreeType.method");  //接口名称
	    String version = WechatApiPropertiesUtil.getConfig("carthreeType.version");  //接口版本号
	     
	    JSONObject json = new JSONObject();
		json.put("vin", vin);
		Map<String,Object> rmap = new HashMap<String,Object>(); 
		rmap.put("bizType", bizType);
		rmap.put("method", method);
		rmap.put("version", version);
		rmap.put("params", json);
		logger.info("doJsonPostHttpWithHeader params: " + JSON.toJSONString(rmap));
		String result = BigDataAPI.doJsonPostHttpWithHeader(carthree_url, rmap);
		logger.info("doJsonPostHttpWithHeader result: " + result);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson == null) {
			logger.info("根据车架号获取车型列表异常！返回数据为空。");
			rs.error("根据VIN获取车辆信息失败");
		}else if ("200".equals(resultJson.getString("code"))) {
			rs.ok(resultJson.getJSONArray("data"));
		}else {
			logger.info("根据车架号获取车型列表异常！返回数据为:" + resultJson.getString("msg"));
			rs.error(resultJson.getString("msg"));
		}
		
		return rs;
		
	}
	
	/**
	 * 
	 * @param file
	 * @param fileType
	 * @param openid
	 * @return
	 */
	@ApiOperation(value = "上传文件")
	@PostMapping(value = "/uploadPhoto")
	public ApiResult<Object> upload(MultipartFile file,String fileType) {
		ApiResult<Object> result = new ApiResult<>();
		logger.info("fileName:{} , fileSize:{}, openid:{}", file.getOriginalFilename(), file.getSize(), CookieHelper.getOpenid());
		try {
			result = OcrApiUtils.vehicleLicenseOcr(BASE64Utils.encode(file.getBytes()));
		} catch (IOException e) {
			logger.warn("file tranfer to byte exception.", e);
		}
		return result;
	}
	
	/**
	 * 大数据提供新接口:根据车型号等返回评估价值
	 * @param carThreeValueEntity
	 * @return
	 */
	@ApiOperation(value = "根据车型号等返回评估价值")
	@ApiImplicitParam(name = "carThreeValueEntity", value = "车辆信息实体", required = true ,paramType="body", dataType = "CarThreeValueEntity")
	@PostMapping(value = "/carThreeValue")
	public ApiResult<Object> carThreeValue(@RequestBody CarThreeValueEntity carThreeValueEntity){
		ApiResult<Object> rs = new ApiResult<>();
		logger.info("carThreeValue ==> params carThreeValueEntity: " + JSON.toJSONString(carThreeValueEntity));
	    String carthreeValue_url = WechatApiPropertiesUtil.getConfig("carthreeValue.url");  //车300价值评估接口地址
	    String bizType = WechatApiPropertiesUtil.getConfig("carthreeValue.bizType");  //业务类型
	    String method = WechatApiPropertiesUtil.getConfig("carthreeValue.method");  //接口名称
	    String version = WechatApiPropertiesUtil.getConfig("carthreeValue.version");  //接口版本号
	     
	    JSONObject json = (JSONObject) JSONObject.toJSON(carThreeValueEntity);
		
		Map<String,Object> rmap = new HashMap<String,Object>(); 
		rmap.put("bizType", bizType);
		rmap.put("method", method);
		rmap.put("version", version);
		rmap.put("params", json);
		logger.info("carThreeValue doJsonPostHttpWithHeader ==> params: " + JSON.toJSONString(rmap));
		String result = BigDataAPI.doJsonPostHttpWithHeader(carthreeValue_url,rmap);
		logger.info("carThreeValue doJsonPostHttpWithHeader ==> result: " + result);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson == null) {
			logger.info("车辆评估异常！返回数据为空。");
			rs.error("车辆评估异常");
		}else if ("200".equals(resultJson.getString("code"))) {
			rs.ok(resultJson.getJSONObject("data"));
		}else {
			logger.info("车辆评估异常！ carThreeValue ==> result: " + resultJson.getString("msg"));
			rs.error(resultJson.getString("msg"));
		}
		
		return rs;
	 }
	

	/**   
	 * <p>Title: trafficViolation</p>   
	 * <p>Description:交通违章查询接口 </p>    
	 * @param carNumber 车牌号
	 * @param carCode 车架号
	 * @param carDriverNumber 发动机号	
	 * @return      
	 * @return: Object      
	 * @throws   
	 */
	@ApiOperation(value = "交通违章查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "carNumber", value = "车牌号", required = false ,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "carCode", value = "车架号", required = false ,paramType="query", dataType = "String"),
		@ApiImplicitParam(name = "carDriverNumber", value = "发动机号", required = false ,paramType="query", dataType = "String")
	})
	@PostMapping(value = "/trafficViolation")
	public  Object trafficViolation(String carNumber,String carCode,String carDriverNumber){
		ApiResult<Object> rs = new ApiResult<Object>();
		logger.info(" trafficViolation ==> params carNumber:{},carCode:{},carDriverNumber:{} " ,carNumber,carCode,carDriverNumber);
	    String trafficviolation_url = WechatApiPropertiesUtil.getConfig("trafficviolation.url");  //车300价值评估接口地址
	    String bizType = WechatApiPropertiesUtil.getConfig("trafficviolation.bizType");  //业务类型
	    String method = WechatApiPropertiesUtil.getConfig("trafficviolation.method");  //接口名称
	    String version = WechatApiPropertiesUtil.getConfig("trafficviolation.version");  //接口版本号
	     
	    JSONObject json = new JSONObject();
	    json.put("carNumber", carNumber);
	    json.put("carCode", carCode);
	    json.put("carDriverNumber", carDriverNumber);
	    
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("bizType", bizType);
		rmap.put("method", method);
		rmap.put("version", version);
		rmap.put("params", json);
		logger.info("trafficViolation doJsonPostHttpWithHeader ==> params: " + JSON.toJSONString(rmap));
		String result = BigDataAPI.doJsonPostHttpWithHeader(trafficviolation_url,rmap);
		logger.info("trafficViolation doJsonPostHttpWithHeader ==> result: " + result);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson == null) {
			logger.info("车辆交通违章查询异常,返回数据为空。");
			rs.error("车辆交通违章查询异常");
		}else if ("200".equals(resultJson.getString("code"))) {
			rs.ok(resultJson.getJSONObject("data"));
		}else {
			logger.info("车辆交通违章查询异常,返回数据为：" + resultJson.getString("msg"));
			rs.error(resultJson.getString("msg"));
		}
		
		return rs;
	 }
	
}
