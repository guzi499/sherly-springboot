package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/27
 */
@Data
public class UserInsertDTO {
    /** 昵称 */
    @ApiModelProperty("昵称")
    @NotBlank
    private String nickname;

    /** 姓名 */
    @ApiModelProperty("姓名")
    @NotBlank
    private String realName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    @NotBlank
    private Integer gender;

    /** 部门id */
    @ApiModelProperty("部门id")
    @NotBlank
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty("角色ids")
    @NotBlank
    private List<Long> roleIds;


}
