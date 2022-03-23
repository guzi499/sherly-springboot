package com.guzi.upr.enums;

import com.guzi.upr.exception.IBaseError;

/**
 * @author 谷子毅
 * @date 2022/3/23
 */
public enum ResultAdminEnum implements IBaseError {

    /** 错误示例 */
    ERROR("999999", "服务器错误");

    private final String code;
    private final String message;

    ResultAdminEnum(String code, String message) {
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
