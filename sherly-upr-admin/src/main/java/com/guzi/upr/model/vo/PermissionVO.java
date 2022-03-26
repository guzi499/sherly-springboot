package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class PermissionVO {

    /** 权限id */
    @ApiModelProperty("权限id")
    private Long permissionId;

    /** 权限名 */
    @ApiModelProperty("权限名")
    private String permissionName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    private Long parentId;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<PermissionVO> children;
}
