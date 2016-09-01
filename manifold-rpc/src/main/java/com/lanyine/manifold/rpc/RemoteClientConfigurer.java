package com.lanyine.manifold.rpc;

import static org.springframework.util.Assert.notNull;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 客服端服务接口,自动扫描配置
 * 
 * @author shadow
 * 
 */
public final class RemoteClientConfigurer
		implements ApplicationContextAware, InitializingBean, BeanDefinitionRegistryPostProcessor {
	private String basePackage;
	private String remoteUrl;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(this.basePackage, "Property 'locations' is required ");
		notNull(this.remoteUrl, "Property 'remoteUrl' is required ");
		if (remoteUrl.endsWith("/")) {
			remoteUrl = remoteUrl.substring(0, remoteUrl.length() - 1);
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		RemoteClientPathScanner scan = new RemoteClientPathScanner(registry, remoteUrl);
		scan.setResourceLoader(this.applicationContext);
		scan.scan(StringUtils.tokenizeToStringArray(this.basePackage,
				ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
}