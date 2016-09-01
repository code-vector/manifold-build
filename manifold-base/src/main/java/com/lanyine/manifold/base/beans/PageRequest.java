package com.lanyine.manifold.base.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * 分页请求
 *
 * @author shadow
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int _PAGE = 1;
    public static final int _PAGE_SIZE = 10;

    /**
     * 当前页码
     */
    private int page = _PAGE;

    /**
     * 每页查询的条数
     */
    private int pageSize = _PAGE_SIZE;

    public PageRequest() {
    }

    public PageRequest(int page, int pageSize) {
        this.setPage(page);
        this.setPageSize(pageSize);
    }

    protected PageRequest(PageRequest page) {
        this(page.getPage(), page.getPageSize());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page < 1 ? _PAGE : page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < 1 ? _PAGE_SIZE : pageSize;
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
