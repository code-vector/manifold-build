package com.lanyine.manifold.base.exception;

/**
 * Dao 异常
 * 
 * @author shadow
 * 
 */
public class BaseDaoException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public BaseDaoException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BaseDaoException(Throwable e) {
		super(e);
	}

	public BaseDaoException(String errorCode, String defaultMessage) {
		super(defaultMessage);
		this.errorCode = errorCode;
	}
}
