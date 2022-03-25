package com.guzi.upr.model;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
public abstract class ThreadLocalModel {

    private String tenantCode;

    private Long userId;

    public String getTenantCode() {
        return tenantCode;
    }

    public Long getUserId() {
        return userId;
    }
}
