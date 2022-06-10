package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/4/9 0:57
 */
@Data
public class UserVo {
    /** 用户id */
    @ApiModelProperty("用户id")
    private Long userId;

    /** 昵称 */
    @ApiModelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty("姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    private Integer gender;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty("角色ids")
    private List<Long> roleIds;
}
