package com.guzi.upr.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 付东辉
 * @date 2022/4/9
 */
@Data
public class UserPageVo {
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

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 性别[enum] */
    @ApiModelProperty(value = "性别[enum]", example = "CommonConstants.java")
    private Integer gender;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    /** 启用禁用[enum] */
    @ApiModelProperty(value = "启用禁用[enum]", example = "CommonConstants.java")
    private Integer enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
