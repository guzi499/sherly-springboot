package com.guzi.sherly.service;

import com.guzi.sherly.model.dto.FlowMatchDonePageDTO;
import com.guzi.sherly.model.dto.FlowMatchRequestPageDTO;
import com.guzi.sherly.model.dto.FlowMatchTodoPageDTO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Service
public class FlowMatchService {

    @Autowired
    private TaskService taskService;

    public List todo(FlowMatchTodoPageDTO dto) {
        Long userId = SecurityUtil.getUserId();
        TaskQuery query = taskService
                .createTaskQuery()
                .taskAssignee(userId.toString());
        List<Task> tasks = query.listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());
        return tasks;
    }

    public List done(FlowMatchDonePageDTO dto) {
        return null;
    }

    public List request(FlowMatchRequestPageDTO dto) {
        return null;
    }
}
