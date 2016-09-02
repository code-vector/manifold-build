package com.lanyine.manifold.base.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 条件查询的基类
 *
 * @author shadow
 */
public abstract class BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, String> orderMap = new LinkedHashMap<>();

	/**
	 * field 属性按照降序排列
	 *
	 * @param field
	 *            需要排序的字段
	 * @return 当前类，可连续操作
	 */
	public final BaseQuery desc(String field) {
		return sort(field, "DESC");
	}

	/**
	 * field 属性按照升序排列
	 *
	 * @param field
	 *            需要排序的字段s
	 * @return 当前类，可连续操作
	 */
	public final BaseQuery asc(String field) {
		return sort(field, "ASC");
	}

	public Map<String, String> getOrderFields() {
		return orderMap;
	}

	/**
	 * orderFields : "id desc, name asc"
	 * 
	 * @param orderFields
	 *            排序内容
	 */
	public final BaseQuery setOrderFields(String orderFields) {
		if (orderFields != null) {
			String[] orders = orderFields.trim().split(",");
			for (String str : orders) {
				String[] xs = str.trim().split("\\s+");
				if (xs.length == 2 && xs[1].toUpperCase().matches("DESC|ASC")) {
					sort(xs[0], xs[1].toUpperCase());
				}
			}
		}
		return this;
	}

	private BaseQuery sort(String field, String sort) {
		if (field == null) {
			return this;
		}

		field = field.trim();
		if (!orderMap.containsKey(field)) {
			orderMap.put(field, sort);
		}
		return this;
	}
}