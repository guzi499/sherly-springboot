package com.guzi.upr.model.dto;

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
    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    /** 密码 */
    @ApiModelProperty("密码")
    @NotBlank
    private String password;
}
