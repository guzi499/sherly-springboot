package com.guzi.sherly.model.dto;

import cn.hutool.core.date.DatePattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantInsertDTO {
    /** 租户code */
    @ApiModelProperty(value = "租户code", required = true)
    @NotBlank
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称", required = true)
    @NotBlank
    private String tenantName;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", required = true)
    @NotBlank
    private String contactUser;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String contactPhone;

    /** 过期时间 */
    @ApiModelProperty(value = "过期时间", required = true)
    @NotNull
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty(value = "用户上限", required = true)
    @NotNull
    private Long userLimit;
}
