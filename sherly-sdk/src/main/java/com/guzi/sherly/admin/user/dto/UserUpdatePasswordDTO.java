package com.guzi.sherly.admin.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 谷子毅
 * @date 2022/6/22
 */
@Data
public class UserUpdatePasswordDTO {
    /** 旧密码 */
    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank
    private String oldPassword;

    /** 新密码 */
    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank
    private String newPassword;
}
