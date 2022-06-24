package com.guzi.upr.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基础对象，所有数据库orm对象都需继承此类
 * @author 谷子毅
 * @date 2022/3/18
 */
@Data
public abstract class BaseModel {

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建人id */
    @ApiModelProperty("创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /** 更新人id */
    @ApiModelProperty("更新人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /** 0未删除 1已删除 */
    @ApiModelProperty("0未删除 1已删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted = 0;
}
