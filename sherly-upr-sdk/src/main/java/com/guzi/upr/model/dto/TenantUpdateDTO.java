package com.guzi.upr.model.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantUpdateDTO {
    /** 租户id */
    @ApiModelProperty(value = "租户id", required = true)
    @NotNull
    private Long tenantId;

    /** 过期时间 */
    @ApiModelProperty(value = "过期时间", required = true)
    @NotNull
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty(value = "用户上限", required = true)
    @NotNull
    private Long userLimit;
}
