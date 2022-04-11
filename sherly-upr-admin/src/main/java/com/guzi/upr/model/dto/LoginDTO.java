package com.guzi.upr.model.dto;

import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class LoginDTO {

    private String tenantCode;

    private String phone;

    private String password;
}
