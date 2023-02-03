package com.guzi.sherly.modules.notice.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
    /** 平台消息 */
    PLATFORM(1, "PLATFORM", "平台消息"),
    /** 系统消息 */
    SYSTEM(2, "SYSTEM", "普通消息")
    ;

    @EnumValue
    private final Integer type;
    @JsonValue
    private final String key;
    private final String description;

}
