package com.guzi.sherly.admin.user.vo;

import com.guzi.sherly.admin.user.enums.GenderEnum;
import com.guzi.sherly.common.enums.UsableEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 付东辉
 * @date 2022/4/9
 */
@Data
public class UserPageVo {
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

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 可用性 */
    @ApiModelProperty(value = "可用性")
    private UsableEnum enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
