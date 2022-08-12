package com.guzi.sherly.model.exception;

import lombok.Getter;

/**
 * 业务异常
 * @author 谷子毅
 * @date 2022/3/23
 */
@Getter
public class BizException extends RuntimeException {

    private final String code;
    private final String message;

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(IBaseError error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BizException(IBaseError error, Object... args) {
        this.code = error.getCode();
        this.message = String.format(error.getMessage(), args);
    }
}
