/**
 *
 */
package com.lanyine.manifold.mybatis.query;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 条件操作连接符
 * @author shadow
 * @date 2016年4月21日
 * 
 */
public enum Connector {
	AND("AND"), OR("OR"), NOT("NOT");

	private final String value;
	private static Map<String, Connector> codeLookup = new HashMap<>();

	static {
		for (Connector connector : Connector.values()) {
			codeLookup.put(connector.value, connector);
		}
	}

	private Connector(String value) {
		this.value = value;
	}

	public static Connector findByValue(String value) {
		return codeLookup.get(value);
	}

	public String value() {
		return value;
	}

}
