package com.guzi.sherly.common.model;

import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.exception.IBaseError;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.guzi.sherly.common.contants.CommonConstants.FALSE;
import static com.guzi.sherly.common.exception.enums.CommonErrorEnum.SUCCESS;
import static com.guzi.sherly.common.exception.enums.CommonErrorEnum.UNKNOWN;

/**
 * 通用结果返回类
 * @author 谷子毅
 * @date 2022/3/22
 */
@Getter
public class Result<T> {

    private final String code;

    private final String message;

    private List<String> errorStack;

    private final Long timestamp;

    private T data;

    public Result(T data) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
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

    /**
     * 处理异常枚举
     * @param error
     * @return
     */
    public static Result error(IBaseError error) {
        return new Result(error.getCode(), error.getMessage());
    }

    /**
     * 处理预编译的异常枚举
     * @param error
     * @param message
     * @return
     */
    public static Result error(IBaseError error, String message) {
        return new Result(error.getCode(), String.format(error.getMessage(), message));
    }

    /**
     * 处理未知异常
     * @param e
     * @return
     */
    public static Result error(Exception e) {
        return new Result(UNKNOWN.getCode(), UNKNOWN.getMessage(), parseException(e));
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    public static Result error(BizException e) {
        return new Result(e.getCode(), e.getMessage(), parseException(e));
    }

    private static List<String> parseException(Exception e) {
        // 如果是生产环境，则不解析异常栈
        if (FALSE.equals(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDevFlag())) {
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
}
