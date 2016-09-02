package com.lanyine.manifold.base.beans;

import java.io.Serializable;

/**
 * 分页请求
 *
 * @author shadow
 */
public class PageRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int PAGE = 1;
	public static final int PAGE_SIZE = 10;

	/**
	 * 当前页码
	 */
	private int page = PAGE;

	/**
	 * 每页查询的条数
	 */
	private int pageSize = PAGE_SIZE;

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

	public PageRequest setPage(int page) {
		this.page = page < 1 ? PAGE : page;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageRequest setPageSize(int pageSize) {
		this.pageSize = pageSize < 1 ? PAGE_SIZE : pageSize;
		return this;
	}

	@Override
	public String toString() {
		return "{\"page\":" + page + ", \"pageSize\":" + pageSize + "}";
	}
}
