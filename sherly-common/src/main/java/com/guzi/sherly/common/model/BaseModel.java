package com.guzi.sherly.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建人编号 */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /** 更新人编号 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /** 逻辑删除[const] */
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;
}
