package com.guzi.sherly.service;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.FlowMatchDonePageDTO;
import com.guzi.sherly.model.dto.FlowMatchMyRequestPageDTO;
import com.guzi.sherly.model.dto.FlowMatchTodoPageDTO;
import com.guzi.sherly.model.vo.FlowMatchDonePageVO;
import com.guzi.sherly.model.vo.FlowMatchMyRequestPageVO;
import com.guzi.sherly.model.vo.FlowMatchTodoPageVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Service
public class FlowMatchService {

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    public PageResult<FlowMatchTodoPageVO> todo(FlowMatchTodoPageDTO dto) {
        Long userId = SecurityUtil.getUserId();
        TaskQuery taskQuery = taskService
                .createTaskQuery()
                .taskCandidateUser(userId.toString());

        List<Task> list = taskQuery.listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());

        long count = taskQuery.count();

        List<FlowMatchTodoPageVO> result = list.stream().map(e -> {
            FlowMatchTodoPageVO flowMatchTodoPageVO = new FlowMatchTodoPageVO();
            BeanUtils.copyProperties(e, flowMatchTodoPageVO);
            return flowMatchTodoPageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, count);
    }

    public PageResult<FlowMatchDonePageVO> done(FlowMatchDonePageDTO dto) {
        Long userId = SecurityUtil.getUserId();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService
                .createHistoricTaskInstanceQuery()
                .finished()
                .taskAssignee(userId.toString());

        List<HistoricTaskInstance> list = historicTaskInstanceQuery.listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());

        long count = historicTaskInstanceQuery.count();

        List<FlowMatchDonePageVO> result = list.stream().map(e -> {
            FlowMatchDonePageVO flowMatchDonePageVO = new FlowMatchDonePageVO();
            BeanUtils.copyProperties(e, flowMatchDonePageVO);
            return flowMatchDonePageVO;
        }).collect(Collectors.toList());
        return PageResult.build(result, count);
    }

    public PageResult<FlowMatchMyRequestPageVO> myRequest(FlowMatchMyRequestPageDTO dto) {
        Long userId = SecurityUtil.getUserId();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(userId.toString());

        List<HistoricProcessInstance> list = historicProcessInstanceQuery.listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());

        long count = historicProcessInstanceQuery.count();

        List<FlowMatchMyRequestPageVO> result = list.stream().map(e -> {
            FlowMatchMyRequestPageVO flowMatchMyRequestPageVO = new FlowMatchMyRequestPageVO();
            BeanUtils.copyProperties(e, flowMatchMyRequestPageVO);
            return flowMatchMyRequestPageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, count);
    }
}
