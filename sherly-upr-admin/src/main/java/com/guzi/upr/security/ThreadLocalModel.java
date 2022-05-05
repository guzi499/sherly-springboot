package com.guzi.upr.security;

import lombok.Data;

/**
 * token参数
 *
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class ThreadLocalModel {

    private Long userId;

    private String phone;

    private String tenantCode;

    private String operateTenantCode;

    private String nickname;

    private String realName;

}
