package com.lanyine.manifold.base.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * JSON类
 *
 * @author shadow
 */
public final class JsonObject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作响应码
     **/
    private String code = "0";

    /**
     * 返回的消息，暂时留用
     **/
    private String msg; // 返回的消息

    /**
     * 需要处理的数据对象
     **/
    private Object data;

    /**
     * 分页概述
     **/
    private Pagination pagination;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public JsonObject setData(Object data) {
        // 如果数据是分页信息，则自动设置分页信息
        if (data instanceof PageResponse) {
            this.setPagination(((PageResponse<?>) data).toPagination());
        }
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonObject setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public JsonObject setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
