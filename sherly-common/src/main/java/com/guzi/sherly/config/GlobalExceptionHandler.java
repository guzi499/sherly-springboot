package com.guzi.sherly.config;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.exception.BizException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.guzi.sherly.model.exception.enums.CommonErrorEnum.ACCESS_DENY;
import static com.guzi.sherly.model.exception.enums.CommonErrorEnum.PARAMS_ERR;

/**
 * 全局异常处理器
 *
 * @author 谷子毅
 * @date 2022/3/23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, BindException.class})
    public Result validateExceptionHandler(Exception e) {
        e.printStackTrace();

        StringBuilder message = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            // @RequestBody 方式校验
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                message.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
            }
        } else if (e instanceof ConstraintViolationException) {
            // @RequestParam 方式校验
            ConstraintViolationException ex = (ConstraintViolationException) e;
            message.append(ex.getMessage());
        } else {
            // get Model 方式校验
            BindException ex = (BindException) e;
            for (FieldError fieldError : ex.getFieldErrors()) {
                message.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
            }
        }

        return Result.error(PARAMS_ERR, message.toString());
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result bizExceptionHandler(BizException e) {
        e.printStackTrace();
        return Result.error(e);
    }

    /**
     * 空指针异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointExceptionHandler(NullPointerException e) {
        e.printStackTrace();
        return Result.error(e);
    }

    /**
     * security认证异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedException(Exception e) {
        e.printStackTrace();
        return Result.error(ACCESS_DENY);
    }

    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error(e);
    }
}
