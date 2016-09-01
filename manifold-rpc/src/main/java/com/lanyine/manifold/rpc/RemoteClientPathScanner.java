package com.lanyine.manifold.rpc;

import java.util.Map;
import java.util.Set;

import com.lanyine.manifold.rpc.annotation.RSType;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * 客户端服务接口自动扫描类
 *
 * @author shadow
 */
public class RemoteClientPathScanner extends RServiceClassPathScanner {
    private String remoteUrl;

    public RemoteClientPathScanner(BeanDefinitionRegistry registry, String remoteUrl) {
        super(registry);
        this.remoteUrl = remoteUrl;
    }

    @Override
    public Set<BeanDefinitionHolder> scanner(Set<BeanDefinitionHolder> beanDefinitions) {
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

            AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ANNOTATION_CLASS.getName());
            RSType type = (RSType) annotationAttributes.get("type");
            String uri = (String) annotationAttributes.get("value");
            int port = (int) annotationAttributes.get("port");
            Class<?> clazz = null;
            String domain = remoteUrl;
            if (RSType.HESSIAN == type) {
                clazz = HessianProxyFactoryBean.class;
                // 支持重载
                definition.getPropertyValues().add("overloadEnabled", true);
            } else if (RSType.HTTP == type) {
                clazz = HttpInvokerProxyFactoryBean.class;
            } else if (RSType.RMI == type) {
                clazz = RmiProxyFactoryBean.class;
                domain = domain.replaceAll("^http", "rmi").replaceAll(":[\\d]{1,5}", ":" + port);
            }

            // set common
            definition.getPropertyValues().add("serviceUrl", domain + uri);
            definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
            // set bean class
            definition.setBeanClass(clazz);
            log.info("RemoteService[{}]-[{}] proxy successfully.", type.name(), uri);
        }
        return beanDefinitions;
    }
}