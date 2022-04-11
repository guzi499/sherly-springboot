package com.guzi.upr.exception;

/**
 * 自定义异常枚举BaseModel，所有自定义异常枚举需继承此类
 * @author 谷子毅
 * @date 2022/3/23
 */
public interface IBaseError {
    /**
     * 获取错误码
     * @return code
     */
    String getCode();

    /**
     * 获取错误详情
     * @return message
     */
    String getMessage();
}
