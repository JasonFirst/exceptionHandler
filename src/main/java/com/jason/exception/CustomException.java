package com.jason.exception;

import com.jason.enumeration.ErrorMsgEnum;

/**
 * 如果需要抛出异常数据，请用该自定义异常类。
 *
 */
public class CustomException extends Exception {

	private ErrorMsgEnum errorMsgEnum;
	private static final long serialVersionUID = 1L;

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}

	public CustomException() {
		super();
	}

	public CustomException(ErrorMsgEnum errorMsgEnum) {
		this.errorMsgEnum = errorMsgEnum;
	}

	public ErrorMsgEnum getErrorMsgEnum() {
		return errorMsgEnum;
	}

	public void setErrorMsgEnum(ErrorMsgEnum errorMsgEnum) {
		this.errorMsgEnum = errorMsgEnum;
	}

}
