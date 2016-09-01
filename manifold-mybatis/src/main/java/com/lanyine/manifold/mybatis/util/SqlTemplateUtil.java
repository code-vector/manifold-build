package com.lanyine.manifold.mybatis.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 解析模板中的SQL
 * 
 * @author shadow
 *
 */
public enum SqlTemplateUtil {
	instance;
	private Map<String, String> sqlMap = new HashMap<>();

	private SqlTemplateUtil() {
		parse();
	}

	@SuppressWarnings("unchecked")
	private void parse() {
		if (sqlMap.isEmpty()) {
			try (InputStream is = getClass().getClassLoader().getResourceAsStream("BaseSqlTemplate.xml")) {
				Document doc = new SAXReader().read(is);
				Element root = doc.getRootElement();
				List<String> tags = Arrays.asList("insert", "update", "delete", "select");
				for (String tag : tags) {
					List<Element> list = root.elements(tag);
					for (Element e : list) {
						String id = e.attributeValue("id");
						StringBuilder sql = new StringBuilder();
						List<?> cs = e.content();
						for (Object node : cs) {
							sql.append(((Node) node).asXML());
						}
						sqlMap.put(id, sql.toString().replaceAll("\\s+", " "));
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Failed to parse 'BaseSqlTemplate.xml': " + e.getMessage());
			}
		}
	}

	public String getSql(String id) {
		return sqlMap.get(id);
	}
}
