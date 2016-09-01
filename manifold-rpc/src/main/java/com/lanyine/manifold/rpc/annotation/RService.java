package com.lanyine.manifold.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 远程发布注解RemoteService
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RService {
	RSType type() default RSType.HESSIAN;

	/**
	 * 远程服务的默认相对路径
	 * 
	 */
	String value();

	/**
	 * rmi 需要额外制定端口
	 */
	int port() default 10946;
}