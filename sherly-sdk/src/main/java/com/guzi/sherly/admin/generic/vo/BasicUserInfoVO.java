package com.guzi.sherly.admin.generic.vo;

import com.guzi.sherly.admin.user.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class BasicUserInfoVO {
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

    /** 用户头像 */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 租户代码 */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
}
