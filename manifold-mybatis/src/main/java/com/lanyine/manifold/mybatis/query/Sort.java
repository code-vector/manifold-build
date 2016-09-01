package com.lanyine.manifold.mybatis.query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.lanyine.manifold.base.exception.BaseDaoException;
import com.lanyine.manifold.mybatis.MapperFactory;

/**
 * @author shadow
 * @Description: 条件类，用于封装条件
 */
public class Sort implements Serializable {
    private static final long serialVersionUID = 3902769971589961323L;
    private Class<?> entityClass;
    /**
     * 升序
     **/
    public static final String DESC = "DESC";
    /**
     * 降序
     **/
    public static final String ASC = "ASC";

    /**
     * 排序的集合，使用的是有序的MAP
     **/
    private Map<String, String> sortMap = new LinkedHashMap<>();

    /**
     *
     */
    public Sort() {
    }

    /**
     *
     */
    public Sort(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 降序排列,顺序与添加顺序一致
     *
     * @param fields
     * @return
     * @see ParamsQuery#desc(String...)
     * @see ParamsQuery#asc(String...)
     */
    public Sort desc(String... fields) {
        return sort(DESC, fields);
    }

    /**
     * 升序排列，顺序与添加顺序一致
     *
     * @param fields
     * @return
     */
    public Sort asc(String... fields) {
        return sort(ASC, fields);
    }

    /**
     * 具体的排序代码
     *
     * @param sort   DESC|ASC
     * @param fields 需要排序的字段
     */
    protected Sort sort(String sort, String... fields) {
        for (String str : fields) {
            sortMap.put(str, sort);
        }
        return this;
    }

    /**
     * 获取排序的集合
     *
     * @return sortMap 排序的集合，使用的是有序的MAP
     */
    public Map<String, String> getSortMap() {
        return sortMap;
    }

    /**
     * 获取排序拼接的SQL(会严格Mapper结果集,根据对象属性获取表的字段名，找不到则抛出异常)
     *
     * @return
     */
    public String getSorts() {
        if (sortMap.isEmpty()) {
            return null;
        }
        return sortMap.keySet().stream().map(f -> transferColName(f).concat(" ").concat(sortMap.get(f)))
                .collect(Collectors.joining(",", " ", " "));
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

}
