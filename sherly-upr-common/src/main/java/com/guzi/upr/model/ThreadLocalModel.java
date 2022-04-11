package com.guzi.upr.model;

/**
 * token参数baseModel，所有token参数需继承此类
 * @author 谷子毅
 * @date 2022/3/24
 */
public interface ThreadLocalModel {

    String getTenantCode();

    Long getUserId();

    String getOperateTenantCode();

}
