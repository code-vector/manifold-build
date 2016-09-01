package com.lanyine.manifold.mybatis.base;

import java.io.Serializable;

import com.lanyine.manifold.mybatis.base.mapper.BaseDeleteMapper;
import com.lanyine.manifold.mybatis.base.mapper.BaseInsertMapper;
import com.lanyine.manifold.mybatis.base.mapper.BaseSelectMapper;
import com.lanyine.manifold.mybatis.base.mapper.BaseUpdateMapper;

/**
 * mybatis 基础查询接口
 *
 * @param <T>
 *            持久化实体类
 * @param <Pk>
 *            主键的类型，比如 varchar → String,Integer → Integer
 * @author shadow
 */
public interface BaseMapper<T, Pk extends Serializable>
		extends BaseInsertMapper<T, Pk>, BaseDeleteMapper<T, Pk>, BaseUpdateMapper<T, Pk>, BaseSelectMapper<T, Pk> {
}
