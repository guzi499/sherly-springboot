package com.guzi.sherly.modules.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录结果枚举
 * @author 谷子毅
 * @date 2023/1/5
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {
    /** 成功 */
    LOGIN_LOG_SUCCESS(0),
    /** 账号或密码不正确 */
    LOGIN_LOG_FAIL(1),
    /** 用户禁用 */
    LOGIN_LOG_DISABLE(2),
    ;

    private final Integer result;
}
