package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/27
 */
@Data
public class UserUpdateDTO {
    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    /** 姓名 */
    @ApiModelProperty("姓名")
    @NotNull
    private String realName;

    /** 性别 */
    @ApiModelProperty("性别")
    @NotNull
    private Integer gender;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty("角色ids")
    private List<Long> roleIds;
}
