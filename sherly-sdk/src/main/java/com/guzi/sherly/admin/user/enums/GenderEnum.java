package com.guzi.sherly.admin.user.enums;

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
    FEMALE(0),
    /** 男 */
    MALE(1),
    /** 未知 */
    NO_GENDER(2),
    ;

    private final Integer gender;
}
