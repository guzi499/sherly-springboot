package com.guzi.upr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.manager.LoginLogManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.dto.LoginLogPageDTO;
import com.guzi.upr.model.vo.LoginLogPageVO;
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

    public PageResult<LoginLogPageVO> listPage(LoginLogPageDTO dto) {
        Page<LoginLog> page = loginLogManager.listPage(dto);

        List<LoginLogPageVO> result = page.getRecords().stream().map(e -> {
            LoginLogPageVO vo = new LoginLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    public void removeAll() {
        loginLogManager.removeAll();
    }
}
