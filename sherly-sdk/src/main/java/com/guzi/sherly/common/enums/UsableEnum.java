package com.guzi.sherly.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2023/2/3
 */
@Getter
@AllArgsConstructor
public enum UsableEnum {
    /** 可用 */
    ENABLE(1, "ENABLE", "可用"),
    /** 禁用 */
    DISABLE(0, "DISABLE", "禁用"),
    ;

    @EnumValue
    private final Integer value;
    @JsonValue
    private final String key;
    private final String description;
}
