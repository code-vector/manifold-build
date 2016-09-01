package com.lanyine.manifold.web.interceptor;

/**
 * 防止表单重复提交注解拦截
 * 
 * <pre>
 * 1.用在控制器方法上
 * &#64;ILogin
 * public String method_1(...){...}
 * 表示该方法需要登录才能访问
 * 
 * 2.用户请求时候，会将一个token生成
 * 3.提交请求时，会将token 提交并验证
 * 
 * </pre>
 * 
 * @author shadow
 *
 */
public abstract class SubmitInterceptor extends AbstractInterceptor {
}