package com.guzi.upr.log.service;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
public interface OperationLogService {

    /**
     * 日志记录
     * @param duration
     * @param joinPoint
     * @param info
     * @param exception
     * @throws Exception
     */
    void saveOne(Long duration, ProceedingJoinPoint joinPoint, String info, Throwable exception) throws Exception;

    /**
     * 日志分页
     * @return
     */
    List listPage();

    /**
     * 日志详情
     * @return
     */
    Object getOne();

    /**
     * 日志清空
     */
    void removeAll();
}
