package com.lanyine.manifold.mybatis.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.lanyine.manifold.base.beans.BaseQuery;
import com.lanyine.manifold.base.beans.PageRequest;
import com.lanyine.manifold.base.exception.BaseDaoException;
import com.lanyine.manifold.base.util.BeansConverter;
import com.lanyine.manifold.mybatis.MapperFactory;
import com.lanyine.manifold.mybatis.ResultMapper;

/**
 * @author shadow
 * @Description: 条件类，用于封装条件
 */
public class Query {
	/**
	 * 持久化对象类
	 */
	private Class<?> entityClass;

	/**
	 * 对象主键
	 */
	private Object id;

	/**
	 * 主键集合
	 **/
	private Set<String> ids = new HashSet<>();

	/**
	 * 查询时候，需要保留的字段
	 **/
	private Set<String> selectSet = new HashSet<>();

	/**
	 * 参与列表，主要用于SQL语句的条件拼接
	 **/
	private Map<String, Object> conditionMap = new HashMap<>();

	private final Sort sort;
	private final Criteria criteria;
	/**
	 * 分页的信息
	 **/
	private PageRequest page;

	private int start;
	private int limit;

	public Query() {
		this(new Criteria());
	}

	public Query(Criteria criteria) {
		this.sort = new Sort();
		this.criteria = criteria;
	}

	/**
	 *
	 */
	protected Query(Class<?> entityClass) {
		this(entityClass, new Criteria());
	}

	/**
	 *
	 */
	public Query(Class<?> entityClass, Criteria criteria) {
		this.entityClass = entityClass;
		this.sort = new Sort(entityClass);
		this.criteria = criteria;
	}

	public static Query query(Criteria criteria) {
		return new Query(criteria);
	}

	/**
	 * @return
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 */
	protected void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 设置分页信息
	 */
	public Query page(PageRequest page) {
		this.page = page;
		return this;
	}

	public PageRequest getPage() {
		return page;
	}

	public Query start(int start) {
		this.start = start;
		return this;
	}

	public Query limit(int limit) {
		this.limit = limit;
		return this;
	}

	/**
	 * 获取start
	 *
	 * @return start start
	 */
	public int getStart() {
		return page == null ? start : (page.getPage() - 1) * page.getPageSize();
	}

	/**
	 * 获取limit
	 *
	 * @return limit limit
	 */
	public int getLimit() {
		return page == null ? limit : page.getPageSize();
	}

	/**
	 * @param fields
	 * @return
	 */
	public Query select(String... fields) {
		Arrays.asList(fields).forEach(f -> selectSet.add(f));
		return this;
	}

	/**
	 * 设置主键
	 *
	 * @param pk
	 * @return
	 */
	public Query pk(Serializable pk) {
		this.id = pk;
		return this;
	}

	/**
	 * 添加集合到主键集合中
	 *
	 * @param pks
	 * @return
	 */
	public Query pks(Collection<?> pks) {
		if (pks == null || pks.isEmpty())
			throw new BaseDaoException("DB1001", "the ids can't be null");

		pks.forEach(pk -> ids.add(pk.toString()));
		return this;
	}

	/**
	 * 添加数组到主键集合中
	 *
	 * @param idArray
	 * @return
	 */
	public Query pks(Object[] pks) {
		if (pks == null || pks.length == 0)
			throw new BaseDaoException("DB1001", "the ids can't be null");

		Arrays.asList(pks).forEach(pk -> ids.add(pk.toString()));
		return this;
	}

	/**
	 * 获取查询时候，需要保留的字段
	 *
	 * @return selects 查询时候，需要保留的字段
	 */
	public String getSelects() {
		if (selectSet.isEmpty()) {
			ResultMapper rm = MapperFactory.fromEntity(entityClass);
			return rm.getBaseColumnList();
		}
		return selectSet.stream().map(f -> transferColName(f)).collect(Collectors.joining(",", " ", " "));
	}

	/**
	 * 根据属性名获取数据库字段名
	 *
	 * @param field
	 * @return
	 */
	private String transferColName(String field) {
		String column = MapperFactory.prop2Item(entityClass, field).getColumn();
		if (field == null || field.trim().isEmpty())
			throw new BaseDaoException("DB1001", String.format("未找到属性[%s]映射的数据库字段", field));
		return column;
	}

	/**
	 * 增加一个查询条件
	 */
	public Query where(String field, Object val) {
		criteria.eq(field, val);
		return this;
	}

	/**
	 * 将类作为一个查询条件
	 *
	 * @param ob
	 * @return
	 */
	public Query where(BaseQuery query) {
		return where(query, false);
	}

	/**
	 * 将类作为一个查询条件，可排除某些属性
	 *
	 * @param ob
	 * @param include
	 * @param fields
	 * @return
	 */
	public Query where(BaseQuery baseQuery, boolean include, String... fields) {
		String sort = baseQuery.getSortFields();
		if (sort != null) {
			baseQuery.setSortFields(null);
			Arrays.asList(sort.trim().split(",")).forEach(x -> {
				String[] xs = x.trim().split("\\s+");
				if (xs.length == 2 && xs[1].toUpperCase().matches("DESC|ASC")) {
					this.sort.getSortMap().put(xs[0], xs[1].toUpperCase());
				} else {
					throw new BaseDaoException("DB1001", "BaseQuery sortFields format error.");
				}
			});
		}

		return filterToMap(baseQuery, include, fields);
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
	private Query filterToMap(Object ob, boolean include, String... fields) {
		List<String> fs = Arrays.asList(fields);
		Map<String, Object> fm = BeansConverter.beanToMap(ob);
		fm.keySet().stream().filter(f -> fs.isEmpty() || (fs.contains(f) ? include : !include))
				.forEach(k -> criteria.eq(k, fm.get(k)));
		return this;
	}

	/**
	 * 获取条件集合
	 *
	 * @return
	 */
	public Map<String, Object> getConditionMap() {
		if (conditionMap.isEmpty()) {
			// 重新计算
			List<Meta> metas = criteria.getCriteria();
			for (Meta meta : metas) {
				conditionMap.put(meta.getProperty(), meta.getVal1());
			}
		}
		return conditionMap;
	}

	/**
	 * 获取sort
	 *
	 * @return sort sort
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * @return
	 */
	public Set<String> getIds() {
		return ids;
	}

	/**
	 * 获取主键，以String返回
	 *
	 * @return
	 */
	public Object getId() {
		return id;
	}

	/**
	 * where 条件是根据condition来拼装的,(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常);</br>
	 * 如果 condition 为空，将抛出异常
	 *
	 * @return
	 */
	public String getStrictWhere() {
		List<Meta> metas = criteria.getCriteria();
		if (metas.isEmpty()) {
			throw new BaseDaoException("DB1001", "update or delete criteria can't be null!");
		}
		return metas.stream().map(m -> getCriterion(transferColName(m.getProperty()), m))
				.collect(Collectors.joining(" and "));
	}

	/**
	 * where 条件是根据condition来拼装的,(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常)
	 *
	 * @return
	 */
	public String getCommonWhere() {
		StringBuilder where = new StringBuilder(" 1=1 ");
		List<Meta> metas = criteria.getCriteria();
		if (!metas.isEmpty()) {
			String column;
			for (Meta meta : metas) {
				column = transferColName(meta.getProperty());
				where.append(" and " + getCriterion(column, meta));
			}
			return where.toString();
		}
		return where.toString();
	}

	private String getCriterion(String column, Meta meta) {
		if (meta == null || meta.getOperator() == null) {
			throw new RuntimeException("获取SQL条件错误");
		}

		Operator op = meta.getOperator();
		String value = null, key = UUID.randomUUID().toString();
		if (meta.isNoValue()) {
			if (op.equals(Operator.ISNULL)) {
				value = column + " is　null";
			} else if (op.equals(Operator.ISNNULL)) {
				value = column + " is not null";
			}
		} else if (meta.isSingleValue()) {
			conditionMap.put(key, meta.getVal1());
			if (op.equals(Operator.EQ)) {
				value = column + " = #{conditionMap." + key + "}";
			} else if (op.equals(Operator.EQ)) {
				value = column + " > #{conditionMap." + key + "}";
			} else if (op.equals(Operator.NE)) {
				value = column + " <> #{conditionMap." + key + "}";
			} else if (op.equals(Operator.LT)) {
				value = column + " < #{conditionMap." + key + "}";
			} else if (op.equals(Operator.LTE)) {
				value = column + " <= #{conditionMap." + key + "}";
			} else if (op.equals(Operator.GT)) {
				value = column + " > #{conditionMap." + key + "}";
			} else if (op.equals(Operator.GTE)) {
				value = column + " >= #{conditionMap." + key + "}";
			} else if (op.equals(Operator.LIKE)) {
				value = column + " like concat('%',#{conditionMap." + key + "},'%')";
			} else if (op.equals(Operator.PLIKE)) {
				value = column + " like concat('%',#{conditionMap." + key + "})";
			} else if (op.equals(Operator.SLIKE)) {
				value = column + " like concat(#{conditionMap." + key + "},'%')";
			} else if (op.equals(Operator.NLIKE)) {
				value = column + " not like concat('%',#{conditionMap." + column + "},'%')";
			}
		} else if (meta.isSetValue()) {
			List<Object> lists = new ArrayList<>();
			StringBuilder strs = new StringBuilder(" (");
			if (meta.getVal1() instanceof Collection) {
				lists.addAll((Collection<?>) meta.getVal1());
			}
			String k;
			for (int i = 0; i < lists.size(); i++) {
				k = key + "_" + i;
				conditionMap.put(k, lists.get(i));
				strs.append("#{conditionMap." + k + "},");
			}
			strs.deleteCharAt(strs.length() - 1).append(")");
			value = strs.deleteCharAt(strs.length() - 1).append(")").toString();

			if (op.equals(Operator.IN)) {
				value = column + " in " + value;
			} else if (op.equals(Operator.NIN)) {
				value = column + " not in " + value;
			}
		} else if (meta.isBetweenValue()) {
			String k1 = key + "_1";
			String k2 = key + "_2";
			conditionMap.put(k1, meta.getVal1());
			conditionMap.put(k2, meta.getVal2());

			if (op.equals(Operator.BTW)) {
				value = column + " between #{conditionMap." + k1 + "} and #{conditionMap." + k2 + "}";
			} else if (op.equals(Operator.NBTW)) {
				value = column + " not between #{conditionMap." + k1 + "} and #{conditionMap." + k2 + "}";
			}
		}
		return value;
	}

}
