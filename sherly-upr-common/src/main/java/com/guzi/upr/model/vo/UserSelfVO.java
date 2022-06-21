package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/6/21
 */
@Data
public class UserSelfVO {
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

    /** 用户头像 */
    @ApiModelProperty("用户头像")
    private String avatar;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    private Integer gender;

    /** 性别 */
    @ApiModelProperty("性别")
    private String genderStr;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /** 角色ids */
    @ApiModelProperty("角色ids")
    private List<Long> roleIds;

    /** 角色名称列表 */
    @ApiModelProperty("角色名称列表")
    private List<String> roleNames;

    /** 租户名称 */
    @ApiModelProperty("租户名称")
    private String tenantName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
