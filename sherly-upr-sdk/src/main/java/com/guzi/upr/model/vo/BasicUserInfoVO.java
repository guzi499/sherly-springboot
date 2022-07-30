package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class BasicUserInfoVO {
    /** 用户id */
    @ApiModelProperty(value = "用户id")
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

    /** 用户头像 */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 性别[enum] */
    @ApiModelProperty(value = "性别[enum]")
    private Integer gender;

    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    /** 租户code */
    @ApiModelProperty(value = "租户code")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
}
