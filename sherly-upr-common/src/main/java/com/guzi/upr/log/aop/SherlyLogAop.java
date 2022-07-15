package com.guzi.upr.log.aop;

import com.guzi.upr.log.annotation.SherlyLog;
import com.guzi.upr.log.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static com.guzi.upr.model.contants.CommonConstants.*;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Slf4j
@Aspect
@Component
public class SherlyLogAop {

    private final OperationLogService operationLogService;

    ThreadLocal<Long> recordTime = new ThreadLocal<>();

    public SherlyLogAop(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Pointcut("execution(* com.guzi.upr..*Controller.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        SherlyLog annotation = methodSignature.getMethod().getAnnotation(SherlyLog.class);
        // 如果controller方法上有注解 且 标注为不记录 那么就不记录日志
        if (annotation != null && annotation.noRecord()) {
            return joinPoint.proceed();
        }

        try {
            recordTime.set(System.currentTimeMillis());
            Object result = joinPoint.proceed();
            Long duration = System.currentTimeMillis() - recordTime.get();
            operationLogService.saveOne(duration, joinPoint, NORMAL_LOG, null);
            return result;
        } catch (Throwable exception) {
            Long duration = System.currentTimeMillis() - recordTime.get();
            operationLogService.saveOne(duration, joinPoint, EXCEPTION_LOG, exception);
            throw exception;
        } finally {
            recordTime.remove();
        }
    }
}
