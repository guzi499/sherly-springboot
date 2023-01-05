package com.guzi.sherly.admin.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 付东辉
 * @date 2022/4/9 0:57
 */
@Data
public class UserVo {
    /** 用户编号 */
    @ApiModelProperty(value = "用户编号")
    private Long userId;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 性别[enum] */
    @ApiModelProperty(value = "性别[enum]")
    private Integer gender;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleIds;
}
