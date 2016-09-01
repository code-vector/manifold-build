package com.lanyine.manifold.mybatis.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanyine.manifold.base.beans.BaseQuery;
import com.lanyine.manifold.base.beans.PageRequest;
import com.lanyine.manifold.base.exception.BaseDaoException;

/**
 * mybatis CRUD 通用查询对象（查询字段为entity字段）
 * 
 * @author shadow
 * @Date 2015年11月12日下午 3:17:39
 * @version 0.5
 *
 */
public final class ParamsQuery {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 持久化对象类
	 */
	private Class<?> entityClass;

	/**
	 * 持久化对象主键
	 */
	private Object id;

	/**
	 * 对象修改类
	 */
	private Update update;

	/**
	 * 对象查询类
	 */
	private Query query;

	/**
	 * 接收的持久化对象必须是BaseEnty的子类
	 * 
	 * @param entityClass
	 *            BaseEntity的子类，一个具体的持久化对象
	 */
	private ParamsQuery(Class<?> entityClass) {
		this.entityClass = entityClass;
		this.query = new Query(entityClass);
		this.update = new Update(entityClass, query);
	}

	public ParamsQuery() {
		this.query = new Query();
		this.update = new Update(query);
	}

	/**
	 * 接收的持久化对象必须是BaseEnty的子类
	 * 
	 * @param entityClass
	 *            BaseEntity的子类，一个具体的持久化对象
	 */
	public static ParamsQuery instance(Class<?> entityClass) {
		return new ParamsQuery(entityClass);
	}

	/**
	 * 创建一个新的对象
	 * 
	 * @return
	 */
	public static ParamsQuery instance() {
		return new ParamsQuery();
	}

	/**
	 * 如果Query
	 * 
	 * @param 对象查询类
	 *            the query to set
	 */
	public ParamsQuery setQuery(Query query) {
		if (query.getEntityClass() != null && entityClass != query.getEntityClass())
			throw new BaseDaoException("Query Object already exists, cannot be covered...");

		this.query = query;
		return this;
	}

	/**
	 * @param 对象修改类
	 *            the update to set
	 */
	public ParamsQuery setUpdate(Update update) {
		if (update.getEntityClass() != null && entityClass != update.getEntityClass())
			throw new BaseDaoException("Update Object already exists, cannot be covered...");

		this.update = update;
		return this;
	}

	/**
	 * 修改私有属性的值
	 * 
	 * @param t
	 * @return
	 */
	protected ParamsQuery setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
		this.query.setEntityClass(entityClass);
		this.update.setEntityClass(entityClass);
		return this;
	}

	/**
	 * 获取持久化对象类
	 * 
	 * @return entityClass 持久化对象类
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * 设置主键
	 *
	 * @param pk
	 * @return
	 */
	public ParamsQuery pk(Serializable pk) {
		this.query.pk(pk);
		return this;
	}

	/**
	 * 添加集合到主键集合中
	 * 
	 * @param pks
	 * @return
	 */
	public ParamsQuery pks(Collection<?> pks) {
		this.query.pks(pks);
		return this;
	}

	/**
	 * 添加数组到主键集合中
	 * 
	 * @param idArray
	 * @return
	 */
	public ParamsQuery pks(Object[] pks) {
		this.query.pks(pks);
		return this;
	}

	/**
	 * 需要查询的字段
	 * 
	 * @param fields
	 * @return
	 */
	public ParamsQuery select(String... fields) {
		this.query.select(fields);
		return this;
	}

	/**
	 * 保存一个标准持久化对象，如果主键未存在，则插入，否则修改除主键外的其他非空字段(现在不能使用此方法)
	 * 
	 * @param ob
	 * @return
	 */
	// public ParamsQuery save(Object ob) {
	// }

	/**
	 * 插入一个标准持久化对象
	 * 
	 * @param ob
	 * @return
	 */
	public ParamsQuery insert(Object ob) {
		this.update.insert(ob);
		return this;
	}

	/**
	 * 修改持久化对象的一个属性
	 * 
	 * @param field
	 * @param val
	 * @return
	 */
	public ParamsQuery update(String field, Object val) {
		this.update.update(field, val);
		return this;
	}

	/**
	 * 修改一个标准持久化对象(会忽略主键)
	 * 
	 * @param ob
	 * @return
	 */
	public ParamsQuery update(Object ob) {
		this.update.update(ob);
		return this;
	}

	/**
	 * 增加一个查询条件
	 */
	public ParamsQuery where(String field, Object val) {
		this.query.where(field, val);
		return this;
	}

	/**
	 * 将类作为一个查询条件
	 * 
	 * @param ob
	 * @return
	 */
	public ParamsQuery where(BaseQuery query) {
		this.query.where(query);
		return this;
	}

	/**
	 * 降序排列,顺序与添加顺序一致
	 * 
	 * @param fields
	 * @return
	 * @see ParamsQuery#desc(String...)
	 * @see ParamsQuery#asc(String...)
	 */
	public ParamsQuery desc(String... fields) {
		this.query.getSort().desc(fields);
		return this;
	}

	/**
	 * 升序排列，顺序与添加顺序一致
	 * 
	 * @param fields
	 * @return
	 */
	public ParamsQuery asc(String... fields) {
		this.query.getSort().asc(fields);
		return this;
	}

	/**
	 * 排序字段
	 * 
	 * @param sort
	 * @return
	 */
	public ParamsQuery sort(Sort sort) {
		if (sort != null) {
			this.query.getSort().getSortMap().putAll(sort.getSortMap());
		}
		return this;
	}

	/**
	 * 设置分页信息
	 */
	public ParamsQuery page(PageRequest page) {
		this.query.page(page);
		return this;
	}

	public PageRequest getPage() {
		return this.query.getPage();
	}

	// ---------------------------------------------------------------------------------
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * 获取主键，以String返回
	 * 
	 * @return
	 */
	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	/**
	 * 如果主键是整型，以整型返回
	 * 
	 * @return
	 */
	public Integer getIdToInt() {
		if (id == null) {
			return null;
		}
		return Integer.parseInt(id.toString());
	}

	// ---------------------------------------------------------------------------------
	/**
	 * 获取排序拼接的SQL(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常)
	 * 
	 * @return
	 */
	public String getSortAs() {
		return query.getSort().getSorts();
	}

	/**
	 * 获取主键集合
	 * 
	 * @return
	 */
	public Set<String> getIds() {
		return query.getIds();
	}

	/**
	 * 获取参数集合<field,value>(里面的field对应对象的属性，而不是数据库的字段)
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap() {
		return update.getParamMap();
	}

	/**
	 * 获取条件集合<field,value>(里面的field对应对象的属性，而不是数据库的字段)
	 * 
	 * @return
	 */
	public Map<String, Object> getConditionMap() {
		return query.getConditionMap();
	}

	/**
	 * 获取对象修改类
	 * 
	 * @return update 对象修改类
	 */
	public Update getUpdate() {
		return update;
	}

	/**
	 * 获取对象查询类
	 * 
	 * @return query 对象查询类
	 */
	public Query getQuery() {
		return query;
	}

}
