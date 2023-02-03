package com.guzi.sherly.admin.menu.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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
    DIR(1, "目录"),
    /** 菜单 */
    MENU(2, "菜单"),
    /** 按钮 */
    BUTTON(3, "按钮"),
    ;

    @EnumValue
    @JsonValue
    private final Integer value;
    private final String description;
}
