package com.lanyine.manifold.mybatis;

import java.util.Map;

/**
 * @author shadow
 * @Date 2015年12月4日 22:17:43
 */
public final class ResultMapper {
	/**
	 * 数据表名字
	 */
	private String table;
	/**
	 * DAO 对应的接口的名字
	 */
	private String mapper;
	/**
	 * 完整的持久化对象类名
	 */
	private Class<?> entity;
	/**
	 * 数据表的主键字段名
	 */
	private ResultItem pk;

	/**
	 * 默认查询时候需要返回的字段
	 */
	private String baseColumnList;

	/**
	 * <数据库字段，持久化属性,映射集>
	 */
	IDualMap<String, String, ResultItem> resultMap = new DualHahshMap<>();

	ResultMapper(String mapper, Class<?> entity, ResultItem pk, String table,
			IDualMap<String, String, ResultItem> resultMap, String baseColumnList) {
		this.mapper = mapper;
		this.entity = entity;
		this.pk = pk;
		this.table = table;
		this.resultMap = resultMap;
		this.baseColumnList = baseColumnList != null ? baseColumnList : "*";
	}

	/**
	 * @return the 数据表名字
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @return the DAO对应的接口的名字
	 */
	public String getMapper() {
		return mapper;
	}

	/**
	 * @return the 完整的持久化对象类名
	 */
	public Class<?> getEntity() {
		return entity;
	}

	/**
	 * @return the 数据表的主键字段名
	 */
	public ResultItem getPk() {
		return pk;
	}

	/**
	 * @return the 默认查询时候需要返回的字段
	 */
	public String getBaseColumnList() {
		return baseColumnList;
	}

	/**
	 * 
	 * @return <数据库字段，映射集>
	 */
	public Map<String, ResultItem> getColumnMap() {
		return resultMap.getMap1();
	}

	/**
	 * 
	 * @return <持久化属性，映射集>
	 */
	public Map<String, ResultItem> getProptyMap() {
		return resultMap.getMap2();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((mapper == null) ? 0 : mapper.hashCode());
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + ((resultMap == null) ? 0 : resultMap.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultMapper other = (ResultMapper) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (mapper == null) {
			if (other.mapper != null)
				return false;
		} else if (!mapper.equals(other.mapper))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (resultMap == null) {
			if (other.resultMap != null)
				return false;
		} else if (!resultMap.equals(other.resultMap))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

}
