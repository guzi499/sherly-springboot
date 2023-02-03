package com.guzi.sherly.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.sherly.modules.log.model.OperationLogDO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLogDO> {
    /**
     * 清空日志表
     */
    @Delete("truncate table sys_operation_log")
    void removeAll();
}
