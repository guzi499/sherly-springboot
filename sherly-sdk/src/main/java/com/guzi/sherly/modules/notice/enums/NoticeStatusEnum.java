package com.guzi.sherly.modules.notice.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息状态枚举
 * @author 谷子毅
 * @date 2022/12/29
 */
@Getter
@AllArgsConstructor
public enum NoticeStatusEnum {
    /** 未读 */
    UNREAD(0, "UNREAD", "未读"),
    /** 已读 */
    READ(1, "READ", "已读"),
    ;

    @EnumValue
    private final Integer type;
    @JsonValue
    private final String key;
    private final String description;
}
