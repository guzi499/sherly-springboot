package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/27
 */
@Data
public class UserInsertDTO {

    /** 姓名 */
    @ApiModelProperty(value = "姓名" , required = true)
    @NotBlank
    private String realName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String phone;

    /** 性别[enum] */
    @ApiModelProperty(value = "性别[enum]", required = true)
    @NotNull
    private Integer gender;

    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 角色ids */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleIds;


}
