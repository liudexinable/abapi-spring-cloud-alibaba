package com.abapi.cloud.web.exception;

/**
 * @Author ldx
 * @Date 2019/9/24 10:49
 * @Description
 * @Version 1.0.0
 */
public class ApiException extends RuntimeException{

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;

    private String errMsg;

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String errCode, String errMsg) {
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
