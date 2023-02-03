package com.guzi.sherly.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志类型枚举
 * @author 谷子毅
 * @date 2023/1/5
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum {
    /** 正常 */
    NORMAL(0, "NORMAL", "正常"),
    /** 异常 */
    EXCEPTION(1, "EXCEPTION", "异常"),
    ;

    @EnumValue
    private final Integer value;
    @JsonValue
    private final String key;
    private final String description;
}
