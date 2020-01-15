package com.abapi.cloud.pay.exception;

/**
 * 异常 类
 * @author liu
 */
public class PayException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3455351346000192450L;

	private String code;

	public PayException(String msg) {
		super(msg);
	}

	public PayException(String msg, String code) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
