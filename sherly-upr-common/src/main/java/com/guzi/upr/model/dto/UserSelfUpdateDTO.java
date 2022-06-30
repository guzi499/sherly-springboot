package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/6/22
 */
@Data
public class UserSelfUpdateDTO {
    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    /** 昵称 */
    @ApiModelProperty("昵称")
    private String nickname;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    @NotNull
    private Integer gender;
}
