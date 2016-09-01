package com.lanyine.manifold.base.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 条件查询的基类
 *
 * @author shadow
 */
public abstract class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private String sortFields;

    public String getSortFields() {
        return sortFields;
    }

    /**
     * field 属性按照降序排列
     *
     * @param field 需要排序的字段
     * @return 当前类，可连续操作
     */
    public final BaseQuery desc(String field) {
        return this;
    }

    /**
     * field 属性按照升序排列
     *
     * @param field 需要排序的字段
     * @return 当前类，可连续操作
     */
    public final BaseQuery asc(String field) {

        return this;
    }

    public final void setSortFields(String sortFields) {
        if (sortFields != null) {
            for (String x : sortFields.trim().split(",")) {
                if (!(x.trim().split("\\s+")[1].toUpperCase().matches("DESC|ASC"))) {
                    logger.error("{}设置排序格式错误，sortFields={}", getClass().getName(), sortFields);
                    throw new RuntimeException("BaseQuery sortFields format error. sortFields=" + sortFields);
                }
            }
        }
        this.sortFields = sortFields;
    }
}
