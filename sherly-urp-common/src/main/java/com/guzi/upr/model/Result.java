package com.guzi.upr.model;

import lombok.Data;

/**
 * 通用结果返回类
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Data
public class Result<T> {

    private static final String DEFAULT_MSG = "SUCCESS";

    private static final String DEFAULT_CODE = "200";

    private String code;

    private String message;

    private Long timestamp;

    private T data;

    public Result(){
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result success(T data) {
        return new Result(DEFAULT_CODE, DEFAULT_MSG, data);
    }

    public static Result success() {
        return new Result(DEFAULT_CODE, DEFAULT_MSG);
    }
}
