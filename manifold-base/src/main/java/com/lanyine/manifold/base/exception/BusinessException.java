package com.lanyine.manifold.base.exception;

/**
 * 业务异常
 * 
 * @author shadow
 * 
 */
public class BusinessException extends RuntimeException {

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

	public BusinessException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BusinessException(String errorCode, String defaultMessage) {
		super(defaultMessage);
		this.errorCode = errorCode;
	}
}
