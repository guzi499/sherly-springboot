package com.guzi.upr.model;

/**
 * token参数baseModel，所有token参数需继承此类
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
