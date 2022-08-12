package com.guzi.sherly.log.service;

import com.guzi.sherly.log.model.OperationLog;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.OperationLogPageDTO;
import com.guzi.sherly.model.vo.OperationLogPageVO;
import com.guzi.sherly.model.vo.OperationLogVO;
import org.springframework.scheduling.annotation.Async;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
public interface OperationLogService {

    /**
     * 日志记录
     * @param operationLog
     */
    @Async
    void saveOne(OperationLog operationLog);

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
