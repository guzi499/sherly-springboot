package com.guzi.sherly.service;

import com.guzi.sherly.model.dto.FlowInstanceStartupDTO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Service
public class FlowInstanceService {

    @Resource
    private RuntimeService runtimeService;

    public void startup(FlowInstanceStartupDTO dto) {
        Long userId = SecurityUtil.getUserId();
        Authentication.setAuthenticatedUserId(userId.toString());
        runtimeService
                .createProcessInstanceBuilder()
                .variables(dto.getVariables())
                .processDefinitionId(dto.getDefinitionId())
                .start();
        Authentication.setAuthenticatedUserId(null);
    }
}
