package com.guzi.upr.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@Data
public class BaseModel {
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private Date createUser;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateUser;

    /** 租户id */
    private String tenantId;
}
