package com.lanyine.manifold.mybatis.base.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

import com.lanyine.manifold.mybatis.base.MLanguageDriver;
import com.lanyine.manifold.mybatis.base.provider.SelectSqlProvider;
import com.lanyine.manifold.mybatis.query.ParamsQuery;

public interface BaseSelectMapper<T, Pk extends Serializable> {

    /**
     * 根据主键查询对象
     *
     * @param Pk 指定的唯一标识符
     * @return 查询的对象
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "findById")
    @ResultMap("BaseResultMap")
    T findById(Pk Pk);

    /**
     * 根据ID和条件，批量查询
     *
     * @param query DML操作辅助类
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "findByIds")
    @ResultMap("BaseResultMap")
    List<T> findByIds(ParamsQuery query);

    /**
     * 通过查询条件,获取一条记录
     *
     * @param query DML操作辅助类
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "findOne")
    @ResultMap("BaseResultMap")
    T findOne(ParamsQuery query);

    /**
     * 根据查询条件,批量查询
     *
     * @param query DML操作辅助类
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "find")
    @ResultMap("BaseResultMap")
    List<T> find(ParamsQuery query);

    /**
     * 获取满足查询参数条件的数据总数
     *
     * @param query DML操作辅助类
     * @return 数据总数
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "count")
    int count(ParamsQuery query);

    /**
     * 分页查询
     *
     * @param query DML操作辅助类:包含查询条件、start、limit，sortAs字段
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "page")
    @ResultMap("BaseResultMap")
    List<T> page(ParamsQuery query);

}
