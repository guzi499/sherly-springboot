package com.guzi.upr.log;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
public class SherlyLogAop {

    @Pointcut("@annotation(com.guzi.upr.log.SherlyLog)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "throwable")
    public void afterThrowing(Joinpoint joinpoint, Throwable throwable) {

    }
}
