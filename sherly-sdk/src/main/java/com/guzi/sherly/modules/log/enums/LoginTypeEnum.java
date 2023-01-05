package com.guzi.sherly.modules.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式枚举
 * @author 谷子毅
 * @date 2023/1/5
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    /** 密码 */
    LOGIN_TYPE_PASSWORD(0),
    /** 二维码 */
    LOGIN_TYPE_QRCODE(1),
    /** QQ */
    LOGIN_TYPE_QQ(2),
    /** 微信 */
    LOGIN_TYPE_WX(3),
    ;

    private final Integer type;
}
