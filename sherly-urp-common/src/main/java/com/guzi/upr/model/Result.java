package com.guzi.upr.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 通用结果返回类
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Data
public class Result<T> {

    private static final String DEFAULT_MSG = "SUCCESS";

    private static final String DEFAULT_CODE = "100000";

    private static final String ERROR_CODE = "999999";

    private String code;

    private String error;

    private String message;

    private List<StackTraceElement> stackTrace;

    private Long timestamp;

    private T data;

    public Result(){
    }

    public Result(String code, String error, String message, T data) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Result(String code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Result(String code, String error, StackTraceElement[] message) {
        this.code = code;
        this.error = error;
        this.stackTrace = Arrays.asList(message);
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> Result<T> success(T data) {
        return new Result(DEFAULT_CODE, null, null, data);
    }

    public static Result success() {
        return new Result();
    }

    public static Result error(NullPointerException e) {
        return new Result(ERROR_CODE, "服务器错误", e.getStackTrace());
    }

    public static Result error(Exception e) {
        return new Result(ERROR_CODE, "服务器错误", e.getMessage());
    }
}
