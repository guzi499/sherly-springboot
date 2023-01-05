package com.guzi.sherly.admin.department.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/3/28
 */
@Data
@TableName("sys_department")
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseModel {
    /** 部门编号 */
    @TableId(type = IdType.AUTO)
    private Long departmentId;

    /** 部门名称 */
    private String departmentName;

    /** 描述 */
    private String description;

    /** 父部门编号 */
    private Long parentId;

    /** 排序 */
    private Integer sort;
}
