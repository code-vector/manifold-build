package com.lanyine.manifold.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import com.lanyine.manifold.base.beans.JsonObject;
import com.lanyine.manifold.web.annotation.ILogin;

/**
 * 登录注解拦截
 * 
 * <pre>
 * 1.用在控制器方法上
 * &#64;ILogin
 * public String method_1(...){...}
 * 表示该方法需要登录才能访问
 * 
 * 2.用在控制器类上
 * &#64;ILogin
 * &#64;Controller
 * public class XXXController{...}
 * 则表示该类下的所有方法都需要登录才能访问
 * 
 * 说明：
 * 优先级：1>2
 * ILogin 默认 value=true,表示需要登录
 * value =false,表示不需要登录
 * 
 * </pre>
 * 
 * @author shadow
 *
 */
public abstract class LoginInterceptor<T> extends AbstractInterceptor {

	public abstract T getUseInSession(HttpServletRequest request, HttpServletResponse response);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		T user = getUseInSession(request, response);
		if (user == null && (handler instanceof HandlerMethod)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 方法级别的不为空，则直接处理方法级别的拦截
			ILogin iMethod = getAnnotationWithMethod(handlerMethod, ILogin.class);
			if (iMethod != null && iMethod.value()) {
				unLoginHandler(request, response);
				return false;
			}
			// (方法级别为空)类级别不为空，则按类级别处理
			ILogin iBean = getAnnotationWithBean(handlerMethod, ILogin.class);
			if (iBean != null && iBean.value()) {
				unLoginHandler(request, response);
				return false;
			}
		}
		return roleHandler(request, response, handler, user);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param user
	 * @return
	 */
	protected boolean roleHandler(HttpServletRequest request, HttpServletResponse response, Object handler, T user) {
		return true;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param required
	 * @return
	 * @throws Exception
	 */
	protected void unLoginHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType == null) { // 普通请求,返回登录页
			redirect(request, response);
		} else if ("XMLHTTPREQUEST".equals(requestType.toUpperCase())) { // ajax请求，返回json数据
			ajaxData(request, response);
		}
	}

	protected void redirect(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "用户未登录");
		response.setStatus(302);
		response.setHeader("Location", request.getContextPath()); // 返回首页
		response.setHeader("Connection", "close");
	}

	protected void ajaxData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject json = new JsonObject();
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		response.setContentType("text/json; charset=UTF-8");
	}
}