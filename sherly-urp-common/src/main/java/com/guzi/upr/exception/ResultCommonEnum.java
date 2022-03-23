package com.guzi.upr.exception;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
public enum ResultCommonEnum implements IBaseError{

    /** 成功 */
    SUCCESS("100000", null, null);

    private final String code;
    private final String error;
    private final String message;

    ResultCommonEnum(String code, String error, String message) {
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
