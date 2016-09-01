package com.lanyine.manifold.mybatis.query;

import java.util.Arrays;

class UpdateObject {
    private Object ob;
    private boolean include;
    private String[] fields;

    public UpdateObject() {
    }

    public UpdateObject(Object ob, boolean include, String[] fields) {
        super();
        this.ob = ob;
        this.include = include;
        this.fields = fields;
    }

    public Object getOb() {
        return ob;
    }

    public void setOb(Object ob) {
        this.ob = ob;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (include ? 1231 : 1237);
        result = prime * result + ((ob == null) ? 0 : ob.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UpdateObject other = (UpdateObject) obj;
        if (include != other.include)
            return false;
        if (ob == null) {
            if (other.ob != null)
                return false;
        } else if (!ob.equals(other.ob))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UpdateObject [ob=" + ob + ", include=" + include + ", fields=" + Arrays.toString(fields) + "]";
    }

}
