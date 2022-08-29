package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/8/29
 */
@Data
@TableName("ge_module")
public class Module extends BaseModel {

    /** 模块id */
    @TableId(type = IdType.AUTO)
    private Integer moduleId;

    /** 模块名称 */
    private Integer moduleName;

    /** 父模块id */
    private Integer parentId;
}
