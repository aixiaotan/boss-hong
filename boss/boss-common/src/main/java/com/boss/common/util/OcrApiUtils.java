package com.boss.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boss.common.beans.ApiResult;

public class OcrApiUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(OcrApiUtils.class);
	
	private static String appKey = "CGHtWVeqRq7WskskoluxN0ls";
	private static String appSecret = "cxYpFcQ0BwHuTpKoXyv956MoKzhdOtIU";
	private final static String ocrTokenKey = "yyd.ocr.token";
	
	public static String getToken(boolean refresh) {//TODO 
		/*RedisProxy jedis = RedisProxyFactory.getProxy();
		String token = jedis.get(ocrTokenKey);
		if (refresh || StringUtils.isEmpty(token)) {
			synchronized(OcrApiUtils.class) {
				if (refresh || StringUtils.isEmpty(token)) {
			        String authHost = "https://aip.baidubce.com/oauth/2.0/token";
			        List<BasicNameValuePair> params = new ArrayList<>();
			        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
			        params.add(new BasicNameValuePair("client_id", appKey));
			        params.add(new BasicNameValuePair("client_secret", appSecret));
			        String result = HttpClientUtil.doPostHttp(authHost, params, null);
			        logger.info("get token:{}", result);
			        JSONObject jsonObject = JSON.parseObject(result);
			        token = jsonObject.getString("access_token");
			        int expire = jsonObject.getIntValue("expires_in");
			        jedis.set(ocrTokenKey, token);
			        jedis.expire(ocrTokenKey, expire);
				}
			}
		}
        
		return token;*/
		return null;
	}
	
	public static ApiResult<Object> vehicleLicenseOcr(String imgBase64Str) {
		ApiResult<Object> rs = new ApiResult<>();
		String host = "https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license";
		List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", getToken(false)));
        params.add(new BasicNameValuePair("detect_direction", "false"));
        params.add(new BasicNameValuePair("accuracy", "normal"));
        params.add(new BasicNameValuePair("image", imgBase64Str));
		
		String result = HttpClientUtil.doPostHttp(host, params, null);
		logger.info("vehicle License Ocr:{}", result);
		JSONObject jsonObj = JSON.parseObject(result);
		if(jsonObj != null) {
			if(jsonObj.getIntValue("errno") == 0 ) {
				JSONObject tmp = new JSONObject();
				JSONObject words = jsonObj.getJSONObject("words_result");
				tmp.put("vinCode", words.getJSONObject("车辆识别代号").getString("words"));
				tmp.put("carNumber",  words.getJSONObject("号牌号码").getString("words"));
				tmp.put("carDriverNumber",  words.getJSONObject("发动机号码").getString("words"));
//				tmp.put("licenseTime",  words.getJSONObject("车辆识别代号").getString("words"));
//				tmp.put("carType",  words.getJSONObject("车辆识别代号").getString("words"));
				rs.ok(tmp);
			}else {
				rs.error(jsonObj.getString("msg"));
				rs.setCode(404);
			}
		}
		logger.debug("vehicle License Ocr result:{}", rs);
		return rs;
	}
	
	
	/*public static void main(String[] args) {
		RedisProxyFactory.start();
		System.out.println(OcrApiUtils.getToken(false));
		RedisProxyFactory.shutdown();
	}*/
}
