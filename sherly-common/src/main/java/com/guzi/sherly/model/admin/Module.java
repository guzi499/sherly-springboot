package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

@Data
@TableName("ge_module")
public class Module extends BaseModel {

    /** 模块id */
    @TableId(type = IdType.AUTO)
    private Integer moduleId;

    /** 模块代码 */
    private String moduleCode;

    /** 模块名称 */
    private String moduleName;

    /** 排序 */
    private Integer sort;

    /** 父模块id */
    private Integer parentId;
}
