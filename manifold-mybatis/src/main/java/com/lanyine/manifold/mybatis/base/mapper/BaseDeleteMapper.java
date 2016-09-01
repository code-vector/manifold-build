package com.lanyine.manifold.mybatis.base.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.ResultType;

import com.lanyine.manifold.mybatis.base.provider.DeleteSqlProvider;
import com.lanyine.manifold.mybatis.query.ParamsQuery;

public interface BaseDeleteMapper<T, Pk extends Serializable> {
	/**
	 * 删除指定的唯一标识符对应的持久化对象
	 *
	 * @param Pk
	 *            指定的唯一标识符
	 * @return Integer 删除的对象数量
	 */
	@DeleteProvider(type = DeleteSqlProvider.class, method = "deleteById")
	int deleteById(Pk Pk);

	/**
	 * 根据Pk和查询条件，批量删除
	 *
	 * @param query
	 *            DML操作辅助类
	 * @return 删除的对象数量
	 */
	@DeleteProvider(type = DeleteSqlProvider.class, method = "deleteByIds")
	int deleteByIds(ParamsQuery query);

	/**
	 * 根据查询条件，批量删除
	 *
	 * @param query
	 *            DML操作辅助类
	 * @return 删除的对象数量
	 */
	@DeleteProvider(type = DeleteSqlProvider.class, method = "delete")
	@ResultType(Integer.class)
	int delete(ParamsQuery query);
}
