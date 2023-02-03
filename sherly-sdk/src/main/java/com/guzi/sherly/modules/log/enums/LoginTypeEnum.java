package com.guzi.sherly.modules.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式枚举
 * @author 谷子毅
 * @date 2023/1/5
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    /** 密码登录 */
    PASSWORD(0, "PASSWORD", "密码登录"),
    ;

    @EnumValue
    private final Integer value;
    @JsonValue
    private final String key;
    private final String description;
}
