package com.guzi.sherly.admin.useronline.vo;

import com.guzi.sherly.admin.user.enums.GenderEnum;
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

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String genderStr;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    /** 角色ids */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleIds;

    /** 角色名称列表 */
    @ApiModelProperty(value = "角色名称列表")
    private List<String> roleNames;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
