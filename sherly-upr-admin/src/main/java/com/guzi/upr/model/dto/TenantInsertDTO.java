package com.guzi.upr.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantInsertDTO {
    /** 租户code */
    @ApiModelProperty("租户code")
    @NotBlank
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty("租户名称")
    @NotBlank
    private String tenantName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    @NotBlank
    private String phone;

    /** 过期时间 */
    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotBlank
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty("用户上限")
    @NotBlank
    private Long userLimit;
}
