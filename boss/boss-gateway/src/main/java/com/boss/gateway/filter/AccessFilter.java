package com.boss.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AccessFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 过滤器运行逻辑
	 */
	@Override
	public Object run() {
		
		String result = restTemplate.getForObject("http://boss/", String.class);
		log.info("gateway:"+result);
		
//		RequestContext ctx = RequestContext.getCurrentContext();
//		HttpServletRequest request = ctx.getRequest();
		
//		log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		
//		Object accessToken = request.getParameter("accessToken");
//		
//		if(accessToken == null) {
//			log.warn("access token is empty");
//			ctx.setSendZuulResponse(false);
//			ctx.setResponseStatusCode(401);
//			return null;
//		}
//		log.info("access token ok");
		return null;
	}

	/**
	 * 返回值定义了过滤器是否执行
	 */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 返回的int值定义了过滤器执行顺序
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 返回值定义了过滤器执行类型
	 * <br>	pre：可以在请求被路由之前调用
	 * <br> routing：在路由请求时候被调用
	 * <br> post：在routing和error过滤器之后被调用
	 * <br> error：处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
