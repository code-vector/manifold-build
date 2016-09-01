package com.lanyine.manifold.mybatis;

import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * Mapper.xml 结果集映射转换关系工具
 * 
 * @author shadow
 *
 */
public interface MapperFactory {
	/**
	 * Map<持久化对象class，接口名称，Map<数据库字段名,映射集[数据库字段名，持久化对象名，数据库类型，是否主键]>> </br>
	 */
	IDualMap<Class<?>, String, ResultMapper> mapperCaches = new DualHahshMap<>();

	/**
	 * 根据entity,找到映射关系
	 * 
	 * @param mapperName
	 * @return
	 */
	static ResultMapper fromEntity(Class<?> entityClass) {
		Map<Class<?>, ResultMapper> map1 = mapperCaches.getMap1();
		if (map1.containsKey(entityClass)) {
			return map1.get(entityClass);
		}
		throw new RuntimeException("找不到持久化类的映射关系：" + entityClass);
	}

	/**
	 * 根据mapper,找到映射关系
	 * 
	 * @param mapperName
	 * @return
	 */
	static ResultMapper fromMapper(String mapperName) {
		Map<String, ResultMapper> map2 = mapperCaches.getMap2();
		if (map2.containsKey(mapperName)) {
			return map2.get(mapperName);
		}
		throw new RuntimeException("找不到Mapper的映射关系：" + mapperName);
	}

	/**
	 * 根据持久化对象属性名 ——获取——> 数据库字段名
	 * 
	 * @param entityClass
	 * @param propName
	 * @return
	 */
	static ResultItem prop2Item(Class<?> entityClass, String propName) {
		ResultItem item = fromEntity(entityClass).getProptyMap().get(propName);
		if (item == null) {
			throw new RuntimeException("error:找不到对应的属性" + propName);
		}
		return item;
	}

	/**
	 * 根据持久化对象属性名 ——获取——> 数据库字段名
	 * 
	 * @param entityClass
	 * @param propName
	 * @return
	 */
	static ResultItem col2Item(Class<?> entityClass, String colName) {
		ResultItem item = fromEntity(entityClass).getColumnMap().get(colName);
		if (item == null) {
			throw new RuntimeException("error:找不到对应的属性" + colName);
		}
		return item;
	}

	/**
	 * 根据持久化类获取对应数据库的列表集
	 * 
	 * @param entityClass
	 * @return
	 */
	static Set<String> columnSets(Class<?> entityClass) {
		return fromEntity(entityClass).getColumnMap().keySet();
	}

	/**
	 * 
	 * 从配置文件中解析并缓存映射结果集
	 * 
	 * <pre>
	 * example：
	 * &ltresultMap id="BaseMap" type="com.XXX.domain.info.entity.ContractPo" &gt
	 * 	 &ltid column="id" property="id" jdbcType="INTEGER" /&gt
	 * 	 &ltresult column="order_id" property="orderId" jdbcType="INTEGER" /&gt
	 * &lt/resultMap&gt
	 * </pre>
	 * 
	 * @param mapperText
	 * @throws ClassNotFoundException
	 */
	default void cacheDataFromMapper(String file, String mapperText) {
		if (mapperText == null)
			return;

		// <column,ResultItem>
		IDualMap<String, String, ResultItem> resultMap = new DualHahshMap<>();
		ResultItem keyRulstItem = null;

		try {
			Document doc = Jsoup.parse(mapperText, "", Parser.xmlParser());
			String mapper = doc.select("mapper").attr("namespace");
			Elements root = doc.select("#BaseResultMap");
			String entity = root.attr("type");
			String table = doc.select("#table").first().text();
			String baseColumnList = doc.select("#Base_Column_List").first().text();

			Elements es = root.get(0).children();
			for (Element e : es) {
				boolean isPk = "id".equals(e.tagName());
				String col = e.attr("column");
				String prop = e.attr("property");
				String type = e.attr("jdbcType");

				ResultItem item = new ResultItem(prop, col, type, isPk);
				resultMap.put(col, prop, item);
				// 设置主键
				if (isPk) {
					keyRulstItem = item;
				}
			}

			Class<?> entityClass = Class.forName(entity);
			ResultMapper rm = new ResultMapper(mapper, entityClass, keyRulstItem, table, resultMap, baseColumnList);
			mapperCaches.put(entityClass, mapper, rm);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("持久化对象类型不存在:%s", file, e.toString()));
		} catch (Exception e) {
			throw new RuntimeException(String.format("[%s]解析失败.", file), e);
		}
	}

	/**
	 * 
	 * @param guideName
	 *            向导文件
	 * @param postfix
	 *            后缀
	 */
	void cacheData(String guideName, String suffix);
}
