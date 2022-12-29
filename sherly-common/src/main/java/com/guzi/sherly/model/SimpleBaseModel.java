package com.guzi.sherly.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Data
public abstract class SimpleBaseModel {
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 逻辑删除[enum] */
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;
}
