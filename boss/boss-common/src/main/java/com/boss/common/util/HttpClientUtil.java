package com.boss.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * GET、POST请求工具类
 *
 */
public class HttpClientUtil {
	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

	private static RequestConfig requestConfig = null;

	static {
		requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(3000).setSocketTimeout(18000).build();
	}

	/**
	 * 工具类，不能实例化
	 */
	private HttpClientUtil() {
	}

	/**
	 * get请求
	 * 
	 * @param uriHttp
	 * @return
	 */
	public static String doGetHttp(String uriHttp) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resHttp = null;
		try {
			HttpGet get = new HttpGet(uriHttp);
			get.setConfig(requestConfig);
			resHttp = httpClient.execute(get);
			int statusHttp = resHttp.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(resHttp.getEntity());
			logger.info("get http[" + uriHttp + "], response status:" + statusHttp + ",content:" + entity);
			if (HttpStatus.SC_OK == statusHttp) {
				return entity;
			}
		}
		catch (Exception e) {
			logger.error("get http[" + uriHttp + "] error:", e);
		}
		finally {
			try {
				if (null != resHttp) {
					resHttp.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("get http[" + uriHttp + "] error:", e);
			}
		}
		return null;
	}

	/**
	 * post请求
	 * 
	 * @param uriHttp
	 * @param param
	 * @return
	 */
	public static String doPostHttp(String uriHttp, List<BasicNameValuePair> paramNameValuePair) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resHttp = null;
		try {
			HttpPost post = new HttpPost(uriHttp);
			post.setConfig(requestConfig);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramNameValuePair, "UTF-8");
			logger.info("post http[" + uriHttp + "],send params:" + EntityUtils.toString(formEntity));
			post.setEntity(formEntity);
			resHttp = httpClient.execute(post);
			int statusHttp = resHttp.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(resHttp.getEntity());
			logger.info("post http[" + uriHttp + "], response status:" + statusHttp + ",content:" + entity);
			if (HttpStatus.SC_OK == statusHttp) {// 正常返回
				return entity;
			}
		}
		catch (Exception e) {
			logger.error("get http[" + uriHttp + "] error:", e);
		}
		finally {
			try {
				if (null != resHttp) {
					resHttp.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("get http[" + uriHttp + "] error:", e);
			}
		}
		return null;
	}

	/**
	 * post请求
	 * 
	 * @param uriHttp
	 * @param param
	 * @return
	 */
	public static String doPostHttp(String uriHttp, String param) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resHttp = null;
		try {
			HttpPost post = new HttpPost(uriHttp);
			post.setConfig(requestConfig);
			StringEntity paramJsonEncode = new StringEntity(param, "UTF-8");// 解决中文乱码问题
			post.setEntity(paramJsonEncode);
			post.setHeader("Content-Type", "application/json");
			resHttp = httpClient.execute(post);
			int statusHttp = resHttp.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(resHttp.getEntity());
			logger.info("post http[" + uriHttp + "], response status:" + statusHttp + ",content:" + entity);
			if (HttpStatus.SC_OK == statusHttp) {// 正常返回
				return entity;
			}
		}
		catch (Exception e) {
			logger.error("get http[" + uriHttp + "] error:", e);
		}
		finally {
			try {
				if (null != resHttp) {
					resHttp.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("get http[" + uriHttp + "] error:", e);
			}
		}
		return null;
	}

	/**
	 * @param url
	 * @param params
	 * @param hearders
	 * @return
	 */
	public static String doJsonPostHttpWithHeader(String uriHttp, Map<String, Object> params, Map<String, String> hearders) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resHttp = null;
		try {
			HttpPost post = new HttpPost(uriHttp);
			post.setConfig(requestConfig);
			StringEntity paramJsonEncode = new StringEntity(JSON.toJSONString(params), "UTF-8");// 解决中文乱码问题
			post.setEntity(paramJsonEncode);
			post.setHeader("Content-Type", "application/json");
			for (Map.Entry<String, String> headerEntry : hearders.entrySet()) {
				post.setHeader(headerEntry.getKey(), headerEntry.getValue());
			}
			resHttp = httpClient.execute(post);
			int statusHttp = resHttp.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(resHttp.getEntity(), "UTF-8");
			logger.info("post http[" + uriHttp + "], response status:" + statusHttp + ",content:" + entity);
			if (HttpStatus.SC_OK == statusHttp) {// 正常返回
				return entity;
			}
		}
		catch (Exception e) {
			logger.error("get http[" + uriHttp + "] error:", e);
		}
		finally {
			try {
				if (null != resHttp) {
					resHttp.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("get http[" + uriHttp + "] error:", e);
			}
		}
		return null;
	}

	public static String doFilePostHttpWithHeader(String uriHttp, TreeMap<String, Object> params, TreeMap<String, Object> fileParams, Map<String, String> hearders) {
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost post = new HttpPost(uriHttp);
		post.setConfig(requestConfig);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.setCharset(Consts.UTF_8);// 设置请求的编码格式

		for (Map.Entry<String, String> headerEntry : hearders.entrySet()) {
			post.setHeader(headerEntry.getKey(), headerEntry.getValue());
		}

		for (Entry<String, Object> entry : fileParams.entrySet()) {
			builder.addPart(entry.getKey(), new InputStreamBody((InputStream) entry.getValue(), "img.jpg"));
		}

		for (Entry<String, Object> entry : params.entrySet()) {
			builder.addTextBody(entry.getKey(), entry.getValue().toString());
		}
		post.setEntity(builder.build());

		String body = null;
		try {
			HttpResponse response = client.execute(post);
			body = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("post http[" + uriHttp + "], response content:" + body);
		}
		catch (ClientProtocolException e) {
			logger.error("doFilePostHttpWithHeader method has Exception", e);
		}
		catch (IOException e) {
			logger.error("doFilePostHttpWithHeader method has Exception", e);
		}
		finally {
			post.releaseConnection();
		}
		return body;
	}
	
	public static String doPostHttp(String uriHttp, List<BasicNameValuePair> paramNameValuePair, Map<String, String> hearders) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resHttp = null;
		try {
			HttpPost post = new HttpPost(uriHttp);
			post.setConfig(requestConfig);
			if(hearders != null) {
				for (Map.Entry<String, String> headerEntry : hearders.entrySet()) {
					post.setHeader(headerEntry.getKey(), headerEntry.getValue());
				}
			}else {
				post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			}
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramNameValuePair, "UTF-8");
			//logger.debug("post http[" + uriHttp + "],send params:" + EntityUtils.toString(formEntity));
			post.setEntity(formEntity);
			resHttp = httpClient.execute(post);
			int statusHttp = resHttp.getStatusLine().getStatusCode();
			String entity = EntityUtils.toString(resHttp.getEntity(), "UTF-8");
			logger.info("post http[" + uriHttp + "], response status:" + statusHttp + ",content:" + entity);
			if (HttpStatus.SC_OK == statusHttp) {// 正常返回
				return entity;
			}
		}
		catch (Exception e) {
			logger.error("get http[" + uriHttp + "] error:", e);
		}
		finally {
			try {
				if (null != resHttp) {
					resHttp.close();
				}
				if (null != httpClient) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("get http[" + uriHttp + "] error:", e);
			}
		}
		return null;
	}

}
