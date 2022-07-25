package com.guzi.upr.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guzi.upr.log.model.LoginLog;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    /**
     * 清空日志表
     */
    @Delete("truncate table sys_login_log")
    void removeAll();
}
