package com.guzi.sherly.admin.menu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 * @author 谷子毅
 * @date 2023/1/6
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    /** 目录 */
    DIR(1),
    /** 菜单 */
    MENU(2),
    /** 按钮 */
    BUTTON(3),
    ;

    private final Integer type;
}
