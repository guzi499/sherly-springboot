package com.guzi.upr.model;

import com.guzi.upr.util.GlobalParamUtil;

import java.util.ArrayList;
import java.util.List;

import static com.guzi.upr.model.contants.CommonConstants.FALSE;

/**
 * 通用结果返回类
 * @author 谷子毅
 * @date 2022/3/22
 */
public class Result<T> {

    private static final String DEFAULT_MSG = "SUCCESS";

    private static final String ERROR_MSG = "服务器异常，请联系服务商！";

    private static final String DEFAULT_CODE = "000000";

    private static final String ERROR_CODE = "999999";

    private String code;

    private String message;

    private List<String> errorStack;

    private Long timestamp;

    private T data;

    public Result(T data) {
        this.code = DEFAULT_CODE;
        this.message = DEFAULT_MSG;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Result(String code, String message, List<String> errorStack) {
        this.code = code;
        this.message = message;
        this.errorStack = errorStack;
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> Result<T> success(T data) {
        return new Result(data);
    }

    public static Result success() {
        return Result.success(null);
    }


    public static Result error(String message) {
        return new Result(ERROR_CODE, message);
    }

    public static Result error(String code, String message, Exception e) {
        return new Result(ERROR_CODE, message, parseException(e));
    }

    public static Result error(String message, Exception e) {
        return new Result(ERROR_CODE, message, parseException(e));
    }

    public static Result error(Exception e) {
        return new Result(ERROR_CODE, ERROR_MSG, parseException(e));
    }

    private static List<String> parseException(Exception e) {
        // 如果是生产环境，则不解析异常栈
        if (FALSE.equals(GlobalParamUtil.getValue("spring.application.dev-flag"))) {
            return null;
        }

        List<String> list = new ArrayList<>();

        // 记录异常原因
        list.add("【" + e.getMessage() + "】");

        // 记录异常堆栈
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            String line = stackTraceElement.toString();
            if (line.startsWith("com.guzi")) {
                list.add(line);
            }
        }

        return list;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrorStack() {
        return errorStack;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }
}
