package com.lanyine.manifold.mybatis.base.provider;

import org.apache.ibatis.mapping.MappedStatement;

import com.lanyine.manifold.mybatis.ResultMapper;

/**
 * 
 * @Description:
 * @author shadow
 *
 */
public class UpdateSqlProvider extends ProviderTemplate {
	public UpdateSqlProvider(Class<?> mapperClass) {
		super(mapperClass);
	}

	public String updateById(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("updateById", rm);
	}

	public String updateByIds(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("updateByIds", rm);
	}

	public String update(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("update", rm);
	}
}
