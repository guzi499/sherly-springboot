package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class PermissionQueryDTO {

    /** 权限名 */
    @ApiModelProperty("权限名")
    private String permissionName;

}
