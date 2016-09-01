package com.lanyine.manifold.mybatis.assist;

import com.lanyine.manifold.base.beans.PageResponse;
import com.lanyine.manifold.base.util.BeansConverter;
import com.lanyine.manifold.mybatis.base.BaseMapper;
import com.lanyine.manifold.mybatis.query.ParamsQuery;

/**
 * 分页辅助类
 *
 * @author shadow
 */
public class PageHelper {
	/**
	 * 通用分页查询(需要外部构建查询条件map)
	 *
	 * @param mappers
	 *            dao类
	 * @param query
	 *            查询条件
	 * @param resultClass
	 *            返回的结果类型
	 * @param <T>
	 * @return
	 */
	public static <T> PageResponse<T> query(BaseMapper<?, ?> mapper, ParamsQuery query, Class<T> resultClass) {
		return new PageResponse<T>(query.getPage(), mapper.count(query),
				BeansConverter.copy(mapper.page(query), resultClass));
	}
}
