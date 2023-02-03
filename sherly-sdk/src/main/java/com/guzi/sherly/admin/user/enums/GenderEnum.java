package com.guzi.sherly.admin.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 * @author 谷子毅
 * @date 2023/1/6
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    /** 女 */
    FEMALE(0, "FEMALE", "女"),
    /** 男 */
    MALE(1, "MALE", "男"),
    /** 未知 */
    UNKNOWN(2, "UNKNOWN", "未知"),
    ;

    @EnumValue
    private final Integer value;
    @JsonValue
    private final String key;
    private final String description;
}
