package com.lanyine.manifold.mybatis.base.provider;

import org.apache.ibatis.mapping.MappedStatement;

import com.lanyine.manifold.mybatis.ResultMapper;

/**
 * 
 * @Description:
 * @author shadow
 *
 */
public class DeleteSqlProvider extends ProviderTemplate {
	public DeleteSqlProvider(Class<?> mapperClass) {
		super(mapperClass);
	}

	public String deleteById(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("deleteById", rm);
	}

	public String deleteByIds(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSqlAndId("deleteByIds", rm);
	}

	public String delete(MappedStatement ms) {
		ResultMapper rm = getResultMapper(ms);
		return initSql("delete", rm);
	}

}
