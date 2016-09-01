package com.lanyine.manifold.mybatis.base.provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import com.lanyine.manifold.mybatis.MapperFactory;
import com.lanyine.manifold.mybatis.ResultItem;
import com.lanyine.manifold.mybatis.ResultMapper;
import com.lanyine.manifold.mybatis.base.InsertType;
import com.lanyine.manifold.mybatis.base.MLanguageDriver;
import com.lanyine.manifold.mybatis.util.SqlTemplateUtil;

/**
 * @Description: TODO)
 * @author shadow
 * 
 */
public abstract class ProviderTemplate {
	private static final XMLLanguageDriver languageDriver = new MLanguageDriver();
	protected static final String TAG_TABLE = "[table]";
	protected static final String TAG_ID = "[id]";
	private Map<String, Method> methodMap = new HashMap<>();

	protected SqlTemplateUtil sqlTemplate = SqlTemplateUtil.instance;
	protected Class<?> mapperClass;

	public ProviderTemplate(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
	}

	protected String tableName(ResultMapper mapper) {
		return mapper.getTable();
	}

	protected String pkName(ResultMapper mapper) {
		return mapper.getPk().getColumn();
	}

	protected String initSql(String method, ResultMapper mapper) {
		return sqlTemplate.getSql(method).replace(TAG_TABLE, tableName(mapper));
	}

	protected String initSql(MappedStatement ms, ResultMapper mapper) {
		return initSql(getMethodName(ms), mapper);
	}

	protected String initSqlAndId(String method, ResultMapper mapper) {
		return initSql(method, mapper).replace(TAG_ID, pkName(mapper));
	}

	protected ResultMapper getResultMapper(MappedStatement ms) {
		String methodID = ms.getId();
		String mapper = methodID.substring(0, methodID.lastIndexOf("."));
		return MapperFactory.fromMapper(mapper);
	}

	/**
	 * 根据msId获取接口类
	 *
	 * @param msId
	 * @return
	 */
	public static Class<?> getMapperClass(String msId) {
		if (msId.indexOf(".") == -1) {
			throw new RuntimeException("当前MappedStatement的id=" + msId + ",不符合MappedStatement的规则!");
		}
		String mapperClassStr = msId.substring(0, msId.lastIndexOf("."));
		try {
			return Class.forName(mapperClassStr);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * 获取执行的方法名
	 *
	 * @param ms
	 * @return
	 */
	public static String getMethodName(MappedStatement ms) {
		return getMethodName(ms.getId());
	}

	/**
	 * 获取执行的方法名
	 *
	 * @param msId
	 * @return
	 */
	public static String getMethodName(String msId) {
		return msId.substring(msId.lastIndexOf(".") + 1);
	}

	/**
	 * 添加映射方法
	 *
	 * @param methodName
	 * @param method
	 */
	public void addMethodMap(String methodName, Method method) {
		methodMap.put(methodName, method);
	}

	/**
	 * 是否支持该通用方法
	 *
	 * @param msId
	 * @return
	 */
	public boolean supportMethod(String msId) {
		Class<?> mapperClass = getMapperClass(msId);
		if (mapperClass != null && this.mapperClass.isAssignableFrom(mapperClass)) {
			String methodName = getMethodName(msId);
			return methodMap.get(methodName) != null;
		}
		return false;
	}

	/**
	 * 重新设置SqlSource，同时判断如果是Jdbc3KeyGenerator，就设置为MultipleJdbc3KeyGenerator
	 *
	 * @param ms
	 * @param sqlSource
	 */
	protected void setSqlSource(MappedStatement ms, SqlSource sqlSource) {
		MetaObject msObject = SystemMetaObject.forObject(ms);
		msObject.setValue("sqlSource", sqlSource);
	}

	/**
	 * 检查是否配置过缓存
	 *
	 * @param ms
	 * @throws Exception
	 */
	private void checkCache(MappedStatement ms) throws Exception {
		if (ms.getCache() == null) {
			String nameSpace = ms.getId().substring(0, ms.getId().lastIndexOf("."));
			Cache cache;
			try {
				// 不存在的时候会抛出异常
				cache = ms.getConfiguration().getCache(nameSpace);
			} catch (IllegalArgumentException e) {
				return;
			}
			if (cache != null) {
				MetaObject metaObject = SystemMetaObject.forObject(ms);
				metaObject.setValue("cache", cache);
			}
		}
	}

	/**
	 * 重新设置SqlSource
	 * 
	 * @param ms
	 * @throws Exception
	 */
	public void setSqlSource(MappedStatement ms) throws Exception {
		if (this.mapperClass == getMapperClass(ms.getId())) {
			throw new RuntimeException("请不要配置或扫描通用Mapper接口类：" + this.mapperClass);
		}
		Method method = methodMap.get(getMethodName(ms));
		try {
			SqlSource newSqlSource = null;
			if (String.class.equals(method.getReturnType())) {// 返回xml形式的sql字符串
				String xmlSql = (String) method.invoke(this, ms);
				newSqlSource = createSqlSource(ms, xmlSql);
			} else if (SqlNode.class.isAssignableFrom(method.getReturnType())) { // 返回SqlNode
				SqlNode sqlNode = (SqlNode) method.invoke(this, ms);
				newSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
			} else if (InsertType.class.isAssignableFrom(method.getReturnType())) {
				String xmlSql = getInsertSql(ms);
				newSqlSource = createSqlSource(ms, xmlSql);
			} else if (method.getReturnType() == Void.TYPE) {// 直接操作ms，不需要返回值
				method.invoke(this, ms);
			} else {
				throw new RuntimeException("自定义Mapper方法返回类型错误,返回类型目前仅支持：Void,SqlNode,String,InsertType!");
			}
			if (newSqlSource != null) {
				// 替换原有的SqlSource
				setSqlSource(ms, newSqlSource);
			}
			// set cache
			checkCache(ms);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getTargetException() != null ? e.getTargetException() : e);
		}
	}

	/**
	 * 获取插入语句（经过处理的插入语句，可以回写ID）
	 * 
	 * @param ms
	 * @return
	 */
	private String getInsertSql(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		ResultItem ri = rm.getPk();
		String pk = ri.getProperty();
		// 为MappedStatement设置生成SelectKey
		Configuration config = ms.getConfiguration();
		if (ri.isNumberType()) { // 如果主键是数字
			SqlSource selectKeySource = new StaticSqlSource(config, "SELECT LAST_INSERT_ID()");
			MappedStatement kms = new MappedStatement.Builder(config, ms.getId() + "!selectKey", selectKeySource,
					SqlCommandType.SELECT).keyProperty(pk).resultMaps(ms.getResultMaps()).build();
			MetaObject msObject = SystemMetaObject.forObject(ms);
			msObject.setValue("keyGenerator", new SelectKeyGenerator(kms, false));
		}
		return initSql(getMethodName(ms), rm);
	}

	/**
	 * 通过xmlSql创建sqlSource
	 *
	 * @param ms
	 * @param xmlSql
	 * @return
	 */
	public SqlSource createSqlSource(MappedStatement ms, String xmlSql) {
		return languageDriver.createSqlSource(ms.getConfiguration(), "<script>\n\t" + xmlSql + "</script>", null);
	}

	/**
	 * 仅作一个标识，原因参见：org.apache.ibatis.builder.annotation.ProviderSqlSource.
	 * ProviderSqlSource(Configuration, Object)
	 * 
	 * @return
	 */
	public String automatic() {
		return "AUTOMATIC_SQL";
	}
}