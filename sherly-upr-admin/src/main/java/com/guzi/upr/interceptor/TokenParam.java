package com.guzi.upr.interceptor;

import com.guzi.upr.model.ThreadLocalModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class TokenParam extends ThreadLocalModel {

    private Long userId;

    private String nickname;

    private String realName;

    private String tenantCode;

}
