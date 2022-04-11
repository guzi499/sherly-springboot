package com.guzi.upr.exception;

/**
 * 业务异常
 * @author 谷子毅
 * @date 2022/3/23
 */
public class BizException extends RuntimeException {

    private String code;
    private String message;

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(IBaseError error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
