package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@Data
@TableName("sys_tenant")
public class Tenant extends BaseModel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 租户名称 */
    private String tenantName;
}
