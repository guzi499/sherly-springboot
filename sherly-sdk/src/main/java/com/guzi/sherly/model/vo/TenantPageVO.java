package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantPageVO {
    /** 租户id */
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /** 租户code */
    @ApiModelProperty(value = "租户code")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    /** 联系人 */
    @ApiModelProperty(value = "联系人")
    private String contactUser;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    /** 过期时间 */
    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty(value = "用户上限")
    private Long userLimit;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
