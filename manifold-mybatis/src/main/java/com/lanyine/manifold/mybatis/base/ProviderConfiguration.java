package com.lanyine.manifold.mybatis.base;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;

import com.lanyine.manifold.mybatis.base.provider.ProviderTemplate;

/**
 * MyBatis 注解配置
 *
 * @author shadow
 */
public class ProviderConfiguration implements InitializingBean {
    /**
     * 注册的通用Mapper接口
     */
    private Set<Class<?>> registerMappers = new HashSet<>();
    /**
     * 通用模板的Provider
     */
    private Map<String, ProviderTemplate> providerMap = new ConcurrentHashMap<>();

    /**
     * 缓存msId和Provider
     */
    private Map<String, ProviderTemplate> msIdCache = new ConcurrentHashMap<>();

    private Configuration configuration;
    private boolean custom;

    /**
     * 通过SqlSessionFactory实例化
     *
     * @param factory : SqlSessionFactory
     */
    public ProviderConfiguration(SqlSessionFactory factory) {
        this(factory, true);
    }

    /**
     * 通过SqlSessionFactory实例化
     *
     * @param factory
     * @param custom
     */
    public ProviderConfiguration(SqlSessionFactory factory, boolean custom) {
        this.configuration = factory.getConfiguration();
        this.custom = custom;
        this.init();
    }

    protected void init() {
        if (custom) {
            registerMapper(BaseMapper.class);
        }
    }

    /**
     * 注册通用Mapper接口（默认已经会注册BaseMapper）
     *
     * @param mapperClass
     */
    public void registerMapper(Class<?> mapperClass) {
        if (registerMappers.contains(mapperClass)) {
            return;
        }

        // 解析通用接口，并获取该通用接口的Provider
        if (parseMapperClass(mapperClass)) {
            registerMappers.add(mapperClass);
        }
        // 注册通用接口继承的父接口
        Class<?>[] interfaces = mapperClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> parent : interfaces) {
                registerMapper(parent);
            }
        }
    }

    /**
     * 通过通用Mapper接口获取对应的BaseSqlProvider
     *
     * @param mapperClass
     * @return
     * @throws Exception
     */
    private boolean parseMapperClass(Class<?> mapperClass) {
        boolean result = false;
        Method[] methods = mapperClass.getDeclaredMethods();
        Class<?> providerClass = null;
        for (Method method : methods) {
            String methodName = null;

            if (method.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = method.getAnnotation(SelectProvider.class);
                providerClass = provider.type();
                methodName = method.getName();
            } else if (method.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider provider = method.getAnnotation(InsertProvider.class);
                providerClass = provider.type();
                methodName = method.getName();
            } else if (method.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider provider = method.getAnnotation(DeleteProvider.class);
                providerClass = provider.type();
                methodName = method.getName();
            } else if (method.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider provider = method.getAnnotation(UpdateProvider.class);
                providerClass = provider.type();
                methodName = method.getName();
            }

            if (methodName != null) {
                ProviderTemplate sqlProvider = initProvider(providerClass, mapperClass);
                try {
                    sqlProvider.addMethodMap(methodName, providerClass.getMethod(methodName, MappedStatement.class));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(
                            providerClass.getCanonicalName() + "not found method[" + methodName + "]");
                }
            }
        }

        return result;
    }

    private ProviderTemplate initProvider(Class<?> providerClass, Class<?> mapperClass) {
        String key = providerClass.getName() + "." + mapperClass.getName();
        ProviderTemplate sqlProvider = providerMap.get(key);
        if (sqlProvider != null)
            return sqlProvider;
        try {
            sqlProvider = (ProviderTemplate) providerClass.getConstructor(Class.class).newInstance(mapperClass);
            providerMap.put(key, sqlProvider);
            return sqlProvider;
        } catch (Exception e) {
            throw new RuntimeException(String.format("实例化'%s'-(%s)对象失败: %s", mapperClass, providerClass, e.toString()));
        }
    }

    /**
     * 判断当前的接口方法是否需要进行拦截
     *
     * @param msId
     * @return
     */
    public boolean isMapperMethod(String msId) {
        for (ProviderTemplate provider : providerMap.values()) {
            if (provider.supportMethod(msId)) {
                msIdCache.put(msId, provider);
                return true;
            }
        }
        return false;
    }

    /**
     * 重新设置SqlSource
     * <p>
     * 执行该方法前必须使用isMapperMethod判断，否则msIdCache会空
     *
     * @param ms
     * @throws Exception
     */
    public void setSqlSource(MappedStatement ms) throws Exception {
        ProviderTemplate provider = msIdCache.get(ms.getId());
        if (provider != null) {
            provider.setSqlSource(ms);
        }
    }

    /**
     * 配置完成后，执行下面的操作 <br>
     * 处理configuration中全部的MappedStatement
     *
     * @throws Exception
     */
    public void processConfiguration() throws Exception {
        Set<?> msSet = new HashSet<>(configuration.getMappedStatements());
        for (Object object : msSet) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                if (isMapperMethod(ms.getId())) {
                    if (ms.getSqlSource() instanceof ProviderSqlSource) {
                        setSqlSource(ms);
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        processConfiguration();
    }
}