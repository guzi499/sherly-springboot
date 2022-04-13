package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class LoginDTO {
    /** 租户code */
    @ApiModelProperty("租户code")
    private String tenantCode;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 密码 */
    @ApiModelProperty("密码")
    private String password;
}
