package com.guzi.upr.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/4/7
 */
@Data
public class RolePageVO {
    /** 角色id */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;
}
