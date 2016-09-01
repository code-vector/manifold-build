package com.lanyine.manifold.mybatis.base.provider;

import org.apache.ibatis.mapping.MappedStatement;

import com.lanyine.manifold.mybatis.base.InsertType;

/**
 * 
 * @Description:
 * @author shadow
 *
 */
public class InsertSqlProvider extends ProviderTemplate {
	public InsertSqlProvider(Class<?> mapperClass) {
		super(mapperClass);
	}

	public InsertType insert(MappedStatement ms) {
		return null;
	}
}
