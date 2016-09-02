package com.lanyine.manifold.base.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * JSON
 *
 * @author shadow
 */
public final class JsonObject implements Serializable {
	private static final long serialVersionUID = 3914211970422213626L;

	/**
	 * The response code for operation
	 **/
	private String code = "0";

	/**
	 * message
	 **/
	private String msg;

	/**
	 * data object
	 **/
	private Object data;

	/**
	 * The paging information
	 **/
	private Pagination page;

	public String getCode() {
		return code;
	}

	public JsonObject setCode(String code) {
		this.code = code;
		return this;
	}

	public Object getData() {
		return data;
	}

	public JsonObject setData(Object data) {
		// If the data instance of PageResponse, set the paging
		if (data instanceof PageResponse) {
			PageResponse<?> rdata = (PageResponse<?>) data;
			this.setPage(rdata.toPagination());
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

	public Pagination getPage() {
		return page;
	}

	public JsonObject setPage(Pagination pagination) {
		this.page = pagination;
		return this;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
