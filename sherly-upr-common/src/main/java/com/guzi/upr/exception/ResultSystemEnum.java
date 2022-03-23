package com.guzi.upr.exception;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
public enum ResultSystemEnum implements IBaseError{

    /** 服务器错误示例 */
    ERROR("999999", "服务器错误"),

    TOKEN_ERROR("100001", "登录参数异常，请重试！");

    private final String code;
    private final String message;

    ResultSystemEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
