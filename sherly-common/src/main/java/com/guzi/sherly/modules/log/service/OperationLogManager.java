package com.guzi.sherly.modules.log.service;

import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.log.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.model.OperationLogDO;
import com.guzi.sherly.modules.log.vo.OperationLogPageVO;
import com.guzi.sherly.modules.log.vo.OperationLogVO;
import org.springframework.scheduling.annotation.Async;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
public interface OperationLogManager {

    /**
     * 日志记录
     * @param operationLogDO
     */
    @Async
    void saveOne(OperationLogDO operationLogDO);

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
