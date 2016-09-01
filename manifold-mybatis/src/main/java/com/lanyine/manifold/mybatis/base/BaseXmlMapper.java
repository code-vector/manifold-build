package com.lanyine.manifold.mybatis.base;

import java.io.Serializable;
import java.util.List;

import com.lanyine.manifold.mybatis.query.ParamsQuery;

/**
 * 
 * mybatis 基础查询接口
 * 
 * @param <T>
 *            持久化实体类
 * @param <Pk>
 *            主键的类型，比如 varchar → String,Integer → Integer
 * @author shadow
 *
 */
public interface BaseXmlMapper<T, Pk extends Serializable> {

	/**
	 * 保存（持久化）对象
	 * 
	 * @param query
	 *            DML操作辅助类
	 */
	int insert(ParamsQuery query);

	/**
	 * 根据主键更新对象
	 * 
	 * @param query
	 */
	int updateById(ParamsQuery query);

	/**
	 * 根据主键批量更新对象
	 * 
	 * @param query
	 */
	int updateByIds(ParamsQuery query);

	/**
	 * 根据查询条件，批量更新
	 * 
	 * @param query
	 *            DML操作辅助类
	 */
	void update(ParamsQuery query);

	/**
	 * 删除指定的唯一标识符对应的持久化对象
	 *
	 * @param Pk
	 *            指定的唯一标识符
	 * @return Integer 删除的对象数量
	 */
	int deleteById(Pk Pk);

	/**
	 * 根据主键批量删除
	 *
	 * @param query
	 *            DML操作辅助类
	 * @return 删除的对象数量
	 */
	int deleteByIds(ParamsQuery query);

	/**
	 * 根据查询条件，批量删除
	 * 
	 * @param query
	 *            DML操作辅助类
	 * @return 删除的对象数量
	 */
	int delete(ParamsQuery query);

	/**
	 * 根据主键查询对象
	 *
	 * @param Pk
	 *            指定的唯一标识符
	 * @return 查询的对象
	 */
	T findById(Pk Pk);

	/**
	 * 通过查询条件,获取一条记录
	 * 
	 * @param query
	 *            DML操作辅助类
	 * @return
	 */
	T findOne(ParamsQuery query);

	/**
	 * 根据ID和条件，批量查询
	 * 
	 * @param query
	 *            DML操作辅助类
	 * @return
	 */
	List<T> findByIds(ParamsQuery query);

	/**
	 * 根据查询条件,批量查询
	 * 
	 * @param query
	 *            DML操作辅助类
	 */
	List<T> find(ParamsQuery query);

	/**
	 * 获取满足查询参数条件的数据总数
	 * 
	 * @param query
	 *            DML操作辅助类
	 * @return 数据总数
	 */
	int count(ParamsQuery query);

	/**
	 * 分页查询
	 * 
	 * @param query
	 *            包含查询条件、start、limit，sortAs字段
	 */
	List<T> page(ParamsQuery query);
}
