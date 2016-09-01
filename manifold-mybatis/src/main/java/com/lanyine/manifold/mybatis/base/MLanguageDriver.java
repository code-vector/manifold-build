package com.lanyine.manifold.mybatis.base;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

public class MLanguageDriver extends XMLLanguageDriver implements LanguageDriver {
	@Override
	public ParameterHandler createParameterHandler(MappedStatement ms, Object parameterObject, BoundSql boundSql) {
		return super.createParameterHandler(ms, parameterObject, boundSql);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		return super.createSqlSource(configuration, script, parameterType);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
		return super.createSqlSource(configuration, script, parameterType);
	}

}
