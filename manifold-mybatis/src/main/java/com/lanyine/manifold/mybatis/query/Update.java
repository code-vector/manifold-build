package com.lanyine.manifold.mybatis.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.lanyine.manifold.base.constant.Null;
import com.lanyine.manifold.base.exception.BaseDaoException;
import com.lanyine.manifold.base.util.BeansConverter;
import com.lanyine.manifold.base.util.ValidatorTool;
import com.lanyine.manifold.mybatis.MapperFactory;
import com.lanyine.manifold.mybatis.ResultItem;

/**
 * @author shadow
 * @Description: 条件类，用于封装条件
 */
public class Update implements Serializable {
	private static final long serialVersionUID = -2236014297206699073L;

	/**
	 * 持久化对象类
	 */
	private Class<?> entityClass;
	private Query query;

	public static final String[] SUPPLEMENT = { "createdTime", "createdBy", "updatedBy", "updatedTime" };
	public static final String[] IGNORE = { "createdTime", "createdBy" };

	/**/
	private List<Object> insertObjs = new ArrayList<>(1);
	private List<UpdateObject> updateObjs = new ArrayList<>(1);
	/**
	 * 参数列表，主要用于SQL语句插入、修改等参数拼接
	 **/
	private Map<String, Object> paramMap = new HashMap<>();

	public Update(Query query) {
		this.query = query;
	}

	protected Update(Class<?> entityClass, Query query) {
		this.entityClass = entityClass;
		this.query = query;
	}

	/**
	 * 获取参数列表，主要用于SQL语句插入、修改等参数拼接
	 *
	 * @return paramMap 参数列表，主要用于SQL语句插入、修改等参数拼接
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * 插入一个标准持久化对象
	 *
	 * @param ob
	 * @return
	 */
	public void insert(Object ob) {
		Assert.notNull(ob);
		this.insertObjs.add(ob);
	}

	/**
	 * 修改一个标准持久化对象
	 *
	 * @param ob
	 * @return
	 */
	public Update update(Object ob) {
		return update(ob, false, IGNORE);
	}

	/**
	 * 修改一个标准持久化对象的的部分属性
	 *
	 * @param ob
	 * @param include
	 * @param fields
	 * @return
	 */
	public Update update(Object ob, boolean include, String... fields) {
		Assert.notNull(ob);
		this.updateObjs.add(new UpdateObject(ob, include, fields));
		return this;
	}

	/**
	 * 修改吃持久化对象的一个属性
	 *
	 * @param field
	 * @param val
	 * @return
	 */
	public Update update(String field, Object val) {
		if ((field != null && !field.trim().isEmpty()) && val != null) {
			paramMap.put(field, val);
		}
		return this;
	}

	private Object convertBean(Object obj) {
		try {
			Map<String, Object> beans = BeansConverter.beanToMap(obj, false);
			Set<String> sets = new HashSet<>();
			sets.addAll(beans.keySet());
			sets.addAll(Arrays.asList(SUPPLEMENT));
			Set<String> etyProSet = BeansConverter.beanToMap(entityClass.newInstance(), false).keySet();
			// 条件1：插入的对象必须包含持久化对象的所有属性

			for (String prop : etyProSet) {
				if (!sets.contains(prop)) {
					throw new BaseDaoException("DB1001", String.format("insert|update [%s] no [%s]-(%s)",
							obj.getClass().getName(), entityClass.getName(), prop));
				}
			}
			return BeansConverter.copy(obj, entityClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseDaoException("DB1001", e.toString());
		}
	}

	/**
	 * <pre>
	 * 当fields 不为空的时候：
	 * 		include == true, 包含     fields ，只会把 fields 包含的字段加入；
	 * 		include == false,忽略     fields ， 不会把  fields 包含的字段加入；
	 * 当fields 为空的时候：
	 * 		include是否为真，都会完全添加到集合中
	 * </pre>
	 *
	 * @param map
	 * @param ob
	 * @param include
	 * @param fields
	 * @return
	 */
	private Update filterToMap(Map<String, Object> map, Object ob, boolean include, String... fields) {
		List<String> fs = Arrays.asList(fields);
		Map<String, Object> fm = BeansConverter.beanToMap(ob);
		fm.keySet().stream().filter(f -> fs.isEmpty() || (fs.contains(f) ? include : !include))
				.forEach(k -> map.put(k, fm.get(k)));

		return this;
	}

	/**
	 * 处理(批量)插入对象
	 */
	private void handlerInsertObjs() {
		if (insertObjs.isEmpty()) {
			return;
		}
		for (Object ob : insertObjs) {
			ob = convertBean(ob);
			ValidatorTool.validate(ob);
			filterToMap(paramMap, ob, false, "");
		}
		insertObjs.clear();
	}

	/**
	 * 处理插入(修改)对象
	 */
	private void handlerUpdateObjs() {
		if (updateObjs.isEmpty()) {
			return;
		}
		for (UpdateObject uo : updateObjs) {
			Object ob = convertBean(uo.getOb());
			ValidatorTool.validateNotNull(ob);
			filterToMap(paramMap, ob, uo.isInclude(), uo.getFields());
		}
		updateObjs.clear();
	}

	/**
	 * 根据 normMap => "(a,b,c,d) values('1','22','test','bcd')" </br>
	 * 插入的SQL,(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常)
	 *
	 * @return
	 */
	public String getColValues() {
		handlerInsertObjs();
		StringBuilder colValues = new StringBuilder();
		if (paramMap.isEmpty())
			throw new BaseDaoException("SQL", "SQL参数错误：insert语句，新增字段不能全部为空");

		StringBuilder columnStr = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();

		Set<String> columns = MapperFactory.columnSets(entityClass);

		ResultItem item = null;
		for (String column : columns) {
			item = MapperFactory.col2Item(entityClass, column);
			Object value = paramMap.get(item.getProperty());

			if (value != null) {
				columnStr.append(column + ",");
				valuesStr.append("#{paramMap." + column + "},");
				paramMap.put(column, value);
			}
		}

		return colValues.append("(" + deleteLast(columnStr) + ") values(" + deleteLast(valuesStr) + ")").toString();
	}

	/**
	 * 更新部分，(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常)
	 *
	 * @return
	 */
	public String getSets() {
		handlerUpdateObjs();
		StringBuilder updateSets = new StringBuilder();
		if (paramMap.isEmpty())
			throw new BaseDaoException("DB1001", "SQL参数错误：update语句，更新字段不能全部为空");

		ResultItem item;
		Map<String, Object> tempMap = new HashMap<>();
		for (String field : paramMap.keySet()) {
			item = MapperFactory.prop2Item(entityClass, field);
			Object value = tranfValue(item, paramMap.get(field));
			tempMap.put(field, value);
			if (item.isPrimaryKey()) {
				query.pk(value.toString());
			} else {
				updateSets.append(" " + item.getColumn() + "=#{paramMap." + field + "},");
			}
		}
		paramMap = tempMap;
		return deleteLast(updateSets).toString();
	}

	/**
	 * 重新转换某些类型的值
	 *
	 * @param item
	 * @param valueOld
	 * @return
	 */
	private Object tranfValue(ResultItem item, Object valueOld) {
		if (valueOld instanceof String && valueOld.equals(Null.STRING)) {
			return null;
		}
		if (valueOld instanceof Integer && valueOld.equals(Null.NUMBER)) {
			return null;
		}
		if (valueOld instanceof BigDecimal && valueOld.equals(Null.DECIMAL)) {
			return null;
		}
		return valueOld;
	}

	private StringBuilder deleteLast(StringBuilder text) {
		if (text == null || text.length() == 0)
			return text;
		return text.deleteCharAt(text.length() - 1);
	}

	/**
	 * 获取持久化对象类
	 *
	 * @return entityClass 持久化对象类
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	protected void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
}
