package com.lanyine.manifold.mybatis.base.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.InsertProvider;

import com.lanyine.manifold.mybatis.base.provider.InsertSqlProvider;
import com.lanyine.manifold.mybatis.query.ParamsQuery;

public interface BaseInsertMapper<T, Pk extends Serializable> {

	/**
	 * 保存（持久化）对象
	 *
	 * @param query
	 *            DML操作辅助类
	 */
	@InsertProvider(type = InsertSqlProvider.class, method = "automatic")
	int insert(ParamsQuery query);
}
