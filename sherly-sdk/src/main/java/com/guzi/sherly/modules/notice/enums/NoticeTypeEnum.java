package com.guzi.sherly.modules.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
    /** 系统消息 */
    SYSTEM(1, "系统消息"),
    COMMON(2, "普通消息")
    ;

    private final Integer type;
    private final String description;

}
