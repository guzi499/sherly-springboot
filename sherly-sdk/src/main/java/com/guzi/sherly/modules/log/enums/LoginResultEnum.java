package com.guzi.sherly.modules.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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
    SUCCESS(0, "SUCCESS", "成功"),
    /** 账号或密码不正确 */
    CHECK_FAIL(1,"CHECK_FAIL", "账号或密码不正确"),
    /** 用户禁用 */
    DISABLE(2, "DISABLE", "用户禁用"),
    ;

    @EnumValue
    private final Integer value;
    @JsonValue
    private final String key;
    private final String description;
}
