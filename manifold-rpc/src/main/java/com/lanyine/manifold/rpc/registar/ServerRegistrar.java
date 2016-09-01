package com.lanyine.manifold.rpc.registar;

import static org.springframework.util.Assert.notNull;

import com.lanyine.manifold.rpc.RemoteServicePathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * 注解配置服务端需要发布的服务接口
 *
 * @author shadow
 */

public class ServerRegistrar implements ApplicationContextAware, ImportBeanDefinitionRegistrar {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private String basePackage;
    private ApplicationContext applicationContext;

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        LOG.info("ApplicationContextAware.........setApplicationContext");
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        LOG.info("ImportBeanDefinitionRegistrar.........registerBeanDefinitions");
        notNull(this.basePackage, "Property 'basePackage' is required ");
        long t1 = System.currentTimeMillis();
        RemoteServicePathScanner scan = new RemoteServicePathScanner(registry);
        scan.setResourceLoader(applicationContext);
        // scan
        scan.scan(StringUtils.tokenizeToStringArray(basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        LOG.info("Register all RemoteServices ,time is {}ms .", (System.currentTimeMillis() - t1));
    }

}