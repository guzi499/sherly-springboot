package com.guzi.sherly.service;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.FlowDefinitionPageDTO;
import com.guzi.sherly.model.vo.FlowDefinitionPageVO;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@Service
public class FlowDefinitionService {

    @Autowired
    private RepositoryService repositoryService;

    public PageResult<FlowDefinitionPageVO> listPage(FlowDefinitionPageDTO dto) {

        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitions = query.orderByProcessDefinitionVersion().desc().listPage((dto.getCurrent() - 1) * dto.getSize(), dto.getSize());
        long count = query.count();

        List<FlowDefinitionPageVO> result = processDefinitions.stream().map(e -> {
            FlowDefinitionPageVO flowDefinitionPageVO = new FlowDefinitionPageVO();
            BeanUtils.copyProperties(e, flowDefinitionPageVO);
            return flowDefinitionPageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, count);
    }

    public void removeOne(String definitionId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
        if (definition == null) {
            return;
            // todo 当前流程不存在
        }
        repositoryService.deleteDeployment(definition.getDeploymentId());
    }
}
