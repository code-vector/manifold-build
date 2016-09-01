package com.lanyine.manifold.mybatis.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 条件类，用于封装条件
 * @author shadow
 */
public class Criteria extends CriteriaDefinition {
    /**
     * 连接符
     */
    private Connector connector = Connector.AND;

    /**
     * 条件链
     */
    @Deprecated
    private List<Criteria> criteriaChain = new ArrayList<>();

    public Criteria() {
    }

    @Deprecated
    public Criteria(Connector connector) {
        this.connector = connector;
    }

    /**
     * 默认调用"="方法来判断
     *
     * @param field
     * @param val
     * @return
     */

    public static Criteria where(String field, Object val) {
        Criteria criteria = new Criteria();
        criteria.eq(field, val);
        return criteria;
    }

    /**
     * "field=#{val}"
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria eq(String field, Object val) {
        addMeta(field, Operator.EQ, val);
        return this;
    }

    /**
     * "!=" or "<>"
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria ne(String field, Object val) {
        addMeta(field, Operator.NE, val);
        return this;
    }

    /**
     * "<"
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria lt(String field, Object val) {
        addMeta(field, Operator.LT, val);
        return this;
    }

    /**
     * "<="
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria lte(String field, Object val) {
        addMeta(field, Operator.LTE, val);
        return this;
    }

    /**
     * ">"
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria gt(String field, Object val) {
        addMeta(field, Operator.GT, val);
        return this;
    }

    /**
     * ">="
     *
     * @param field
     * @param val
     * @return
     */
    public Criteria gte(String field, Object val) {
        addMeta(field, Operator.GTE, val);
        return this;
    }

    /**
     * "BETWEEN … AND …"
     *
     * @param field
     * @param val1
     * @param val2
     * @return
     */
    public Criteria btw(String field, Object val1, Object val2) {
        addMeta(field, Operator.BTW, val1, val2);
        return this;
    }

    /**
     * "NOT BETWEEN … AND …"
     *
     * @param field
     * @param val1
     * @param val2
     * @return
     */
    public Criteria in(String field, Object... vals) {
        return in(field, Arrays.asList(vals));
    }

    /**
     * @param field
     * @param vals
     * @return
     */
    public Criteria in(String field, Collection<?> vals) {
        addMeta(field, Operator.IN, vals);
        return this;
    }

    /**
     * @param field
     * @param vals
     * @return
     */
    public Criteria nin(String field, Object... vals) {
        return nin(field, Arrays.asList(vals));
    }

    /**
     * @param field
     * @param vals
     * @return
     */
    public Criteria nin(String field, Collection<?> vals) {
        addMeta(field, Operator.NIN, vals);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria like(String field, Object val) {
        addMeta(field, Operator.LIKE, val);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria plike(String field, Object val) {
        addMeta(field, Operator.PLIKE, val);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria slike(String field, Object val) {
        addMeta(field, Operator.SLIKE, val);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria nlike(String field, Object val) {
        addMeta(field, Operator.NLIKE, val);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria isNull(String field) {
        addMeta(field, Operator.ISNULL);
        return this;
    }

    /**
     * @param field
     * @param val
     * @return
     */
    public Criteria isNNull(String field) {
        addMeta(field, Operator.ISNNULL);
        return this;
    }

    /**
     * and 条件链
     *
     * @param criteria
     * @return
     */
    @Deprecated
    public Criteria andOperator(Criteria... criteria) {
        return registerCriteriaChainElement(Connector.AND, criteria);
    }

    /**
     * or 条件链
     *
     * @param criteria
     * @return
     */
    @Deprecated
    public Criteria orOperator(Criteria... criteria) {
        return registerCriteriaChainElement(Connector.OR, criteria);
    }

    /**
     * 注册条件链(现在还有BUG，不要使用)
     *
     * @param connector
     * @param criteria
     * @return
     */
    @Deprecated
    private Criteria registerCriteriaChainElement(Connector connector, Criteria... criteria) {
        // Criteria another = new Criteria(connector);
        // for (Criteria c : criteria) {
        // this.criteriaChain.add(c);
        // }
        throw new RuntimeException("开发中.....");
    }

    /**
     * 获取条件链
     *
     * @return criteriaChain 条件链
     */
    @Deprecated
    public List<Criteria> getCriteriaChain() {
        return criteriaChain;
    }

    /**
     * 获取连接符
     *
     * @return connector 连接符
     */
    public Connector getConnector() {
        return connector;
    }
}
