package com.guzi.sherly.modules.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志类型枚举
 * @author 谷子毅
 * @date 2023/1/5
 */
@Getter
@AllArgsConstructor
public enum OperationLogTypeEnum {
    /** 正常 */
    NORMAL_LOG(0),
    /** 异常 */
    EXCEPTION_LOG(1),
    ;

    private final Integer type;
}
