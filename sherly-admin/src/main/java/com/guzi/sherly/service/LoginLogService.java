package com.guzi.sherly.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.LoginLogPageDTO;
import com.guzi.sherly.model.vo.LoginLogPageVO;
import com.guzi.sherly.modules.log.manager.LoginLogManager;
import com.guzi.sherly.modules.log.model.LoginLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogService {

    @Autowired
    private LoginLogManager loginLogManager;

    /**
     * 日志分页
     * @param dto
     * @return
     */
    public PageResult<LoginLogPageVO> listPage(LoginLogPageDTO dto) {
        Page<LoginLog> page = loginLogManager.listPage(dto);

        List<LoginLogPageVO> result = page.getRecords().stream().map(e -> {
            LoginLogPageVO vo = new LoginLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 日志清空
     */
    public void removeAll() {
        loginLogManager.removeAll();
    }
}
