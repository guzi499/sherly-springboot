package com.guzi.sherly.common.exception;

/**
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
