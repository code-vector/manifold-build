package com.lanyine.manifold.mybatis.base;

import java.io.Serializable;

/**
 * @author shadow
 * @Description:
 */
public class KV implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object k;
    private Object v;

    public Object getK() {
        return k;
    }

    public void setK(Object k) {
        this.k = k;
    }

    public Object getV() {
        return v;
    }

    public void setV(Object v) {
        this.v = v;
    }

}
