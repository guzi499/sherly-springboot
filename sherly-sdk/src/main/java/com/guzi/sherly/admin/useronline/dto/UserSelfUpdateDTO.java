package com.guzi.sherly.admin.useronline.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/6/22
 */
@Data
public class UserSelfUpdateDTO {

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    @Email
    private String email;

    /** 性别[enum] */
    @ApiModelProperty(value = "性别[enum]", required = true)
    @NotNull
    private Integer gender;
}
