package com.guzi.upr.exception;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
public class BizException extends RuntimeException {

    private String code;
    private String error;
    private String message;

    public BizException() {
    }

    public BizException(String code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

    public BizException(IBaseError error) {
        this.code = error.getCode();
        this.error = error.getError();
        this.message = error.getMessage();
    }
}
