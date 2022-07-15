package com.guzi.upr.log.service;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.model.vo.OperationLogVO;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
public interface OperationLogService {

    /**
     * 日志记录
     * @param duration
     * @param joinPoint
     * @param type
     * @param exception
     * @throws Exception
     */
    void saveOne(Long duration, ProceedingJoinPoint joinPoint, Integer type, Throwable exception) throws Exception;

    /**
     * 日志分页
     * @param dto
     * @return
     */
    PageResult<OperationLogPageVO> listPage(OperationLogPageDTO dto);

    /**
     * 日志详情
     * @param logId
     * @return
     */
    OperationLogVO getOne(Long logId);

    /**
     * 日志清空
     */
    void removeAll();
}
