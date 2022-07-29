package com.guzi.upr.model.exception.enums;

import com.guzi.upr.model.exception.IBaseError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2022/7/29
 */
@Getter
@AllArgsConstructor
public enum CommonErrorEnum implements IBaseError {

    /** 000-SUCCESS */
    SUCCESS("000", "SUCCESS"),
    /** 001-参数异常 */
    PARAMS_ERR("999", "{%s}"),
    /** 999-未知异常 */
    UNKNOWN("999", "服务器异常，请联系服务商！"),

    /** 401-登录未授权 */
    UNAUTHORIZED("401", "登录未授权！"),
    /** 403-操作未授权 */
    ACCESS_DENY("403", "操作未授权！"),
    ;

    private final String code;
    private final String message;
}
