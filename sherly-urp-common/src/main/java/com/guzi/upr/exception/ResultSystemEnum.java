package com.guzi.upr.exception;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
public enum ResultSystemEnum implements IBaseError{

    /** 服务器错误 */
    ERROR("999999", "服务器错误", null);

    private final String code;
    private final String error;
    private final String message;

    ResultSystemEnum(String code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
