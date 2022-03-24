package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@Data
@TableName("sys_tenant")
public class Tenant extends BaseModel {

    /** 租户id */
    @ApiModelProperty("租户id")
    @TableId(type = IdType.AUTO)
    private Long tenantId;

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
    private Date expireTime;
}
