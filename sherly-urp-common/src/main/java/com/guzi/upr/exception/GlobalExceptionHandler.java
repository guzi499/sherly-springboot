package com.guzi.upr.exception;

import com.guzi.upr.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result bizExceptionHandler(BizException e) {
        return Result.error(e);
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointExceptionHandler(NullPointerException e) {
        return Result.error(e);
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        return Result.error(e);
    }
}
