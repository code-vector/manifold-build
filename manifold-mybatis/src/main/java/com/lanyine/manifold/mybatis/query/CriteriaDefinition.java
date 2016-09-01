package com.lanyine.manifold.mybatis.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 条件类，用于封装条件
 * @author shadow
 *
 */
public abstract class CriteriaDefinition {
	protected List<Meta> criteria = new ArrayList<>();

	/**
	 * 获取操作连接符
	 * 
	 * @return
	 */
	abstract Connector getConnector();

	protected void addMeta(String property, Operator operator) {
		criteria.add(new Meta(property, operator));
	}

	protected void addMeta(String property, Operator operator, Object value) {
		if (value == null) {
			throw new RuntimeException("Value for " + property + " cannot be null");
		}
		criteria.add(new Meta(property, operator, value));
	}

	protected void addMeta(String property, Operator operator, Object value1, Object value2) {
		if (value1 == null || value2 == null) {
			throw new RuntimeException("Between values for " + property + " cannot be null");
		}
		criteria.add(new Meta(property, operator, value1, value2));
	}

	/**
	 * 获取具体的条件
	 * 
	 * @return criteria 具体的条件
	 */
	public List<Meta> getCriteria() {
		return criteria;
	}
}
