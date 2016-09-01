package com.lanyine.manifold.base.constant;

import java.math.BigDecimal;

/**
 * Null类
 *
 * @author shadow
 */
public final class Null {
    public static final String STRING = "@ValueIsNull@";
    public static final Integer NUMBER = -2147483647;
    public static final BigDecimal DECIMAL = new BigDecimal(NUMBER);
}
