package com.lanyine.manifold.base.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据返回
 * 
 * @author shadow
 * 
 */
public class PageResponse<T> extends Pagination {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回的分页内容
	 */
	private List<T> items = new ArrayList<>();

	public PageResponse() {
	}

	/**
	 * 
	 * @param page
	 *            PageRequest （包含页码，每页请求数）
	 * @param total
	 *            目标总条数
	 * @param items
	 *            该页内的内容
	 */
	public PageResponse(PageRequest page, int total, List<T> items) {
		super(page, total);
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	/**
	 * 根据查询结果获取分页信息 ;如果总条数为0.返回默认分页信息
	 * 
	 * @return
	 */
	public Pagination toPagination() {
		return new Pagination(getPage(), getPageSize(), getTotal());
	}
}
