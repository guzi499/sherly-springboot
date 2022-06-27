package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/28
 */
@Data
@TableName("sys_department")
public class Department extends BaseModel {
    /** 部门id */
    @TableId(type = IdType.AUTO)
    private Long departmentId;

    /** 部门名称 */
    private String departmentName;

    /** 描述 */
    private String description;

    /** 父部门id */
    private Long parentId;

    /** 排序 */
    private Integer sort;
}
