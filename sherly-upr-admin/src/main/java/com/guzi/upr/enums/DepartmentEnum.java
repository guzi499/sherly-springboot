package com.guzi.upr.enums;

import com.guzi.upr.exception.IBaseError;

/**
 * @ClassName: DepartmentEnum
 * @author: 冰焰
 * @date: 2022/4/2
 * @Version: V1.0
 **/
public enum DepartmentEnum implements IBaseError {

    /**
     * 错误示例
     */
    DEPARTMENT_IS_EXIST("102001", "部门已存在！"),
    DEPARTMENT_NOT_EXIST("102002", "部门不存在！");

    private final String code;
    private final String message;

    DepartmentEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
