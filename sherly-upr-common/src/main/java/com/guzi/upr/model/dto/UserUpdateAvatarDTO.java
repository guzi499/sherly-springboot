package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/6/22
 */
@Data
public class UserUpdateAvatarDTO {
    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    /** 用户头像 */
    @ApiModelProperty("用户头像")
    @NotBlank
    private String avatar;
}
