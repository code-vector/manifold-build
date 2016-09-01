package com.lanyine.manifold.web;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.lanyine.manifold.base.beans.RequestHeader;
import com.lanyine.manifold.web.editor.DateEditorSupport;
import com.lanyine.manifold.web.editor.XssEscapeEditorSupport;
import com.lanyine.manifold.web.util.WebUtil;

/**
 * web项目的基类controller，其中包含了controller所需要的基本信息
 */
public abstract class WebController implements InitializingBean {
	protected final String USER = "USER_TOKEN";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		// 拓展绑定
		this.extBindEditor(binder);
	}

	/**
	 * 拓展绑定
	 */
	protected void extBindEditor(ServletRequestDataBinder binder) {
		// 自动转换日期类型的字段格式
		binder.registerCustomEditor(Date.class, new DateEditorSupport());
		// 防止XSS攻击
		binder.registerCustomEditor(String.class, new XssEscapeEditorSupport());
	}

	protected String getDomain() {
		return "";
	}

	protected String getImagePath() {
		return "";
	}

	protected String getStaticPath() {
		return "";
	}

	protected abstract void setUserInSession(Object user);

	protected abstract Object getUserInSession();

	protected abstract RequestHeader header();

	protected HttpSession getHttpSession() {
		return WebUtil.getHttpSession();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return WebUtil.getHttpServletResponse();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return WebUtil.getHttpServletRequest();
	}

	protected ServletContext getServletContext() {
		return getHttpServletRequest().getServletContext();
	}

	protected String[] getParameterValues(String parameterName) {
		return WebUtil.getParameterValues(parameterName);
	}

	protected String getParameterValue(String parameterName) {
		return WebUtil.getParameterValue(parameterName);
	}

	protected Object getAttribute(String attributeName) {
		return WebUtil.getAttribute(attributeName);
	}

	protected Object getObjectInSession(String key) {
		return WebUtil.getObjectInSession(key);
	}

	protected void setObjectInSession(String key, Object obj) {
		getHttpSession().setAttribute(key, obj);
	}

	/**
	 * 添加cookie
	 *
	 * @param cookie
	 */
	protected void addCookie(Cookie cookie) {
		getHttpServletResponse().addCookie(cookie);
	}

	/**
	 * 根据名字取出cookie的值
	 *
	 * @param name
	 * @return
	 */
	protected String popCookie(String name) {
		Cookie[] cookies = getHttpServletRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 清除Session中的用户信息，用于用户退出系统
	 */
	protected void resetSession() {
		getHttpSession().removeAttribute(USER);
		getHttpSession().invalidate();
	}

	protected void jsonWrite(Object model, HttpServletResponse response) {
		WebUtil.jsonWrite(model, response);
	}

	protected void jsonWrite(Object model, MediaType contentType, HttpServletResponse response) {
		WebUtil.jsonWrite(model, contentType, response);
	}

	/**
	 * 获取请求的IP地址
	 *
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		return WebUtil.getRemoteIp(request);
	}

	/**
	 * 获取请求的IP地址
	 *
	 * @param request
	 * @return
	 */
	public String getIpAddr() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null) {
			return getIpAddr(request);
		}
		return null;
	}

	/**
	 * URL 302 重定向
	 *
	 * @param response
	 */
	protected void redirectURL(HttpServletResponse response, String url) {
		response.setStatus(302);
		response.setHeader("Location", url);
		response.setHeader("Connection", "close");
	}
}
