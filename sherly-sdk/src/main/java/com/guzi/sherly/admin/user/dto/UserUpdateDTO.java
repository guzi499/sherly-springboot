package com.guzi.sherly.admin.user.dto;

import com.guzi.sherly.admin.user.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/27
 */
@Data
public class UserUpdateDTO {
    /** 用户编号 */
    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull
    private Long userId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true)
    @NotNull
    private String realName;

    /** 性别 */
    @ApiModelProperty(value = "性别", required = true)
    @NotNull
    private GenderEnum gender;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleIds;
}
