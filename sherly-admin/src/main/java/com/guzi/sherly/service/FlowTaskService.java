package com.guzi.sherly.service;

import com.guzi.sherly.model.dto.TaskHandleDTO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Service
public class FlowTaskService {

    @Autowired
    private TaskService taskService;

    public void handle(TaskHandleDTO dto) {
        Long userId = SecurityUtil.getUserId();
        taskService.claim(dto.getTaskId(), userId.toString());
        taskService.addComment(dto.getTaskId(), dto.getInstanceId(), dto.getMessage());
        taskService.complete(dto.getTaskId(), dto.getVariables());
    }
}
