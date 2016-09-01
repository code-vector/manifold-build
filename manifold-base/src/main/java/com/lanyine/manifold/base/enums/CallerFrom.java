package com.lanyine.manifold.base.enums;

/**
 * 调用源
 *
 * @author shadow
 */
public enum CallerFrom {
    /**
     * PC 网站
     */
    web(0),

    /**
     * 手机Wap
     */
    wap(1),

    /**
     * * 手机App *
     */
    app(2),

    /**
     * * 运营管理系统 *
     */
    oss(3);

    private final Integer value;

    private CallerFrom(Integer value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     *
     * @return null if the value is not found.
     */
    public static CallerFrom findByValue(int value) {
        switch (value) {
            case 0:
                return web;
            case 1:
                return wap;
            case 2:
                return app;
            case 3:
                return oss;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
