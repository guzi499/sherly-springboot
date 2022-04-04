package com.guzi.upr.interceptor;

import com.guzi.upr.model.ThreadLocalModel;
import lombok.Data;

/**
 * token参数
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class TokenParam implements ThreadLocalModel {

    private Long userId;

    private String tenantCode;

    private String operateTenantCode;

    private String nickname;

    private String realName;

}
