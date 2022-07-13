package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/7/13
 */
@Data
public class UserSelectDTO {
    /** 用户ids */
    @ApiModelProperty(value = "用户ids")
    private List<Long> userIds;

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

    /** 部门ids */
    @ApiModelProperty(value = "部门ids")
    private List<Long> departmentIds;

    /** 角色ids */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleIds;

    /** 启用禁用 */
    @ApiModelProperty(value = "启用禁用")
    private Integer enable;
}
