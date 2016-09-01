package com.lanyine.manifold.mybatis.base.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.UpdateProvider;

import com.lanyine.manifold.mybatis.base.provider.UpdateSqlProvider;
import com.lanyine.manifold.mybatis.query.ParamsQuery;

public interface BaseUpdateMapper<T, PK extends Serializable> {
	/**
	 * 根据主键更新对象
	 *
	 * @param query
	 */
	@UpdateProvider(type = UpdateSqlProvider.class, method = "updateById")
	@ResultType(Integer.class)
	int updateById(ParamsQuery query);

	/**
	 * 根据查询条件，批量更新
	 *
	 * @param query
	 *            DML操作辅助类
	 */
	@UpdateProvider(type = UpdateSqlProvider.class, method = "update")
	@ResultType(Integer.class)
	int update(ParamsQuery query);

}
