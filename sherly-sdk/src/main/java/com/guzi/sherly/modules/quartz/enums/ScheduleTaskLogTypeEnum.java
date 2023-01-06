package com.guzi.sherly.modules.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务日志类型枚举
 * @author 谷子毅
 * @date 2023/1/6
 */
@Getter
@AllArgsConstructor
public enum ScheduleTaskLogTypeEnum {
    /** 正常 */
    NORMAL_LOG(0),
    /** 异常 */
    EXCEPTION_LOG(1),
    ;

    private final Integer type;
}
