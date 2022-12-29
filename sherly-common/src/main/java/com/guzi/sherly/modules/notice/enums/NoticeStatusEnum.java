package com.guzi.sherly.modules.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Getter
@AllArgsConstructor
public enum NoticeStatusEnum {
    /** 未读 */
    UNREAD(0),
    /** 已读 */
    READ(1),
    ;

    private final Integer status;
}
