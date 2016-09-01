package com.lanyine.manifold.mybatis.base.provider;

import org.apache.ibatis.mapping.MappedStatement;

import com.lanyine.manifold.mybatis.ResultMapper;

/**
 * 
 * @Description:
 * @author shadow
 *
 */
public class SelectSqlProvider extends ProviderTemplate {
	public SelectSqlProvider(Class<?> mapperClass) {
		super(mapperClass);
	}

	public String findById(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("findById", rm);
	}

	public String findByIds(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("findByIds", rm);
	}

	public String findOne(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("findOne", rm);
	}

	public String find(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("find", rm);
	}

	public String count(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("count", rm);
	}

	public String page(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("page", rm);
	}
}
