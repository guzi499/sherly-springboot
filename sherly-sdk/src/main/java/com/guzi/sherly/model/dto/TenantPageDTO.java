package com.guzi.sherly.model.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.sherly.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantPageDTO extends PageQuery {

    /** 租户代码 */
    @ApiModelProperty(value = "租户代码")
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

    /** 开始过期时间 */
    @ApiModelProperty(value = "开始过期时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginExpireTime;

    /** 结束过期时间 */
    @ApiModelProperty(value = "结束过期时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endExpireTime;

    /** 开始用户上限 */
    @ApiModelProperty(value = "开始用户上限")
    private Long beginUserLimit;

    /** 结束用户上限 */
    @ApiModelProperty(value = "结束用户上限")
    private Long endUserLimit;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endTime;
}
