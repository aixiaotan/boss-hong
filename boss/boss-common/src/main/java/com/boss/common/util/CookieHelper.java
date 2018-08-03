package com.boss.common.util;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boss.common.constant.Constants;
import com.boss.common.util.StringUtils;

public class CookieHelper {
	
	/*public static String getCookieDomain() {
		Map<String, String> config = PropertiesUtils.load("config");
		return config.get("cookie_domain");
	}*/
	
	private static ResourceBundle resource = ResourceBundle.getBundle("config"); // 读取属性文件
	
	public static String getCookieDomain() {
		return resource.getString("cookie_domain");
	}
	
	public static String getCookieValue(String key, HttpServletRequest httpRequest) {
		Cookie c = getCookie(key, httpRequest);
		return c == null ? null : c.getValue();
	}

	private static Cookie getCookie(String key, HttpServletRequest httpRequest) {
		if (key != null && httpRequest != null) {
			Cookie[] cookies = httpRequest.getCookies();
			if (!(cookies != null && cookies.length != 0 )) {
				for (Cookie c : cookies) {
					if (key.equals(c.getName())) {
						return c;
					}
				}
			}
		}
		return null;
	}

	public static void setCookie(String key, String value, int maxage, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		if (!StringUtils.isEmpty(value)) {
			if (httpRequest != null) {
				Cookie c = getCookie(key, httpRequest);
				if (c != null)
					c.setValue(value);
			}

			if (httpResponse != null) {
				Cookie cookie = new Cookie(key, value);
				cookie.setPath("/");
				cookie.setMaxAge(maxage);
				httpResponse.addCookie(cookie);
			}
		}
	}
	
	public static void setCookie(String key, String value, int timeOut, HttpServletResponse response) {
		if (response != null) {
			Cookie cookie = new Cookie(key, value);
			cookie.setPath("/");
			//cookie.setDomain(".touna.cn");
			cookie.setMaxAge(timeOut);
			response.addCookie(cookie);
		}
	}
	
	public static void setCookie(String key, String value, int timeOut, String domain, HttpServletResponse response) {
		if (response != null) {
			Cookie cookie = new Cookie(key, value);
			cookie.setPath("/");
			cookie.setDomain(domain);
			cookie.setMaxAge(timeOut);
			response.addCookie(cookie);
		}
	}

	public static String getOpenid() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String accessToken = getCookieValue(Constants.ACCESSTOKEN_KEY, request);
		return AesUtil.decode(accessToken);
	}
	
}
