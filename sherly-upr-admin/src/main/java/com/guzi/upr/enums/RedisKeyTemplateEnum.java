package com.guzi.upr.enums;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
public enum RedisKeyTemplateEnum {

    /** userDetails */
    USER_DETAILS("ge:user:", "SpringSecurity用户详情");


    private final String keyTemplate;
    private final String description;

    RedisKeyTemplateEnum(String keyTemplate, String description) {
        this.keyTemplate = keyTemplate;
        this.description = description;
    }


    public String getKey() {
        return keyTemplate;
    }


    public String getDescription() {
        return description;
    }
}
