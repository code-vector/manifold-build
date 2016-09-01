/**
 *
 */
package com.lanyine.manifold.mybatis.query;

/**
 * @author shadow
 */
public interface ParmasHelper {

	static ParamsQuery setEntity(ParamsQuery pq, Class<?> entityClass) {
		return pq.setEntityClass(entityClass);
	}
}
