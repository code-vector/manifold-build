package com.lanyine.manifold.mybatis.query;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shadow
 * @Description:
 */
public enum Operator {
    EQ("="), NE("!="), LT("<"), LTE("<="), GT(">"), GTE(">="), BTW("Between...AND..."), NBTW("Not Between...AND..."), IN(
            "IN"), NIN("NOT IN"), LIKE("LIKE"), PLIKE("PLIKE"), SLIKE("SLIKE"), NLIKE("NOT LIKE"), ISNULL(
            "IS NULL"), ISNNULL("IS NOT NULL"), AND("AND"), OR("OR");

    private final String value;
    private static Map<String, Operator> codeLookup = new HashMap<>();

    static {
        for (Operator type : Operator.values()) {
            codeLookup.put(type.value, type);
        }
    }

    private Operator(String value) {
        this.value = value;
    }

    public static Operator findByValue(String value) {
        return codeLookup.get(value);
    }

    public String value() {
        return value;
    }
}
