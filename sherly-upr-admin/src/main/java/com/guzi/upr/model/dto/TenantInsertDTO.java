package com.guzi.upr.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantInsertDTO {
    /** 租户code */
    @ApiModelProperty("租户code")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty("租户名称")
    private String tenantName;

    /** 联系人id */
    @ApiModelProperty("联系人id")
    private Long contactUserId;

    /** 过期时间 */
    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty("用户上限")
    private Long userLimit;
}
