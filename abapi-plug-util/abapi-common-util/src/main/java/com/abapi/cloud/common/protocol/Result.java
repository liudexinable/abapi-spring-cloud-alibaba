package com.abapi.cloud.common.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ldx
 * @Date 2019/9/24 14:01
 * @Description
 * @Version 1.0.0
 */
@Data
public class Result implements Serializable {

    private String msg;

    private String code;

    private Object data;

    private final static String DEFAULE_SUCCESS = "200";

    private final static String DEFAULT_FAIL = "500";

    public Result() {}

    public Result(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static void aa(){
        System.out.println("-----");
    }

    public static Result success(){
        Result result = new Result(DEFAULE_SUCCESS,null);
        return result;
    }

    public static Result success(String code,String message){
        Result result = new Result(code,message);
        return result;
    }

    public static Result success(String code,String message,Object data){
        Result result = new Result(code,message,data);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result(DEFAULE_SUCCESS,null,data);
        return result;
    }

    public static Result fail(){
        Result result = new Result(DEFAULT_FAIL,null);
        return result;
    }

    public static Result fail(String code,String message){
        Result result = new Result(code,message);
        return result;
    }

    public static Result fail(String code,String message,Object data){
        Result result = new Result(code,message,data);
        return result;
    }

    public static Result fail(Object data){
        Result result = new Result(DEFAULT_FAIL,null,data);
        return result;
    }

}
