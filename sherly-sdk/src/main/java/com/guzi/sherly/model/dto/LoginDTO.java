package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class LoginDTO {

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String phone;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;

    /** 租户code */
    @ApiModelProperty(value = "租户code")
    private String tenantCode;
}
