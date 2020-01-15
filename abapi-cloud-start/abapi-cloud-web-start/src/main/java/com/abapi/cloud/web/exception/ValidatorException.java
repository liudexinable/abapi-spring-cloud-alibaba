package com.abapi.cloud.web.exception;

/**
 * @Author ldx
 * @Date 2019/9/24 10:49
 * @Description
 * @Version 1.0.0
 */
public class ValidatorException extends RuntimeException{

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;

    private String errMsg;

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public ValidatorException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
