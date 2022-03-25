package com.guzi.upr.enums;

import com.guzi.upr.exception.IBaseError;
import com.guzi.upr.manager.PermissionManager;

/**
 * @author 谷子毅
 * @date 2022/3/23
 */
public enum ResultAdminEnum implements IBaseError {

    /** 错误示例 */
    ERROR("999999", "服务器错误"),

    TOKEN_NOT_FOUND("100001", "TOKEN不存在，请检查！"),
    TOKEN_ERROR("100002", "登录参数异常，请重试！"),
    LOGIN_ERROR("100003", "用户名或密码错误！"),
    PERMISSION_REPEAT("100004", "该权限已存在！");

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
