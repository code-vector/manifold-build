package com.lanyine.manifold.web.bean;

import java.util.Date;

/**
 * @Description: Session 用户对象,不要序列化接口!!!
 * @author shadow
 * 
 */
public abstract class User {
	/**
	 * 用户ID
	 */
	private Integer id;

	/**
	 * 用户登录账号
	 */
	private String account;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户类型- 11：员工， 21：供应商，31：采购商
	 * 
	 * @see com.manifold.commons.constant.Constant.User
	 */
	private Integer type;

	/**
	 * 邮件是否验证- 0：未验证，1：已验证
	 */
	private Integer emailValid;

	/**
	 * 是否是否验证- 0：未验证，1：已验证
	 */
	private Integer phoneValid;

	/**
	 * 激活类型- 1：系统自动，2：邮箱激活，3 手机激活，4：参展激活，
	 */
	private Integer activeType;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 账号状态- 1:正常，2：禁用， （默认1）
	 */
	private Integer status;

	/**
	 * 是否账号异常- 1：正常，2：异常，（默认1）
	 */
	private Integer exception;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getEmailValid() {
		return emailValid;
	}

	public void setEmailValid(Integer emailValid) {
		this.emailValid = emailValid;
	}

	public Integer getPhoneValid() {
		return phoneValid;
	}

	public void setPhoneValid(Integer phoneValid) {
		this.phoneValid = phoneValid;
	}

	public Integer getActiveType() {
		return activeType;
	}

	public void setActiveType(Integer activeType) {
		this.activeType = activeType;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getException() {
		return exception;
	}

	public void setException(Integer exception) {
		this.exception = exception;
	}

}
