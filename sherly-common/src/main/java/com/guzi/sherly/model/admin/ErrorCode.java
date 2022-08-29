package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/8/22
 */
@Data
@TableName("ge_error_code")
public class ErrorCode extends BaseModel {
    /** 错误id */
    @TableId(type = IdType.AUTO)
    private Integer errorId;

    /** 错误代码 */
    private String errorCode;

    /** 错误信息 */
    private String message;

    /** 描述 */
    private String description;

    /** 模块id */
    private String moduleId;

    /** 模块代码 */
    private String moduleCode;
}
